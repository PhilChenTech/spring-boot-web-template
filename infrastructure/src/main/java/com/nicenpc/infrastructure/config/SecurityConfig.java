package com.nicenpc.infrastructure.config;

import com.nicenpc.infrastructure.config.properties.AppProperties;
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
                
                // 禁用基本認證
                .httpBasic(AbstractHttpConfigurer::disable)
                
                // 禁用表單登入
                .formLogin(AbstractHttpConfigurer::disable)
                
                // 禁用登出
                .logout(AbstractHttpConfigurer::disable)
                
                // CORS 設定
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                
                // 停用預設的 frame options（允許 H2 控制台）
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                
                // 無狀態會話（適用於 API）
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                
                // 授權設定 - 允許所有請求通過
                .authorizeHttpRequests(auth -> auth
                        // 允許所有請求無需認證
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
