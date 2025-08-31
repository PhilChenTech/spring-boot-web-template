package com.nicenpc.infrastructure.adapter.inbound.exception;

/**
 * 統一錯誤代碼定義
 * 為不同類型的錯誤提供標準化的錯誤代碼
 */
public enum ErrorCode {
    
    // 通用錯誤 (1xxx)
    INTERNAL_SERVER_ERROR("1001", "內部伺服器錯誤"),
    BAD_REQUEST("1002", "請求參數錯誤"),
    UNAUTHORIZED("1003", "未授權訪問"),
    FORBIDDEN("1004", "禁止訪問"),
    NOT_FOUND("1005", "資源不存在"),
    METHOD_NOT_ALLOWED("1006", "請求方法不被允許"),
    VALIDATION_ERROR("1007", "資料驗證失敗"),
    
    // 使用者相關錯誤 (2xxx)
    USER_NOT_FOUND("2001", "使用者不存在"),
    USER_ALREADY_EXISTS("2002", "使用者已存在"),
    USER_VALIDATION_ERROR("2003", "使用者資料驗證失敗"),
    INVALID_USER_EMAIL("2004", "無效的電子郵件格式"),
    INVALID_USER_NAME("2005", "無效的使用者名稱"),
    USER_CREATION_FAILED("2006", "使用者建立失敗"),
    USER_UPDATE_FAILED("2007", "使用者更新失敗"),
    USER_DELETE_FAILED("2008", "使用者刪除失敗"),
    
    // 系統錯誤 (3xxx)
    DATABASE_ERROR("3001", "資料庫操作錯誤"),
    EXTERNAL_SERVICE_ERROR("3002", "外部服務呼叫錯誤"),
    CACHE_ERROR("3003", "快取操作錯誤"),
    CONFIGURATION_ERROR("3004", "系統配置錯誤"),
    
    // 業務邏輯錯誤 (4xxx)
    BUSINESS_RULE_VIOLATION("4001", "業務規則違反"),
    OPERATION_NOT_PERMITTED("4002", "操作不被允許"),
    RESOURCE_CONFLICT("4003", "資源衝突"),
    INSUFFICIENT_PERMISSIONS("4004", "權限不足");
    
    private final String code;
    private final String message;
    
    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getMessage() {
        return message;
    }
    
    @Override
    public String toString() {
        return String.format("[%s] %s", code, message);
    }
}
