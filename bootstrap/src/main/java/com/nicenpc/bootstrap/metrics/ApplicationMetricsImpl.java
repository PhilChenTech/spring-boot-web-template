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
    private final Timer userCreationTimer;
    private final AtomicLong activeUsers = new AtomicLong(0);
    
    public ApplicationMetricsImpl(MeterRegistry meterRegistry) {
        this.userCreationCounter = Counter.builder("user.creation.count")
                .description("使用者建立總次數")
                .register(meterRegistry);
        
        this.userQueryCounter = Counter.builder("user.query.count")
                .description("使用者查詢總次數")
                .register(meterRegistry);
        
        this.userCreationTimer = Timer.builder("user.creation.time")
                .description("使用者建立耗時")
                .register(meterRegistry);
        
        Gauge.builder("user.active.count", this, ApplicationMetricsImpl::getActiveUserCount)
                .description("目前活躍使用者數")
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
    
    private double getActiveUserCount() {
        return activeUsers.doubleValue();
    }
    
    private static class TimerSampleImpl implements TimerSample {
        private final Timer.Sample sample;
        
        public TimerSampleImpl(Timer.Sample sample) {
            this.sample = sample;
        }
    }
}