package com.nicenpc.domain.exception;

/**
 * 使用者不存在異常
 *
 * <p>當嘗試查找不存在的使用者時拋出的異常。
 * 提供使用者ID或Email的詳細資訊。</p>
 *
 * @author Nice NPC Team
 * @version 1.0
 * @since 1.0
 */
public class UserNotFoundException extends DomainException {
    
    private final Long userId;
    private final String email;

    /**
     * 創建使用者不存在異常（根據ID）
     *
     * @param userId 使用者ID
     */
    public UserNotFoundException(Long userId) {
        super("找不到使用者: ID = " + userId);
        this.userId = userId;
        this.email = null;
    }
    
    /**
     * 創建使用者不存在異常（根據Email）
     *
     * @param email 使用者Email
     */
    public UserNotFoundException(String email) {
        super("找不到使用者: Email = " + email);
        this.userId = null;
        this.email = email;
    }

    /**
     * 創建使用者不存在異常（自訂訊息）
     *
     * @param message 自訂異常訊息
     * @param userId 使用者ID
     */
    public UserNotFoundException(String message, Long userId) {
        super(message);
        this.userId = userId;
        this.email = null;
    }
    
    /**
     * 取得使用者ID
     *
     * @return 使用者ID，可能為 null
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 取得使用者Email
     *
     * @return 使用者Email，可能為 null
     */
    public String getEmail() {
        return email;
    }
}