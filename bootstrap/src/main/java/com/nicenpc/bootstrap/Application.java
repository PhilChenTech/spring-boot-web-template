package com.nicenpc.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Web應用程式入口
 *
 * <p>Spring Boot應用程式的主要入口點，負責啟動整個應用程式。
 * 遵循Clean Architecture的模組化設計，通過ComponentScan自動掃描所有層級的組件。</p>
 *
 * @author Nice NPC Team
 * @version 1.0
 * @since 1.0
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.nicenpc")
public class Application {
    
    /**
     * 應用程式主要入口點
     *
     * @param args 命令列參數
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        
        // 設定為Web應用程式
        app.setWebApplicationType(WebApplicationType.SERVLET);
        System.out.println("Starting Spring Boot application in WEB mode...");

        app.run(args);
    }
}
