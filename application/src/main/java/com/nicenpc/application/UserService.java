package com.nicenpc.application;

import com.nicenpc.application.bus.CommandBus;
import com.nicenpc.application.bus.QueryBus;
import com.nicenpc.application.command.CreateUserCommand;
import com.nicenpc.application.command.DeleteAllUsersCommand;
import com.nicenpc.application.query.*;
import com.nicenpc.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        commandBus.send(new CreateUserCommand(name, email));
        return getUserByEmail(email);
    }

    public long count() {
        return queryBus.send(new CountUsersQuery());
    }

    public List<User> findByEmailDomain(String domain) {
        return queryBus.send(new FindByEmailDomainQuery(domain));
    }

    public void deleteAll() {
        commandBus.send(new DeleteAllUsersCommand());
    }

    public boolean existsByEmail(String email) {
        return queryBus.send(new ExistsByEmailQuery(email));
    }
}