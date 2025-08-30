package com.nicenpc.application;

import com.nicenpc.application.bus.CommandBus;
import com.nicenpc.application.bus.QueryBus;
import com.nicenpc.application.command.CreateUserCommand;
import com.nicenpc.application.query.GetAllUsersQuery;
import com.nicenpc.application.query.GetUserByEmailQuery;
import com.nicenpc.application.query.GetUserByIdQuery;
import com.nicenpc.domain.User;
import com.nicenpc.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 使用者應用服務
 * 使用 CQRS 模式處理使用者相關的業務邏輯
 */
@Service
@RequiredArgsConstructor
public class UserService {
    
    private final CommandBus commandBus;
    private final QueryBus queryBus;
    
    // 保留部分直接存取方法以維持現有API兼容性
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return queryBus.send(new GetAllUsersQuery());
    }

    public User getUserById(Long id) {
        return queryBus.send(new GetUserByIdQuery(id));
    }

    public User getUserByEmail(String email) {
        return queryBus.send(new GetUserByEmailQuery(email));
    }

    public User createUser(String name, String email) {
        // 使用命令匯流排發送建立使用者指令
        commandBus.send(new CreateUserCommand(name, email));
        
        // 查詢剛建立的使用者（在實際應用中，可能需要返回建立的使用者ID）
        return getUserByEmail(email);
    }

    // 保留一些直接方法以維持現有功能
    @Transactional(readOnly = true)
    public long count() {
        return userRepository.count();
    }

    @Transactional(readOnly = true)
    public List<User> findByEmailDomain(String domain) {
        return userRepository.findByEmailDomain(domain);
    }

    @Transactional
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
