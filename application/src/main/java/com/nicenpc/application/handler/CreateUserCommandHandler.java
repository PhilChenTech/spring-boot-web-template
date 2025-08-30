package com.nicenpc.application.handler;

import com.nicenpc.application.command.CreateUserCommand;
import com.nicenpc.application.metrics.ApplicationMetrics;
import com.nicenpc.domain.User;
import com.nicenpc.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 建立使用者指令處理器
 */
@Component
@RequiredArgsConstructor
public class CreateUserCommandHandler implements CommandHandler<CreateUserCommand> {
    
    private final UserRepository userRepository;
    private final ApplicationMetrics applicationMetrics;
    
    @Override
    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public void handle(CreateUserCommand command) {
        ApplicationMetrics.TimerSample sample = applicationMetrics.startUserCreationTimer();
        
        try {
            // 建立Domain物件並進行驗證
            User user = new User();
            user.setName(command.getName());
            user.setEmail(command.getEmail());
            
            // 使用Domain層的驗證邏輯
            user.validate();
            
            // 檢查email是否已存在
            if (userRepository.existsByEmail(command.getEmail())) {
                throw new IllegalArgumentException("電子郵件已存在: " + command.getEmail());
            }
            
            userRepository.save(user);
            
            // 記錄成功指標
            applicationMetrics.incrementUserCreation();
            
        } finally {
            // 記錄執行時間
            applicationMetrics.stopUserCreationTimer(sample);
        }
    }
}