package com.nicenpc.bootstrap.metrics;

import com.nicenpc.application.metrics.ApplicationMetrics;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 應用程式自定義指標實現
 */
@Component
public class ApplicationMetricsImpl implements ApplicationMetrics {
    
    private final Counter userCreationCounter;
    private final Counter userQueryCounter;
    private final Counter userUpdateCounter;
    private final Counter userDeleteCounter;
    private final Counter userOperationErrorCounter;
    
    private final Timer userCreationTimer;
    private final Timer userQueryTimer;
    private final Timer userUpdateTimer;
    private final Timer userDeleteTimer;
    private final Timer databaseOperationTimer;
    
    private final AtomicLong activeUsers = new AtomicLong(0);
    private final AtomicLong databaseConnectionPoolSize = new AtomicLong(0);
    
    // 系統資源指標
    private final Counter cacheHitCounter;
    private final Counter cacheMissCounter;
    private final Counter cacheEvictionCounter;
    
    private final MeterRegistry meterRegistry;
    
    public ApplicationMetricsImpl(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        
        // 使用者操作計數器
        this.userCreationCounter = Counter.builder("user.creation.count")
                .description("使用者建立總次數")
                .register(meterRegistry);
        
        this.userQueryCounter = Counter.builder("user.query.count")
                .description("使用者查詢總次數")
                .register(meterRegistry);
        
        this.userUpdateCounter = Counter.builder("user.update.count")
                .description("使用者更新總次數")
                .register(meterRegistry);
        
        this.userDeleteCounter = Counter.builder("user.delete.count")
                .description("使用者刪除總次數")
                .register(meterRegistry);
        
        this.userOperationErrorCounter = Counter.builder("user.operation.error.count")
                .description("使用者操作錯誤總次數")
                .register(meterRegistry);
        
        // 效能計時器
        this.userCreationTimer = Timer.builder("user.creation.time")
                .description("使用者建立耗時")
                .register(meterRegistry);
        
        this.userQueryTimer = Timer.builder("user.query.time")
                .description("使用者查詢耗時")
                .register(meterRegistry);
        
        this.userUpdateTimer = Timer.builder("user.update.time")
                .description("使用者更新耗時")
                .register(meterRegistry);
        
        this.userDeleteTimer = Timer.builder("user.delete.time")
                .description("使用者刪除耗時")
                .register(meterRegistry);
        
        this.databaseOperationTimer = Timer.builder("database.operation.time")
                .description("資料庫操作耗時")
                .register(meterRegistry);
        
        // 快取指標
        this.cacheHitCounter = Counter.builder("cache.hit.count")
                .description("快取命中次數")
                .register(meterRegistry);
        
        this.cacheMissCounter = Counter.builder("cache.miss.count")
                .description("快取未命中次數")
                .register(meterRegistry);
        
        this.cacheEvictionCounter = Counter.builder("cache.eviction.count")
                .description("快取逐出次數")
                .register(meterRegistry);
        
        // 系統狀態指標
        Gauge.builder("user.active.count", this, ApplicationMetricsImpl::getActiveUserCount)
                .description("目前活躍使用者數")
                .register(meterRegistry);
        
        Gauge.builder("database.connection.pool.size", this, ApplicationMetricsImpl::getDatabaseConnectionPoolSize)
                .description("資料庫連線池大小")
                .register(meterRegistry);
        
        // JVM 記憶體指標
        Gauge.builder("jvm.memory.used", this, impl -> {
                    Runtime runtime = Runtime.getRuntime();
                    return runtime.totalMemory() - runtime.freeMemory();
                })
                .description("JVM 使用記憶體")
                .register(meterRegistry);
        
        Gauge.builder("jvm.memory.max", this, impl -> Runtime.getRuntime().maxMemory())
                .description("JVM 最大記憶體")
                .register(meterRegistry);
    }
    
    @Override
    public void incrementUserCreation() {
        userCreationCounter.increment();
    }
    
    @Override
    public void incrementUserQuery() {
        userQueryCounter.increment();
    }
    
    @Override
    public TimerSample startUserCreationTimer() {
        Timer.Sample sample = Timer.start();
        return new TimerSampleImpl(sample);
    }
    
    @Override
    public void stopUserCreationTimer(TimerSample sample) {
        if (sample instanceof TimerSampleImpl impl) {
            impl.sample.stop(userCreationTimer);
        }
    }
    
    @Override
    public void setActiveUsers(long count) {
        activeUsers.set(count);
    }
    
    // 新增實作方法
    @Override
    public void incrementUserUpdate() {
        userUpdateCounter.increment();
    }
    
    @Override
    public void incrementUserDelete() {
        userDeleteCounter.increment();
    }
    
    @Override
    public void recordUserOperationError(String operation) {
        userOperationErrorCounter.increment();
    }
    
    @Override
    public TimerSample startUserQueryTimer() {
        Timer.Sample sample = Timer.start();
        return new TimerSampleImpl(sample);
    }
    
    @Override
    public void stopUserQueryTimer(TimerSample sample) {
        if (sample instanceof TimerSampleImpl impl) {
            impl.sample.stop(userQueryTimer);
        }
    }
    
    @Override
    public TimerSample startUserUpdateTimer() {
        Timer.Sample sample = Timer.start();
        return new TimerSampleImpl(sample);
    }
    
    @Override
    public void stopUserUpdateTimer(TimerSample sample) {
        if (sample instanceof TimerSampleImpl impl) {
            impl.sample.stop(userUpdateTimer);
        }
    }
    
    @Override
    public TimerSample startUserDeleteTimer() {
        Timer.Sample sample = Timer.start();
        return new TimerSampleImpl(sample);
    }
    
    @Override
    public void stopUserDeleteTimer(TimerSample sample) {
        if (sample instanceof TimerSampleImpl impl) {
            impl.sample.stop(userDeleteTimer);
        }
    }
    
    @Override
    public TimerSample startDatabaseOperationTimer() {
        Timer.Sample sample = Timer.start();
        return new TimerSampleImpl(sample);
    }
    
    @Override
    public void stopDatabaseOperationTimer(TimerSample sample) {
        if (sample instanceof TimerSampleImpl impl) {
            impl.sample.stop(databaseOperationTimer);
        }
    }
    
    @Override
    public void setDatabaseConnectionPoolSize(int size) {
        databaseConnectionPoolSize.set(size);
    }
    
    @Override
    public void recordResponseTime(String endpoint, long milliseconds) {
        // 這可以通過 Timer.recordCallable() 或類似方法實現
        Timer.builder("http.request.duration")
                .tag("endpoint", endpoint)
                .description("HTTP請求耗時")
                .register(null)  // 這裡需要 MeterRegistry 參考
                .record(milliseconds, java.util.concurrent.TimeUnit.MILLISECONDS);
    }
    
    @Override
    public void recordMemoryUsage(long usedMemory, long totalMemory) {
        // 這些已經在構造函式中作為 Gauge 註冊了
    }
    
    @Override
    public void recordCacheHit(String cacheName) {
        cacheHitCounter.increment();
    }
    
    @Override
    public void recordCacheMiss(String cacheName) {
        cacheMissCounter.increment();
    }
    
    @Override
    public void recordCacheEviction(String cacheName) {
        cacheEvictionCounter.increment();
    }
    
    private double getActiveUserCount() {
        return activeUsers.doubleValue();
    }
    
    private double getDatabaseConnectionPoolSize() {
        return databaseConnectionPoolSize.doubleValue();
    }
    
    private static class TimerSampleImpl implements TimerSample {
        private final Timer.Sample sample;
        
        public TimerSampleImpl(Timer.Sample sample) {
            this.sample = sample;
        }
    }
}