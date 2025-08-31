package com.nicenpc.adapterinbound.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 創建使用者請求DTO
 *
 * <p>用於 Inbound Adapter 層接收創建使用者的請求參數。
 * 包含必要的驗證註解確保資料完整性。</p>
 *
 * @author Nice NPC Team
 * @version 1.0
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "創建使用者請求")
public class CreateUserRequest {
    
    @NotBlank(message = "姓名不能為空")
    @Size(min = 2, max = 100, message = "姓名長度必須在 2-100 個字符之間")
    @Schema(description = "使用者姓名", example = "張三", required = true)
    private String name;
    
    @NotBlank(message = "Email 不能為空")
    @Email(message = "Email 格式不正確")
    @Schema(description = "電子郵件地址", example = "zhang.san@example.com", required = true)
    private String email;
}
