package com.nicenpc.infrastructure.config;

import com.nicenpc.infrastructure.config.properties.AppProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 配置屬性啟用類
 */
@Configuration
@EnableConfigurationProperties({
    AppProperties.class
})
public class PropertiesConfig {
}
