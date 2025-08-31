package com.nicenpc.adapter.inbound.mapper;

import com.nicenpc.domain.User;
import com.nicenpc.adapter.inbound.dto.CreateUserRequest;
import com.nicenpc.adapter.inbound.dto.UserResponse;
import com.nicenpc.common.mapper.MapStructConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 使用者 DTO 映射器
 * 
 * <p>負責 Domain 實體與 DTO 之間的轉換。
 * 使用 MapStruct 自動生成映射程式碼，支援 Record 類型的 DTO。</p>
 * 
 * @author Nice NPC Team
 * @version 1.0
 * @since 1.0
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
     *       active 設為預設值 true
     *       審計欄位由系統自動填入
     * @param request 創建請求 DTO
     * @return 領域實體
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    User toDomain(CreateUserRequest request);
}
