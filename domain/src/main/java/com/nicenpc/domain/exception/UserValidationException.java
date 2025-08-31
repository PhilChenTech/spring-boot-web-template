package com.nicenpc.domain.exception;

/**
 * 使用者驗證異常
 *
 * <p>當使用者資料驗證失敗時拋出的異常。
 * 用於標識違反業務規則的驗證錯誤。</p>
 *
 * @author Nice NPC Team
 * @version 1.0
 * @since 1.0
 */
public class UserValidationException extends DomainException {
    
    /**
     * 創建使用者驗證異常
     *
     * @param message 異常訊息
     */
    public UserValidationException(String message) {
        super(message);
    }
    
    /**
     * 創建使用者驗證異常
     *
     * @param message 異常訊息
     * @param cause 引起異常的原因
     */
    public UserValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}