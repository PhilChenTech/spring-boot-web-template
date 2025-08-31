package com.nicenpc.adapterinbound.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 創建使用者請求DTO
 *
 * <p>用於 Inbound Adapter 層接收創建使用者的請求參數。
 * 使用 Record 實現不可變的 DTO，包含必要的驗證註解確保資料完整性。</p>
 *
 * @param name 使用者姓名，不能為空且長度在 2-100 個字符之間
 * @param email 使用者信箱，必須符合標準信箱格式
 * @author Nice NPC Team
 * @version 1.0
 * @since 1.0
 */
@Schema(description = "創建使用者請求")
public record CreateUserRequest(
    @NotBlank(message = "姓名不能為空")
    @Size(min = 2, max = 100, message = "姓名長度必須在 2-100 個字符之間")
    @Schema(description = "使用者姓名", example = "張三", required = true)
    String name,
    
    @NotBlank(message = "Email 不能為空")
    @Email(message = "Email 格式不正確")
    @Schema(description = "電子郵件地址", example = "zhang.san@example.com", required = true)
    String email
) {
    /**
     * 緊湊建構函數，用於驗證
     */
    public CreateUserRequest {
        if (name != null) {
            name = name.trim();
        }
        if (email != null) {
            email = email.trim().toLowerCase();
        }
    }
    
    /**
     * 工廠方法：創建新的請求實例
     */
    public static CreateUserRequest of(String name, String email) {
        return new CreateUserRequest(name, email);
    }
}
