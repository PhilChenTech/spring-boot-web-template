package com.nicenpc.domain.exception;

/**
 * 使用者不存在異常
 * 當查詢的使用者不存在時拋出
 */
public class UserNotFoundException extends DomainException {
    
    private final Long userId;
    
    public UserNotFoundException(Long userId) {
        super("使用者不存在: ID = " + userId);
        this.userId = userId;
    }
    
    public UserNotFoundException(String email) {
        super("使用者不存在: Email = " + email);
        this.userId = null;
    }
    
    public Long getUserId() {
        return userId;
    }
}