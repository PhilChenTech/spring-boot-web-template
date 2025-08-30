package com.nicenpc.application.metrics;

/**
 * 應用程式指標介面
 * 定義在 Application 層以遵循 Clean Architecture 原則
 */
public interface ApplicationMetrics {
    
    // 使用者相關指標
    void incrementUserCreation();
    
    void incrementUserQuery();
    
    void incrementUserUpdate();
    
    void incrementUserDelete();
    
    void recordUserOperationError(String operation);
    
    // 效能計時器
    TimerSample startUserCreationTimer();
    
    void stopUserCreationTimer(TimerSample sample);
    
    TimerSample startUserQueryTimer();
    
    void stopUserQueryTimer(TimerSample sample);
    
    TimerSample startUserUpdateTimer();
    
    void stopUserUpdateTimer(TimerSample sample);
    
    TimerSample startUserDeleteTimer();
    
    void stopUserDeleteTimer(TimerSample sample);
    
    TimerSample startDatabaseOperationTimer();
    
    void stopDatabaseOperationTimer(TimerSample sample);
    
    // 系統狀態指標
    void setActiveUsers(long count);
    
    void setDatabaseConnectionPoolSize(int size);
    
    void recordResponseTime(String endpoint, long milliseconds);
    
    void recordMemoryUsage(long usedMemory, long totalMemory);
    
    // 快取相關指標
    void recordCacheHit(String cacheName);
    
    void recordCacheMiss(String cacheName);
    
    void recordCacheEviction(String cacheName);
    
    /**
     * 計時器樣本介面
     */
    interface TimerSample {
    }
}