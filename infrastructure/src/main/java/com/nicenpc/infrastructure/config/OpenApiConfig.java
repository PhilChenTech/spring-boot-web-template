package com.nicenpc.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("web")
public class OpenApiConfig {

    @Value("${server.port:8080}")
    private String serverPort;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Nice NPC Spring Boot DDD Template API")
                        .description("基於 Clean Architecture 和 DDD 原則的 Spring Boot 模板專案 API 文檔")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Nice NPC Team")
                                .email("contact@nicenpc.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:" + serverPort)
                                .description("開發環境"),
                        new Server()
                                .url("https://api.nicenpc.com")
                                .description("生產環境")
                ));
    }
}
