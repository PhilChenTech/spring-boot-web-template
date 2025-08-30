package com.nicenpc.adapterinbound.dto;

import java.time.LocalDateTime;

/**
 * 統一API回應格式
 * 為所有成功的API回應提供一致的結構
 */
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;
    private String path;
    private Metadata metadata;
    
    public ApiResponse() {
        this.timestamp = LocalDateTime.now();
        this.success = true;
    }
    
    public ApiResponse(T data) {
        this();
        this.data = data;
    }
    
    public ApiResponse(T data, String message) {
        this();
        this.data = data;
        this.message = message;
    }
    
    public ApiResponse(T data, String message, String path) {
        this();
        this.data = data;
        this.message = message;
        this.path = path;
    }
    
    // 靜態工廠方法
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data);
    }
    
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(data, message);
    }
    
    public static <T> ApiResponse<T> success(T data, String message, String path) {
        return new ApiResponse<>(data, message, path);
    }
    
    public static ApiResponse<Void> success() {
        return new ApiResponse<>(null, "操作成功");
    }
    
    public static ApiResponse<Void> success(String message) {
        return new ApiResponse<>(null, message);
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
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
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
     * 回應元資料
     * 包含分頁、排序等額外資訊
     */
    public static class Metadata {
        private Long totalElements;
        private Integer totalPages;
        private Integer currentPage;
        private Integer pageSize;
        private String sortBy;
        private String sortDirection;
        private String version;
        
        public Metadata() {}
        
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