package com.nicenpc.adapter.inbound.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

/**
 * 使用者回應 DTO
 * 
 * <p>屬於 Inbound Adapter 層，用於回應外部請求。
 * 使用 Record 實現不可變的回應 DTO，符合統一回應格式標準。</p>
 * 
 * @param id 使用者ID
 * @param name 使用者名稱
 * @param email 電子郵件
 * @param active 是否啟用
 * @param createdAt 建立時間（UTC+0）
 * @param updatedAt 更新時間（UTC+0）
 * @param version 版本號
 * @author Nice NPC Team
 * @version 1.0
 * @since 1.0
 */
@Schema(description = "使用者回應資料")
public record UserResponse(
    @Schema(description = "使用者 ID", example = "1")
    Long id,
    
    @Schema(description = "使用者名稱", example = "張三")
    String name,
    
    @Schema(description = "電子郵件", example = "zhang.san@example.com")
    String email,
    
    @Schema(description = "是否啟用", example = "true")
    Boolean active,
    
    @Schema(description = "建立時間", example = "2023-01-01T00:00:00Z")
    Instant createdAt,
    
    @Schema(description = "更新時間", example = "2023-01-01T00:00:00Z")
    Instant updatedAt,
    
    @Schema(description = "版本號", example = "1")
    Integer version
) {
    /**
     * 工廠方法：從基本資訊創建回應
     */
    public static UserResponse of(Long id, String name, String email) {
        return new UserResponse(id, name, email, true, null, null, 0);
    }
    
    /**
     * 檢查使用者是否為新用戶（版本號為0）
     */
    public boolean isNewUser() {
        return version != null && version == 0;
    }
}
