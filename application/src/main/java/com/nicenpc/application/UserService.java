package com.nicenpc.application;

import com.nicenpc.domain.User;
import com.nicenpc.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 使用者應用服務
 * 處理使用者相關的業務邏輯
 */
@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Transactional
    public User createUser(String name, String email) {
        // 建立Domain物件並進行驗證
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        
        // 使用Domain層的驗證邏輯
        user.validate();
        
        // 檢查email是否已存在
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("電子郵件已存在: " + email);
        }
        
        return userRepository.save(user);
    }

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
