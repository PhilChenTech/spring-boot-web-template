package com.nicenpc.bootstrap.config;

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
public class SecurityConfig {

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
                                "/v3/api-docs/**"
                        ).permitAll()
                        
                        // 其他端點需要認證
                        .requestMatchers("/api/**").authenticated()
                        
                        // 允許其他所有請求（可根據需要調整）
                        .anyRequest().permitAll()
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // 允許的源
        configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:*", "http://127.0.0.1:*"));
        
        // 允許的 HTTP 方法
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // 允許的標頭
        configuration.setAllowedHeaders(List.of("*"));
        
        // 是否允許憑證
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        
        return source;
    }
}