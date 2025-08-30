package com.nicenpc.adapteroutbound.mapper;

import com.nicenpc.domain.User;
import com.nicenpc.adapteroutbound.entity.UserEntity;
import com.nicenpc.common.mapper.MapStructConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 使用者映射器
 * 負責 Domain 實體與 JPA 實體之間的轉換
 * 使用 MapStruct 自動生成映射程式碼
 */
@Mapper(config = MapStructConfig.class)
public interface UserMapper {
    
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    
    /**
     * 將 JPA 實體轉換為領域實體
     * @param entity JPA 實體
     * @return 領域實體
     */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    User toDomain(UserEntity entity);
    
    /**
     * 將領域實體轉換為 JPA 實體
     * @param domain 領域實體
     * @return JPA 實體
     */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserEntity toEntity(User domain);
}
