package com.nicenpc.domain.exception;

/**
 * 領域異常基礎類
 *
 * <p>所有領域層異常的基礎父類，用於標識業務邏輯相關的異常。
 * 遵循 Domain-Driven Design 的異常處理原則。</p>
 *
 * @author Nice NPC Team
 * @version 1.0
 * @since 1.0
 */
public abstract class DomainException extends RuntimeException {

    /**
     * 創建領域異常
     *
     * @param message 異常訊息
     */
    protected DomainException(String message) {
        super(message);
    }
    
    /**
     * 創建領域異常
     *
     * @param message 異常訊息
     * @param cause 引起異常的原因
     */
    protected DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}