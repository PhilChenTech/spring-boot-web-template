package com.nicenpc.domain.exception;

/**
 * 使用者已存在異常
 * 當嘗試創建重複使用者時拋出
 */
public class UserAlreadyExistsException extends DomainException {
    
    private final String email;
    
    public UserAlreadyExistsException(String email) {
        super("使用者已存在: " + email);
        this.email = email;
    }
    
    public String getEmail() {
        return email;
    }
}