package com.nicenpc.application.metrics;

/**
 * 應用程式指標介面
 * 定義在 Application 層以遵循 Clean Architecture 原則
 */
public interface ApplicationMetrics {
    
    void incrementUserCreation();
    
    void incrementUserQuery();
    
    TimerSample startUserCreationTimer();
    
    void stopUserCreationTimer(TimerSample sample);
    
    void setActiveUsers(long count);
    
    /**
     * 計時器樣本介面
     */
    interface TimerSample {
    }
}