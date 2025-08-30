package com.nicenpc.adapterinbound.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * 創建使用者請求 DTO
 * 屬於 Inbound Adapter 層，用於接收外部請求
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "建立使用者請求資料")
public class CreateUserRequest {
    
    @NotBlank(message = "姓名不能為空")
    @Schema(description = "使用者名稱", example = "張三", required = true)
    private String name;
    
    @NotBlank(message = "Email 不能為空")
    @Email(message = "Email 格式不正確")
    @Schema(description = "電子郵件", example = "zhang.san@example.com", required = true)
    private String email;
}
