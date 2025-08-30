package com.nicenpc.domain.exception;

/**
 * 使用者驗證異常
 * 當使用者資料不符合業務規則時拋出
 */
public class UserValidationException extends DomainException {
    
    public UserValidationException(String message) {
        super(message);
    }
    
    public UserValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}