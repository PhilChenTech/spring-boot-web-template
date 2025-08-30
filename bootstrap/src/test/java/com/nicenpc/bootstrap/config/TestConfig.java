package com.nicenpc.bootstrap.config;

import com.nicenpc.application.UserService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.mock;

/**
 * 測試配置
 * 提供 Mock bean 以避免依賴真實的服務實作
 */
@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    public UserService userService() {
        return mock(UserService.class);
    }
}
