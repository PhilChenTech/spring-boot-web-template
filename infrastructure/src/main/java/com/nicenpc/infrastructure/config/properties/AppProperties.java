package com.nicenpc.infrastructure.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

/**
 * 應用程式配置屬性
 * 提供類型安全的配置管理
 */
@ConfigurationProperties(prefix = "app")
@Data
@Validated
public class AppProperties {

    @NotBlank(message = "應用程式名稱不能為空")
    private String name;

    @NotBlank(message = "應用程式版本不能為空")
    @Pattern(regexp = "^\\d+\\.\\d+\\.\\d+$", message = "版本號格式應為 x.y.z")
    private String version;

    @NotBlank(message = "應用程式描述不能為空")
    private String description;

    @Valid
    @NotNull(message = "資料庫配置不能為空")
    private Database database;

    @Valid
    @NotNull(message = "安全配置不能為空")  
    private Security security;

    @Valid
    @NotNull(message = "CORS 配置不能為空")
    private Cors cors;

    @Valid
    @NotNull(message = "快取配置不能為空")
    private Cache cache;

    /**
     * 資料庫配置
     */
    @Data
    @Validated
    public static class Database {
        @NotBlank(message = "資料庫主機不能為空")
        private String host;

        @Min(value = 1, message = "端口號不能小於 1")
        @Max(value = 65535, message = "端口號不能大於 65535")
        private int port;

        @NotBlank(message = "資料庫名稱不能為空")
        private String name;

        @NotBlank(message = "資料庫用戶名不能為空")
        private String username;

        @NotBlank(message = "資料庫密碼不能為空")
        private String password;
    }

    /**
     * 安全配置
     */
    @Data
    @Validated
    public static class Security {
        @NotBlank(message = "管理員用戶名不能為空")
        private String adminUsername;

        @NotBlank(message = "管理員密碼不能為空")
        private String adminPassword;

        private String adminRoles = "ADMIN";
    }

    /**
     * CORS 配置
     */
    @Data
    @Validated
    public static class Cors {
        @NotNull(message = "允許的來源不能為空")
        private String allowedOrigins;

        private String allowedMethods = "GET,POST,PUT,DELETE,OPTIONS";
        private String allowedHeaders = "Authorization,Content-Type,Accept,X-Requested-With,Cache-Control";
        private boolean allowCredentials = true;
        private long maxAge = 3600;
    }

    /**
     * 快取配置
     */
    @Data
    @Validated
    public static class Cache {
        private String type = "caffeine";
        
        @NotBlank(message = "快取規範不能為空")
        private String spec = "maximumSize=1000,expireAfterAccess=600s,expireAfterWrite=300s";
    }
}
