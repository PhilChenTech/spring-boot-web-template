package com.nicenpc.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 通用應用程式啟動類
 * 
 * 這個啟動類設計為可以支援多種應用程式類型：
 * - Web 應用程式（當 adapter-inbound 包含 Controller 時）
 * - 桌面應用程式（當包含 GUI 組件時）
 * - 批次處理應用程式（當包含 Scheduler 或 CommandLineRunner 時）
 * - 消息驅動應用程式（當包含 Message Listener 時）
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.nicenpc")
public class Application {
    
    public static void main(String[] args) {
        // 從啟動參數中提取 app.type，預設為 web
        String appType = "web";
        for (String arg : args) {
            if (arg.startsWith("--app.type=")) {
                appType = arg.substring("--app.type=".length());
                break;
            }
        }
        
        // 也可以從系統屬性中讀取
        appType = System.getProperty("app.type", appType);
        
        SpringApplication app = new SpringApplication(Application.class);
        
        // 根據應用程式類型設定不同的配置
        switch (appType.toLowerCase()) {
            case "desktop":
                // 桌面應用程式配置
                app.setHeadless(false);
                app.setWebApplicationType(WebApplicationType.NONE);
                System.out.println("Starting in DESKTOP mode...");
                break;
            case "batch":
                // 批次處理應用程式配置
                app.setWebApplicationType(WebApplicationType.NONE);
                System.out.println("Starting in BATCH mode...");
                break;
            case "web":
            default:
                // Web 應用程式配置（預設）
                app.setWebApplicationType(WebApplicationType.SERVLET);
                System.out.println("Starting in WEB mode...");
                break;
        }
        
        app.run(args);
    }
}
