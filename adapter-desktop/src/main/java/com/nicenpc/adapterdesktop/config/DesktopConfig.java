package com.nicenpc.adapterdesktop.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct;

/**
 * 桌面應用程式配置
 * 只有在 desktop 模式下才會啟用
 */
@Configuration
@ConditionalOnProperty(name = "app.type", havingValue = "desktop")
public class DesktopConfig {
    
    @PostConstruct
    public void init() {
        // 設定系統屬性以支援 JavaFX/Swing
        System.setProperty("java.awt.headless", "false");
        System.setProperty("prism.order", "sw");
        System.setProperty("javafx.platform", "desktop");
    }
}
