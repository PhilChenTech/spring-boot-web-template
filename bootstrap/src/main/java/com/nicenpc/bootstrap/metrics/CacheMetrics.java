package com.nicenpc.bootstrap.metrics;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

/**
 * 快取指標監控
 * 監控各種快取的效能指標
 */
@Component
public class CacheMetrics {
    
    private final MeterRegistry meterRegistry;
    private final CacheManager cacheManager;
    
    public CacheMetrics(MeterRegistry meterRegistry, CacheManager cacheManager) {
        this.meterRegistry = meterRegistry;
        this.cacheManager = cacheManager;
    }
    
    @PostConstruct
    public void registerCacheMetrics() {
        // 為每個快取註冊指標
        cacheManager.getCacheNames().forEach(this::registerCacheMetric);
    }
    
    private void registerCacheMetric(String cacheName) {
        org.springframework.cache.Cache springCache = cacheManager.getCache(cacheName);
        if (springCache instanceof CaffeineCache caffeineCache) {
            Cache<Object, Object> nativeCache = caffeineCache.getNativeCache();
            
            // 註冊快取大小指標
            Gauge.builder("cache.size", nativeCache, cache -> (double) cache.estimatedSize())
                    .tag("cache", cacheName)
                    .description("快取中的條目數量")
                    .register(meterRegistry);
            
            // 註冊快取統計指標
            registerCacheStats(cacheName, nativeCache);
        }
    }
    
    private void registerCacheStats(String cacheName, Cache<Object, Object> cache) {
        // 快取命中率
        Gauge.builder("cache.hit.rate", cache, c -> c.stats().hitRate())
                .tag("cache", cacheName)
                .description("快取命中率")
                .register(meterRegistry);
        
        // 快取未命中數
        Gauge.builder("cache.miss.count", cache, c -> (double) c.stats().missCount())
                .tag("cache", cacheName)
                .description("快取未命中次數")
                .register(meterRegistry);
        
        // 快取載入時間
        Gauge.builder("cache.load.time", cache, c -> c.stats().averageLoadPenalty())
                .tag("cache", cacheName)
                .description("平均快取載入時間 (奈秒)")
                .register(meterRegistry);
        
        // 快取逐出數量
        Gauge.builder("cache.eviction.count", cache, c -> (double) c.stats().evictionCount())
                .tag("cache", cacheName)
                .description("快取逐出次數")
                .register(meterRegistry);
    }
    
    /**
     * 手動取得快取統計資訊
     */
    public CacheStats getCacheStats(String cacheName) {
        org.springframework.cache.Cache springCache = cacheManager.getCache(cacheName);
        if (springCache instanceof CaffeineCache caffeineCache) {
            return caffeineCache.getNativeCache().stats();
        }
        return null;
    }
    
    /**
     * 清除指定快取
     */
    public void clearCache(String cacheName) {
        org.springframework.cache.Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        }
    }
    
    /**
     * 清除所有快取
     */
    public void clearAllCaches() {
        cacheManager.getCacheNames().forEach(this::clearCache);
    }
}