package com.nicenpc.adapteroutbound.repository;

import com.nicenpc.domain.User;
import com.nicenpc.domain.repository.UserRepository;
import com.nicenpc.adapteroutbound.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 使用者倉儲實作
 * 實作 domain 層的 UserRepository 介面，負責在 domain 實體和 JPA 實體間轉換
 */
@Component
public class UserRepositoryImpl implements UserRepository {
    
    private final UserJpaRepository userJpaRepository;
    
    @Autowired
    public UserRepositoryImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }
    
    @Override
    public User save(User user) {
        return UserMapper.toDomain(userJpaRepository.save(UserMapper.toEntity(user)));
    }
    
    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id).map(UserMapper::toDomain);
    }
    
    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email).map(UserMapper::toDomain);
    }
    
    @Override
    public List<User> findAll() {
        return userJpaRepository.findAll().stream()
                .map(UserMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<User> findByNameContainingIgnoreCase(String name) {
        return userJpaRepository.findByNameContainingIgnoreCase(name).stream()
                .map(UserMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }
    
    @Override
    public boolean existsById(Long id) {
        return userJpaRepository.existsById(id);
    }
    
    @Override
    public void deleteById(Long id) {
        userJpaRepository.deleteById(id);
    }
    
    @Override
    public void deleteAll() {
        userJpaRepository.deleteAll();
    }
    
    @Override
    public long count() {
        return userJpaRepository.count();
    }
    
    @Override
    public List<User> findByEmailDomain(String domain) {
        return userJpaRepository.findByEmailDomain(domain).stream()
                .map(UserMapper::toDomain)
                .collect(Collectors.toList());
    }
}
