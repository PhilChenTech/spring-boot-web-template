package com.nicenpc.infrastructure.metrics;

import com.nicenpc.application.metrics.ApplicationMetrics;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Micrometer 應用程式指標實作
 * 
 * <p>使用 Micrometer 作為底層指標收集框架，
 * 實作 ApplicationMetrics 介面以提供應用程式層所需的指標功能。</p>
 *
 * @author Nice NPC Team
 * @version 1.0
 * @since 1.0
 */
@Component
public class MicrometerApplicationMetrics implements ApplicationMetrics {

    private final MeterRegistry meterRegistry;
    
    // 計數器
    private final Counter userCreationCounter;
    private final Counter userQueryCounter;
    private final Counter userUpdateCounter;
    private final Counter userDeleteCounter;
    
    // 計時器
    private final Timer userCreationTimer;
    private final Timer userQueryTimer;
    private final Timer userUpdateTimer;
    private final Timer userDeleteTimer;
    private final Timer databaseOperationTimer;
    
    // 原子變數用於 Gauge
    private final AtomicLong activeUsersCount = new AtomicLong(0);
    private final AtomicLong databaseConnectionPoolSize = new AtomicLong(0);
    private final AtomicLong usedMemory = new AtomicLong(0);
    private final AtomicLong totalMemory = new AtomicLong(0);

    public MicrometerApplicationMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        
        // 初始化計數器
        this.userCreationCounter = Counter.builder("user.creation.count")
                .description("Number of user creation operations")
                .register(meterRegistry);
                
        this.userQueryCounter = Counter.builder("user.query.count")
                .description("Number of user query operations")
                .register(meterRegistry);
                
        this.userUpdateCounter = Counter.builder("user.update.count")
                .description("Number of user update operations")
                .register(meterRegistry);
                
        this.userDeleteCounter = Counter.builder("user.delete.count")
                .description("Number of user delete operations")
                .register(meterRegistry);
        
        // 初始化計時器
        this.userCreationTimer = Timer.builder("user.creation.time")
                .description("Time taken for user creation operations")
                .register(meterRegistry);
                
        this.userQueryTimer = Timer.builder("user.query.time")
                .description("Time taken for user query operations")
                .register(meterRegistry);
                
        this.userUpdateTimer = Timer.builder("user.update.time")
                .description("Time taken for user update operations")
                .register(meterRegistry);
                
        this.userDeleteTimer = Timer.builder("user.delete.time")
                .description("Time taken for user delete operations")
                .register(meterRegistry);
                
        this.databaseOperationTimer = Timer.builder("database.operation.time")
                .description("Time taken for database operations")
                .register(meterRegistry);
        
        // 註冊 Gauge
        Gauge.builder("user.active.count", activeUsersCount, AtomicLong::get)
                .description("Number of active users")
                .register(meterRegistry);
                
        Gauge.builder("database.connection.pool.size", databaseConnectionPoolSize, AtomicLong::get)
                .description("Database connection pool size")
                .register(meterRegistry);
                
        Gauge.builder("memory.used", usedMemory, AtomicLong::get)
                .description("Used memory in bytes")
                .register(meterRegistry);
                
        Gauge.builder("memory.total", totalMemory, AtomicLong::get)
                .description("Total memory in bytes")
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
    public void incrementUserUpdate() {
        userUpdateCounter.increment();
    }

    @Override
    public void incrementUserDelete() {
        userDeleteCounter.increment();
    }

    @Override
    public void recordUserOperationError(String operation) {
        Counter.builder("user.operation.error.count")
                .description("Number of user operation errors")
                .tag("operation", operation)
                .register(meterRegistry)
                .increment();
    }

    @Override
    public TimerSample startUserCreationTimer() {
        return new MicrometerTimerSample(Timer.start(meterRegistry), userCreationTimer);
    }

    @Override
    public void stopUserCreationTimer(TimerSample sample) {
        if (sample instanceof MicrometerTimerSample micrometerSample) {
            micrometerSample.stop();
        }
    }

    @Override
    public TimerSample startUserQueryTimer() {
        return new MicrometerTimerSample(Timer.start(meterRegistry), userQueryTimer);
    }

    @Override
    public void stopUserQueryTimer(TimerSample sample) {
        if (sample instanceof MicrometerTimerSample micrometerSample) {
            micrometerSample.stop();
        }
    }

    @Override
    public TimerSample startUserUpdateTimer() {
        return new MicrometerTimerSample(Timer.start(meterRegistry), userUpdateTimer);
    }

    @Override
    public void stopUserUpdateTimer(TimerSample sample) {
        if (sample instanceof MicrometerTimerSample micrometerSample) {
            micrometerSample.stop();
        }
    }

    @Override
    public TimerSample startUserDeleteTimer() {
        return new MicrometerTimerSample(Timer.start(meterRegistry), userDeleteTimer);
    }

    @Override
    public void stopUserDeleteTimer(TimerSample sample) {
        if (sample instanceof MicrometerTimerSample micrometerSample) {
            micrometerSample.stop();
        }
    }

    @Override
    public TimerSample startDatabaseOperationTimer() {
        return new MicrometerTimerSample(Timer.start(meterRegistry), databaseOperationTimer);
    }

    @Override
    public void stopDatabaseOperationTimer(TimerSample sample) {
        if (sample instanceof MicrometerTimerSample micrometerSample) {
            micrometerSample.stop();
        }
    }

    @Override
    public void setActiveUsers(long count) {
        activeUsersCount.set(count);
    }

    @Override
    public void setDatabaseConnectionPoolSize(int size) {
        databaseConnectionPoolSize.set(size);
    }

    @Override
    public void recordResponseTime(String endpoint, long milliseconds) {
        Timer.builder("http.response.time")
                .description("HTTP response time")
                .tag("endpoint", endpoint)
                .register(meterRegistry)
                .record(milliseconds, java.util.concurrent.TimeUnit.MILLISECONDS);
    }

    @Override
    public void recordMemoryUsage(long usedMemory, long totalMemory) {
        this.usedMemory.set(usedMemory);
        this.totalMemory.set(totalMemory);
    }

    @Override
    public void recordCacheHit(String cacheName) {
        Counter.builder("cache.hit.count")
                .description("Cache hit count")
                .tag("cache", cacheName)
                .register(meterRegistry)
                .increment();
    }

    @Override
    public void recordCacheMiss(String cacheName) {
        Counter.builder("cache.miss.count")
                .description("Cache miss count")
                .tag("cache", cacheName)
                .register(meterRegistry)
                .increment();
    }

    @Override
    public void recordCacheEviction(String cacheName) {
        Counter.builder("cache.eviction.count")
                .description("Cache eviction count")
                .tag("cache", cacheName)
                .register(meterRegistry)
                .increment();
    }

    /**
     * Micrometer Timer Sample 包裝類別
     */
    private static class MicrometerTimerSample implements TimerSample {
        private final Timer.Sample sample;
        private final Timer timer;

        public MicrometerTimerSample(Timer.Sample sample, Timer timer) {
            this.sample = sample;
            this.timer = timer;
        }

        public void stop() {
            sample.stop(timer);
        }
    }
}
