package com.nicenpc.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * API版本控制配置
 * 配置API版本策略和相關設定
 */
@Configuration
public class ApiVersioningConfig implements WebMvcConfigurer {
    
    public static final String API_VERSION_1 = "1.0";
    public static final String API_VERSION_2 = "2.0";
    
    /**
     * 當前支援的API版本
     */
    public static class SupportedVersions {
        public static final String V1 = "v1";
        public static final String V2 = "v2";
        
        public static boolean isSupported(String version) {
            return V1.equals(version) || V2.equals(version);
        }
        
        public static String getLatest() {
            return V1; // 當前最新版本
        }
    }
    
    /**
     * API版本相關常數
     */
    public static class ApiConstants {
        public static final String VERSION_HEADER = "X-API-Version";
        public static final String VERSION_PARAM = "version";
        public static final String ACCEPT_HEADER_V1 = "application/vnd.api.v1+json";
        public static final String ACCEPT_HEADER_V2 = "application/vnd.api.v2+json";
    }
}
