package com.nicenpc.domain.repository;

import com.nicenpc.domain.User;

import java.util.List;
import java.util.Optional;

/**
 * 使用者倉儲介面
 * 定義在 domain 層，由 adapter.outbound 層實作
 */
public interface UserRepository {
    
    User save(User user);
    
    Optional<User> findById(Long id);
    
    Optional<User> findByEmail(String email);
    
    List<User> findAll();
    
    List<User> findByNameContainingIgnoreCase(String name);
    
    boolean existsByEmail(String email);
    
    boolean existsById(Long id);
    
    void deleteById(Long id);
    
    void deleteAll();
    
    long count();
    
    List<User> findByEmailDomain(String domain);
}
