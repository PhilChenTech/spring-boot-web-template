package com.nicenpc.application.handler;

import com.nicenpc.application.command.DeleteUserCommand;
import com.nicenpc.application.metrics.ApplicationMetrics;
import com.nicenpc.domain.User;
import com.nicenpc.domain.exception.UserNotFoundException;
import com.nicenpc.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 刪除使用者指令處理器
 */
@Component
@RequiredArgsConstructor
public class DeleteUserCommandHandler implements CommandHandler<DeleteUserCommand> {
    
    private final UserRepository userRepository;
    private final ApplicationMetrics applicationMetrics;
    
    @Override
    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public void handle(DeleteUserCommand command) {
        ApplicationMetrics.TimerSample sample = applicationMetrics.startUserDeleteTimer();
        
        try {
            // 檢查使用者是否存在
            userRepository.findById(command.getUserId())
                .orElseThrow(() -> new UserNotFoundException(command.getUserId()));
            
            // 刪除使用者
            userRepository.deleteById(command.getUserId());
            
            // 記錄成功指標
            applicationMetrics.incrementUserDelete();
            
        } finally {
            // 記錄執行時間
            applicationMetrics.stopUserDeleteTimer(sample);
        }
    }
}