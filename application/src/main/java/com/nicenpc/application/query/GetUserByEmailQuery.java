package com.nicenpc.application.query;

/**
 * 根據電子郵件查詢使用者
 */
public class GetUserByEmailQuery {
    
    private final String email;
    
    public GetUserByEmailQuery(String email) {
        this.email = email;
    }
    
    public String getEmail() {
        return email;
    }
}