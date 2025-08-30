package com.nicenpc.bootstrap.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.Arrays;

/**
 * 快取配置
 * 提供分層的快取策略和效能監控
 */
@Configuration
@EnableCaching
public class CacheConfig {
    
    // 快取常數
    public static final String USERS_CACHE = "users";
    public static final String USER_STATS_CACHE = "user-stats";
    public static final String SYSTEM_CONFIG_CACHE = "system-config";
    
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        
        // 設定快取名稱
        cacheManager.setCacheNames(Arrays.asList(
            USERS_CACHE,
            USER_STATS_CACHE,
            SYSTEM_CONFIG_CACHE
        ));
        
        // 為不同快取設定不同的策略
        cacheManager.setCaffeine(defaultCaffeineCacheBuilder());
        
        // 啟用動態快取創建
        cacheManager.setAllowNullValues(false);
        
        return cacheManager;
    }
    
    /**
     * 預設快取配置 - 適用於一般數據
     */
    private Caffeine<Object, Object> defaultCaffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(1000)
                .expireAfterAccess(Duration.ofMinutes(10))
                .expireAfterWrite(Duration.ofMinutes(5))
                .recordStats()
                .removalListener((key, value, cause) -> {
                    // 記錄快取移除事件用於監控
                    System.out.printf("快取移除: key=%s, cause=%s%n", key, cause);
                });
    }
    
    /**
     * 使用者數據專用快取配置
     */
    @Bean("userCacheBuilder")
    public Caffeine<Object, Object> userCaffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .initialCapacity(200)
                .maximumSize(2000)
                .expireAfterAccess(Duration.ofMinutes(15))
                .expireAfterWrite(Duration.ofMinutes(10))
                .recordStats()
                .removalListener((key, value, cause) -> {
                    System.out.printf("使用者快取移除: key=%s, cause=%s%n", key, cause);
                });
    }
    
    /**
     * 系統配置快取配置 - 長期快取
     */
    @Bean("configCacheBuilder")
    public Caffeine<Object, Object> configCaffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .initialCapacity(50)
                .maximumSize(500)
                .expireAfterAccess(Duration.ofHours(2))
                .expireAfterWrite(Duration.ofHours(1))
                .recordStats();
    }
    
    /**
     * 快取配置屬性
     */
    @ConfigurationProperties(prefix = "app.cache")
    public static class CacheProperties {
        private boolean enabled = true;
        private Duration defaultTtl = Duration.ofMinutes(5);
        private int maxSize = 1000;
        
        public boolean isEnabled() {
            return enabled;
        }
        
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
        
        public Duration getDefaultTtl() {
            return defaultTtl;
        }
        
        public void setDefaultTtl(Duration defaultTtl) {
            this.defaultTtl = defaultTtl;
        }
        
        public int getMaxSize() {
            return maxSize;
        }
        
        public void setMaxSize(int maxSize) {
            this.maxSize = maxSize;
        }
    }
}