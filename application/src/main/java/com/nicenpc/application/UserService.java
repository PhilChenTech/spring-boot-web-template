package com.nicenpc.application;

import com.nicenpc.application.bus.CommandBus;
import com.nicenpc.application.bus.QueryBus;
import com.nicenpc.application.command.CreateUserCommand;
import com.nicenpc.application.command.UpdateUserCommand;
import com.nicenpc.application.command.DeleteUserCommand;
import com.nicenpc.application.command.DeleteAllUsersCommand;
import com.nicenpc.application.query.*;
import com.nicenpc.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 使用者應用服務
 *
 * <p>實現 CQRS 模式，協調指令和查詢的處理。
 * 此服務作為應用層的入口點，負責業務流程的編排。</p>
 *
 * @author Nice NPC Team
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final CommandBus commandBus;
    private final QueryBus queryBus;

    /**
     * 取得所有使用者
     *
     * @return 所有使用者清單
     */
    public List<User> getAllUsers() {
        return queryBus.send(new GetAllUsersQuery());
    }

    /**
     * 根據ID取得使用者
     *
     * @param id 使用者ID
     * @return 使用者實體
     */
    public User getUserById(Long id) {
        return queryBus.send(new GetUserByIdQuery(id));
    }

    /**
     * 根據信箱取得使用者
     *
     * @param email 使用者信箱
     * @return 使用者實體
     */
    public User getUserByEmail(String email) {
        return queryBus.send(new GetUserByEmailQuery(email));
    }

    /**
     * 創建新使用者
     *
     * @param name 使用者姓名
     * @param email 使用者信箱
     * @return 創建的使用者實體
     */
    public User createUser(String name, String email) {
        commandBus.send(new CreateUserCommand(name, email));
        return getUserByEmail(email);
    }

    /**
     * 計算使用者總數
     *
     * @return 使用者總數
     */
    public long count() {
        return queryBus.send(new CountUsersQuery());
    }

    /**
     * 根據信箱網域查找使用者
     *
     * @param domain 信箱網域
     * @return 符合條件的使用者清單
     */
    public List<User> findByEmailDomain(String domain) {
        return queryBus.send(new FindByEmailDomainQuery(domain));
    }

    /**
     * 刪除所有使用者
     */
    public void deleteAll() {
        commandBus.send(new DeleteAllUsersCommand());
    }

    /**
     * 檢查指定信箱的使用者是否存在
     *
     * @param email 使用者信箱
     * @return true 如果存在，否則返回 false
     */
    public boolean existsByEmail(String email) {
        return queryBus.send(new ExistsByEmailQuery(email));
    }
    
    /**
     * 更新使用者資訊
     *
     * @param userId 使用者ID
     * @param name 新的姓名
     * @param email 新的信箱
     * @return 更新後的使用者實體
     */
    public User updateUser(Long userId, String name, String email) {
        commandBus.send(new UpdateUserCommand(userId, name, email));
        return getUserById(userId);
    }
    
    /**
     * 刪除指定使用者
     *
     * @param userId 使用者ID
     */
    public void deleteUser(Long userId) {
        commandBus.send(new DeleteUserCommand(userId));
    }
}