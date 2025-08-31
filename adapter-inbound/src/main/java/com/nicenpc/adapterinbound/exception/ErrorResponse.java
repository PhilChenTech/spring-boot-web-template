package com.nicenpc.adapterinbound.exception;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

/**
 * API 錯誤響應格式
 * 提供結構化的錯誤信息，包含錯誤代碼、追蹤ID等
 * 使用 Instant 確保時間處理符合 UTC+0 標準
 */
public class ErrorResponse {
    private String errorCode;
    private String error;
    private String message;
    private String details;
    private int status;
    private Instant timestamp;
    private String path;
    private String traceId;
    private Map<String, Object> additionalInfo;
    
    public ErrorResponse() {
        this.timestamp = Instant.now();
        this.traceId = UUID.randomUUID().toString();
    }
    
    public ErrorResponse(String error, String message, int status, String path) {
        this();
        this.error = error;
        this.message = message;
        this.status = status;
        this.path = path;
    }
    
    public ErrorResponse(ErrorCode errorCode, String details, int status, String path) {
        this();
        this.errorCode = errorCode.getCode();
        this.error = errorCode.name();
        this.message = errorCode.getMessage();
        this.details = details;
        this.status = status;
        this.path = path;
    }
    
    // Getters and Setters
    public String getError() {
        return error;
    }
    
    public void setError(String error) {
        this.error = error;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public int getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    
    public Instant getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
    
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    
    public String getDetails() {
        return details;
    }
    
    public void setDetails(String details) {
        this.details = details;
    }
    
    public String getTraceId() {
        return traceId;
    }
    
    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }
    
    public Map<String, Object> getAdditionalInfo() {
        return additionalInfo;
    }
    
    public void setAdditionalInfo(Map<String, Object> additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
