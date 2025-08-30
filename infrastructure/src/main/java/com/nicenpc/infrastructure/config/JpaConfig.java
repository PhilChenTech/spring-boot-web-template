package com.nicenpc.infrastructure.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * JPA 配置類
 * 基礎設施層負責 JPA 相關配置
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.nicenpc.adapteroutbound.repository")
@EntityScan(basePackages = "com.nicenpc.adapteroutbound.entity")
public class JpaConfig {
    // JPA 相關配置
}
