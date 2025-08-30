package com.nicenpc.application.handler;

import com.nicenpc.application.command.UpdateUserCommand;
import com.nicenpc.application.metrics.ApplicationMetrics;
import com.nicenpc.domain.User;
import com.nicenpc.domain.exception.UserAlreadyExistsException;
import com.nicenpc.domain.exception.UserNotFoundException;
import com.nicenpc.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 更新使用者指令處理器
 */
@Component
@RequiredArgsConstructor
public class UpdateUserCommandHandler implements CommandHandler<UpdateUserCommand> {
    
    private final UserRepository userRepository;
    private final ApplicationMetrics applicationMetrics;
    
    @Override
    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public void handle(UpdateUserCommand command) {
        ApplicationMetrics.TimerSample sample = applicationMetrics.startUserUpdateTimer();
        
        try {
            // 檢查使用者是否存在
            User existingUser = userRepository.findById(command.getUserId())
                .orElseThrow(() -> new UserNotFoundException(command.getUserId()));
            
            // 如果email有變更，檢查新的email是否已被其他使用者使用
            if (!existingUser.getEmail().equals(command.getEmail()) && 
                userRepository.existsByEmail(command.getEmail())) {
                throw new UserAlreadyExistsException(command.getEmail());
            }
            
            // 更新使用者資訊並進行驗證
            existingUser.setName(command.getName());
            existingUser.setEmail(command.getEmail());
            existingUser.validate();
            
            userRepository.save(existingUser);
            
            // 記錄成功指標
            applicationMetrics.incrementUserUpdate();
            
        } finally {
            // 記錄執行時間
            applicationMetrics.stopUserUpdateTimer(sample);
        }
    }
}