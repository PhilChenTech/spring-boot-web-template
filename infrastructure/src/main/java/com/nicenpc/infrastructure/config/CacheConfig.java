package com.nicenpc.infrastructure.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.time.Duration;
import java.util.Arrays;

/**
 * 快取配置
 * 提供分層的快取策略和效能監控
 */
@Configuration
@EnableCaching
@EnableConfigurationProperties(CacheConfig.CacheProperties.class)
public class CacheConfig {
    
    // 快取常數
    public static final String USERS_CACHE = "users";
    public static final String USER_STATS_CACHE = "user-stats";
    public static final String SYSTEM_CONFIG_CACHE = "system-config";
    
    private final CacheProperties cacheProperties;
    
    public CacheConfig(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }
    
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        
        // 設定快取名稱
        cacheManager.setCacheNames(Arrays.asList(
            USERS_CACHE,
            USER_STATS_CACHE,
            SYSTEM_CONFIG_CACHE
        ));
        
        // 使用統一的快取配置
        cacheManager.setCaffeine(caffeineCacheBuilder());
        
        // 啟用動態快取創建
        cacheManager.setAllowNullValues(false);
        
        return cacheManager;
    }
    
    /**
     * 快取配置 - 整合屬性配置
     */
    private Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(cacheProperties.getMaxSize())
                .expireAfterAccess(cacheProperties.getDefaultTtl())
                .expireAfterWrite(cacheProperties.getDefaultTtl().dividedBy(2))
                .recordStats()
                .removalListener((key, value, cause) -> {
                    // 記錄快取移除事件用於監控
                    System.out.printf("快取移除: key=%s, cause=%s%n", key, cause);
                });
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
