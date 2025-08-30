package com.nicenpc.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Web 應用程式啟動類
 * 
 * 這個啟動類專門用於 Web 應用程式。
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.nicenpc")
public class Application {
    
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        
        // 設定為 Web 應用程式
        app.setWebApplicationType(WebApplicationType.SERVLET);
        System.out.println("Starting in WEB mode...");
        
        app.run(args);
    }
}
