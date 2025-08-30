package com.nicenpc.application.command;

/**
 * 刪除使用者指令
 */
public class DeleteUserCommand {
    
    private final Long userId;
    
    public DeleteUserCommand(Long userId) {
        this.userId = userId;
    }
    
    public Long getUserId() {
        return userId;
    }
}