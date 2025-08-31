package com.nicenpc.infrastructure.config;

import jakarta.persistence.Entity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * JPA 配置類
 * 只有當 JPA 相關類在 classpath 中時才會啟用
 */
@Configuration
@ConditionalOnClass(Entity.class)
@EnableJpaRepositories(basePackages = "com.nicenpc.adapter.outbound.repository")
@EntityScan(basePackages = "com.nicenpc.adapter.outbound.entity")
public class JpaConfig {
}
