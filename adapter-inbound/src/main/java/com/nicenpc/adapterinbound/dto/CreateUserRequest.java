package com.nicenpc.adapterinbound.dto;

import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * 創建使用者請求 DTO
 * 屬於 Inbound Adapter 層，用於接收外部請求
 */
@Data
public class CreateUserRequest {
    
    @NotBlank(message = "姓名不能為空")
    private String name;
    
    @NotBlank(message = "Email 不能為空")
    @Email(message = "Email 格式不正確")
    private String email;
}
