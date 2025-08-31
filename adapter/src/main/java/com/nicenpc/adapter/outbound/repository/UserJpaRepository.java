package com.nicenpc.adapter.outbound.repository;

import com.nicenpc.adapter.outbound.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA 使用者倉儲
 * 基於 UserEntity 的資料存取層實作
 */
@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    
    /**
     * 根據email查找用戶
     */
    @Query("SELECT u FROM UserEntity u WHERE u.email = :email")
    Optional<UserEntity> findByEmail(@Param("email") String email);
    
    /**
     * 根據姓名模糊查詢用戶
     */
    @Query("SELECT u FROM UserEntity u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<UserEntity> findByNameContainingIgnoreCase(@Param("name") String name);
    
    /**
     * 檢查email是否已存在
     */
    @Query("SELECT COUNT(u) > 0 FROM UserEntity u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);
    
    /**
     * 自定義查詢：根據email域名查找用戶
     */
    @Query("SELECT u FROM UserEntity u WHERE u.email LIKE %:domain")
    List<UserEntity> findByEmailDomain(@Param("domain") String domain);
    
    /**
     * 查找所有啟用的用戶
     */
    @Query("SELECT u FROM UserEntity u WHERE u.active = true")
    List<UserEntity> findAllActiveUsers();
}
