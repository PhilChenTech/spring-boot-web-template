package com.nicenpc.bootstrap.config;

import com.nicenpc.bootstrap.config.properties.AppProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@Profile("web")
@RequiredArgsConstructor
public class SecurityConfig {

    private final AppProperties appProperties;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 禁用 CSRF（對於 API 應用程式）
                .csrf(AbstractHttpConfigurer::disable)
                
                // CORS 設定
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                
                // 停用預設的 frame options（允許 H2 控制台）
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                
                // 無狀態會話（適用於 API）
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                
                // 授權設定
                .authorizeHttpRequests(auth -> auth
                        // 允許公開端點
                        .requestMatchers(
                                "/api/public/**",
                                "/actuator/health",
                                "/actuator/info",
                                "/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/api/v1/users/**",  // 允許用戶 API 端點無需認證
                                "/api/users/**"      // 允許用戶 API 端點無需認證（兼容測試路徑）
                        ).permitAll()
                        
                        // 管理員端點需要認證
                        .requestMatchers("/api/admin/**").authenticated()
                        
                        // 允許其他所有請求（可根據需要調整）
                        .anyRequest().permitAll()
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        AppProperties.Cors corsConfig = appProperties.getCors();
        
        // 允許的源 - 從配置屬性讀取，提高安全性
        List<String> origins = Arrays.asList(corsConfig.getAllowedOrigins().split(","));
        configuration.setAllowedOrigins(origins);
        
        // 允許的 HTTP 方法
        List<String> methods = Arrays.asList(corsConfig.getAllowedMethods().split(","));
        configuration.setAllowedMethods(methods);
        
        // 允許的標頭 - 從配置讀取
        List<String> headers = Arrays.asList(corsConfig.getAllowedHeaders().split(","));
        configuration.setAllowedHeaders(headers);
        
        // 暴露的標頭
        configuration.setExposedHeaders(Arrays.asList("X-Total-Count"));
        
        // 是否允許憑證
        configuration.setAllowCredentials(corsConfig.isAllowCredentials());
        
        // 預檢請求快取時間（秒）
        configuration.setMaxAge(corsConfig.getMaxAge());
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        
        return source;
    }
}