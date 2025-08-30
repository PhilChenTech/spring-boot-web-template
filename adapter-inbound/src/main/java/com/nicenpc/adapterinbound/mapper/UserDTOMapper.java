package com.nicenpc.adapterinbound.mapper;

import com.nicenpc.domain.User;
import com.nicenpc.adapterinbound.dto.CreateUserRequest;
import com.nicenpc.adapterinbound.dto.UserResponse;
import com.nicenpc.common.mapper.MapStructConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 使用者 DTO 映射器
 * 負責 Domain 實體與 DTO 之間的轉換
 * 使用 MapStruct 自動生成映射程式碼
 */
@Mapper(config = MapStructConfig.class)
public interface UserDTOMapper {
    
    UserDTOMapper INSTANCE = Mappers.getMapper(UserDTOMapper.class);
    
    /**
     * 將領域實體轉換為回應 DTO
     * @param user 領域實體
     * @return 回應 DTO
     */
    UserResponse toResponse(User user);
    
    /**
     * 將創建請求 DTO 轉換為領域實體
     * 注意：id 設為 null，因為創建時還沒有 id
     * @param request 創建請求 DTO
     * @return 領域實體
     */
    @Mapping(target = "id", ignore = true)
    User toDomain(CreateUserRequest request);
}
