package com.nicenpc.domain.exception;

/**
 * 使用者已存在異常
 *
 * <p>當嘗試創建已存在的使用者時拋出的異常。
 * 通常在Email重複時觸發。</p>
 *
 * @author Nice NPC Team
 * @version 1.0
 * @since 1.0
 */
public class UserAlreadyExistsException extends DomainException {
    
    private final String email;
    
    /**
     * 創建使用者已存在異常
     *
     * @param email 已存在的使用者Email
     */
    public UserAlreadyExistsException(String email) {
        super("使用者已存在: " + email);
        this.email = email;
    }
    
    /**
     * 創建使用者已存在異常（自訂訊息）
     *
     * @param message 自訂異常訊息
     * @param email 已存在的使用者Email
     */
    public UserAlreadyExistsException(String message, String email) {
        super(message);
        this.email = email;
    }

    /**
     * 取得已存在的使用者Email
     *
     * @return 使用者Email
     */
    public String getEmail() {
        return email;
    }
}