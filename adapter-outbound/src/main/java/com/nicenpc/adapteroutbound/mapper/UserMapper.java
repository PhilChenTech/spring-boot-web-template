package com.nicenpc.adapteroutbound.mapper;

import com.nicenpc.domain.User;
import com.nicenpc.adapteroutbound.entity.UserEntity;

/**
 * 使用者映射器
 * 負責 Domain 實體與 JPA 實體之間的轉換
 */
public class UserMapper {
    
    public static User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return new User(entity.getId(), entity.getName(), entity.getEmail());
    }
    
    public static UserEntity toEntity(User domain) {
        if (domain == null) {
            return null;
        }
        return new UserEntity(domain.getId(), domain.getName(), domain.getEmail());
    }
}
