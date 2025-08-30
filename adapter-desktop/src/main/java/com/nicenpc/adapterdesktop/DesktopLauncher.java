package com.nicenpc.adapterdesktop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 桌面應用程式獨立啟動器
 * 這個類可以直接啟動桌面應用程式，不需要通過 bootstrap 模組
 */
@SpringBootApplication(scanBasePackages = {
    "com.nicenpc.adapterdesktop",
    "com.nicenpc.application",
    "com.nicenpc.adapteroutbound",
    "com.nicenpc.infrastructure"
})
public class DesktopLauncher {
    
    public static void main(String[] args) {
        // 設定為非 Web 應用程式
        System.setProperty("java.awt.headless", "false");
        System.setProperty("app.type", "desktop");
        
        SpringApplication app = new SpringApplication(DesktopLauncher.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.setHeadless(false);
        
        // 設定 desktop profile
        System.setProperty("spring.profiles.active", "desktop");
        
        System.out.println("Starting Desktop Application...");
        app.run(args);
    }
}
