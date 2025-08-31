package com.nicenpc.adapter.inbound.dto;

import java.time.Instant;

/**
 * 統一API回應格式
 *
 * <p>提供標準化的API回應結構，包含成功狀態、訊息、資料和時間戳記。
 * 遵循統一回應格式的設計原則，使用 Instant 處理時間以確保 UTC+0 標準。</p>
 *
 * @param <T> 回應資料的型別
 * @author Nice NPC Team
 * @version 1.0
 * @since 1.0
 */
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private Instant timestamp;
    private String path;
    private Metadata metadata;
    
    /**
     * 預設建構函數
     */
    public ApiResponse() {
        this.timestamp = Instant.now();
        this.success = true;
    }
    
    /**
     * 包含資料的建構函數
     *
     * @param data 回應資料
     */
    public ApiResponse(T data) {
        this();
        this.data = data;
    }
    
    /**
     * 包含資料和訊息的建構函數
     *
     * @param data 回應資料
     * @param message 回應訊息
     */
    public ApiResponse(T data, String message) {
        this();
        this.data = data;
        this.message = message;
    }
    
    /**
     * 完整的建構函數
     *
     * @param data 回應資料
     * @param message 回應訊息
     * @param path 請求路徑
     */
    public ApiResponse(T data, String message, String path) {
        this();
        this.data = data;
        this.message = message;
        this.path = path;
    }
    
    // 靜態工廠方法

    /**
     * 創建成功回應
     *
     * @param <T> 資料型別
     * @param data 回應資料
     * @return 成功的API回應
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data);
    }
    
    /**
     * 創建包含訊息的成功回應
     *
     * @param <T> 資料型別
     * @param data 回應資料
     * @param message 成功訊息
     * @return 成功的API回應
     */
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(data, message);
    }
    
    /**
     * 創建包含路徑的成功回應
     *
     * @param <T> 資料型別
     * @param data 回應資料
     * @param message 成功訊息
     * @param path 請求路徑
     * @return 成功的API回應
     */
    public static <T> ApiResponse<T> success(T data, String message, String path) {
        return new ApiResponse<>(data, message, path);
    }
    
    /**
     * 創建無資料的成功回應
     *
     * @return 成功的API回應
     */
    public static ApiResponse<Void> success() {
        return new ApiResponse<>(null, "操作成功");
    }
    
    /**
     * 創建包含訊息的無資料成功回應
     *
     * @param message 成功訊息
     * @return 成功的API回應
     */
    public static ApiResponse<Void> success(String message) {
        return new ApiResponse<>(null, message);
    }
    
    /**
     * 創建錯誤回應
     *
     * @param message 錯誤訊息
     * @return 錯誤的API回應
     */
    public static ApiResponse<Void> error(String message) {
        ApiResponse<Void> response = new ApiResponse<>();
        response.setSuccess(false);
        response.setMessage(message);
        return response;
    }

    // Getters and Setters

    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
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
    
    public Metadata getMetadata() {
        return metadata;
    }
    
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
    
    /**
     * 元資料類別
     *
     * <p>包含分頁、排序等額外資訊。</p>
     */
    public static class Metadata {

        private Long totalElements;
        private Integer totalPages;
        private Integer currentPage;
        private Integer pageSize;
        private String sortBy;
        private String sortDirection;
        private String version;
        
        /**
         * 預設建構函數
         */
        public Metadata() {}
        
        /**
         * 分頁建構函數
         *
         * @param totalElements 總元素數
         * @param totalPages 總頁數
         * @param currentPage 當前頁數
         * @param pageSize 每頁大小
         */
        public Metadata(Long totalElements, Integer totalPages, Integer currentPage, Integer pageSize) {
            this.totalElements = totalElements;
            this.totalPages = totalPages;
            this.currentPage = currentPage;
            this.pageSize = pageSize;
        }
        
        // Getters and Setters

        public Long getTotalElements() {
            return totalElements;
        }
        
        public void setTotalElements(Long totalElements) {
            this.totalElements = totalElements;
        }
        
        public Integer getTotalPages() {
            return totalPages;
        }
        
        public void setTotalPages(Integer totalPages) {
            this.totalPages = totalPages;
        }
        
        public Integer getCurrentPage() {
            return currentPage;
        }
        
        public void setCurrentPage(Integer currentPage) {
            this.currentPage = currentPage;
        }
        
        public Integer getPageSize() {
            return pageSize;
        }
        
        public void setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
        }
        
        public String getSortBy() {
            return sortBy;
        }
        
        public void setSortBy(String sortBy) {
            this.sortBy = sortBy;
        }
        
        public String getSortDirection() {
            return sortDirection;
        }
        
        public void setSortDirection(String sortDirection) {
            this.sortDirection = sortDirection;
        }
        
        public String getVersion() {
            return version;
        }
        
        public void setVersion(String version) {
            this.version = version;
        }
    }
}
