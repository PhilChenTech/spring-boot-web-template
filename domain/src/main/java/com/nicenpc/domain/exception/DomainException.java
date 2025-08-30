package com.nicenpc.domain.exception;

/**
 * 領域異常基類
 * 用於封裝領域層的業務規則違反
 */
public class DomainException extends RuntimeException {
    
    public DomainException(String message) {
        super(message);
    }
    
    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}