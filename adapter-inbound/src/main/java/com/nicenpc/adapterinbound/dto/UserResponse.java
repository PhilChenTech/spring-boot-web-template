package com.nicenpc.adapterinbound.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 使用者回應 DTO
 * 屬於 Inbound Adapter 層，用於回應外部請求
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "使用者回應資料")
public class UserResponse {
    
    @Schema(description = "使用者 ID", example = "1")
    private Long id;
    
    @Schema(description = "使用者名稱", example = "張三")
    private String name;
    
    @Schema(description = "電子郵件", example = "zhang.san@example.com")
    private String email;
}
