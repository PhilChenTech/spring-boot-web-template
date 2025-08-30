package com.nicenpc.application.command;

/**
 * 更新使用者指令
 */
public class UpdateUserCommand {
    
    private final Long userId;
    private final String name;
    private final String email;
    
    public UpdateUserCommand(Long userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public String getName() {
        return name;
    }
    
    public String getEmail() {
        return email;
    }
}