package com.nicenpc.adapterinbound.controller;

import com.nicenpc.domain.User;
import com.nicenpc.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/h2-demo")
public class H2DemoController {

    @Autowired
    private UserRepository userRepository;

    /**
     * 獲取資料庫統計信息
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getDatabaseStats() {
        long totalUsers = userRepository.count();
        List<User> allUsers = userRepository.findAll();
        
        Map<String, Object> stats = Map.of(
            "totalUsers", totalUsers,
            "users", allUsers,
            "h2ConsoleUrl", "/h2-console",
            "databaseUrl", "jdbc:h2:mem:testdb",
            "username", "sa",
            "password", ""
        );
        
        return ResponseEntity.ok(stats);
    }

    /**
     * 根據email域名搜尋用戶
     */
    @GetMapping("/search-by-domain")
    public ResponseEntity<List<User>> searchByEmailDomain(@RequestParam String domain) {
        List<User> users = userRepository.findByEmailDomain(domain);
        return ResponseEntity.ok(users);
    }

    /**
     * 批量創建測試用戶
     */
    @PostMapping("/create-test-users")
    public ResponseEntity<List<User>> createTestUsers() {
        List<User> testUsers = List.of(
            createUserIfNotExists("測試用戶1", "test1@example.com"),
            createUserIfNotExists("測試用戶2", "test2@example.com"),
            createUserIfNotExists("測試用戶3", "test3@nicenpc.com")
        );
        
        return ResponseEntity.ok(testUsers);
    }

    /**
     * 清空所有用戶（僅用於測試）
     */
    @DeleteMapping("/clear-all")
    public ResponseEntity<String> clearAllUsers() {
        userRepository.deleteAll();
        return ResponseEntity.ok("所有用戶數據已清除");
    }

    private User createUserIfNotExists(String name, String email) {
        if (!userRepository.existsByEmail(email)) {
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            return userRepository.save(user);
        }
        return userRepository.findByEmail(email).orElse(null);
    }
}
