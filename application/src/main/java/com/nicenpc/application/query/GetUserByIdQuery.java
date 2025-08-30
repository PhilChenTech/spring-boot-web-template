package com.nicenpc.application.query;

/**
 * 根據ID查詢使用者
 */
public class GetUserByIdQuery {
    
    private final Long userId;
    
    public GetUserByIdQuery(Long userId) {
        this.userId = userId;
    }
    
    public Long getUserId() {
        return userId;
    }
}