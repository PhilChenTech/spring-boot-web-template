package com.nicenpc.adapterinbound.dto;

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
public class UserResponse {
    
    private Long id;
    private String name;
    private String email;
}
