# Nice NPC Spring Boot DDD Template

[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://choosealicense.com/licenses/mit/)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.1-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Gradle](https://img.shields.io/badge/Gradle-8.x-blue.svg)](https://gradle.org/)
[![Company](https://img.shields.io/badge/Company-Nice%20NPC-blue.svg)](https://nice-npc.com)

一個基於 Domain-Driven Design (DDD) 架構的 Spring Boot 多模組專案模板，由 **Nice NPC** 開發並維護，適合用作微服務或複雜業務系統的起始專案。

## 專案配置
- **Java 版本**: 21 (LTS)
- **Spring Boot**: 3.2.1
- **Gradle**: 8.x
- **架構**: DDD (Domain-Driven Design) 多模組
- **許可證**: MIT License
- **公司**: Nice NPC (nice-npc.com)

## 專案修正總結

### 1. 解決的問題
- ✅ 修正了 `build.gradle` 檔案格式錯誤（所有依賴都在同一行）
- ✅ 修正了模組依賴關係（adapter-inbound 需要依賴 domain）
- ✅ 配置了正確的多模組 Spring Boot 結構
- ✅ 解決了 Gradle daemon 卡住的問題

### 2. Gradle 性能優化
建立了 `gradle.properties` 檔案以優化性能：
```properties
org.gradle.daemon=true
org.gradle.parallel=true
org.gradle.configureondemand=true
org.gradle.caching=true
org.gradle.jvmargs=-Xmx2048m -XX:MaxMetaspaceSize=512m
```

### 3. 推薦的開發工作流程

#### 快速建置（避免卡住）：
```bash
# 停止所有 daemon
.\gradlew.bat --stop

# 清理並建置（不使用 daemon）
.\gradlew.bat --no-daemon clean build -x test

# 直接執行 JAR 檔案（最快）
java -jar adapter-inbound\build\libs\adapter-inbound-1.0.0.jar
```

#### 或使用批次檔：
```bash
# 執行 start-app.bat
start-app.bat
```

### 4. API 端點
- 健康檢查：http://localhost:8080/api/health
- 歡迎頁面：http://localhost:8080/api/
- 用戶 API：http://localhost:8080/api/users

### 5. 專案結構
```
springboot-web-template/
├── adapter-inbound/     # Web 層（Controller）
├── adapter-outbound/    # 資料存取層
├── application/         # 應用服務層
├── domain/             # 領域模型層
├── infrastructure/     # 基礎設施層
├── common/            # 共用組件
├── gradle.properties  # Gradle 優化配置
└── start-app.bat      # 快速啟動腳本
```

### 6. Java 21 升級說明
✅ **已升級至 Java 21**
- 編譯目標: `sourceCompatibility = '21'`
- 運行環境: `targetCompatibility = '21'`
- 兼容性: Spring Boot 3.2.1 完全支援 Java 21
- 性能提升: Java 21 LTS 版本，提供更好的性能和安全性

### 7. 避免 Gradle 卡住的技巧

#### `--no-daemon` 參數詳解
```bash
# 一般執行（使用 daemon）
.\gradlew.bat build  # 快，但可能卡住

# 使用 --no-daemon（不使用 daemon）
.\gradlew.bat --no-daemon build  # 慢一點，但穩定
```

**什麼是 Gradle Daemon？**
- 背景常駐的 JVM 進程
- 目的：加速後續建置
- 問題：可能記憶體洩漏、卡死、版本衝突

**何時使用 `--no-daemon`？**
- ✅ Gradle 經常卡住時
- ✅ CI/CD 環境
- ✅ 記憶體有限的電腦
- ✅ 偶爾建置的專案

**其他避免卡住技巧：**
1. 定期執行 `.\gradlew.bat --stop` 清理 daemon
2. 使用 `-x test` 跳過測試以加快建置
3. 直接執行 JAR 檔案：`java -jar *.jar`
4. 配置適當的 JVM 記憶體設定
5. 監控 daemon 狀態：`.\gradlew.bat --status`

## 快速開始

### 1. 複製專案
```bash
git clone https://github.com/PhilChenTech/spring-boot-web-template.git
cd spring-boot-web-template
```

### 2. 建置專案
```bash
# 建議使用 --no-daemon 避免卡住
.\gradlew.bat --no-daemon clean build -x test
```

### 3. 啟動應用程式
```bash
# 方法一：使用批次檔
start-app.bat

# 方法二：直接執行 JAR
java -jar adapter-inbound\build\libs\adapter-inbound-1.0.0.jar
```

### 4. 測試 API
- 健康檢查：http://localhost:8080/api/health
- 歡迎頁面：http://localhost:8080/api/
- 用戶 API：http://localhost:8080/api/users

現在 API 回應將包含 Nice NPC 的品牌信息！

## 貢獻指南

我們歡迎任何形式的貢獻！請參考以下步驟：

1. Fork 此專案
2. 創建您的功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交您的變更 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 開啟一個 Pull Request

## 許可證

此專案採用 MIT 許可證 - 詳情請參閱 [LICENSE](LICENSE) 檔案。

## 聯絡方式

如果您有任何問題或建議，歡迎：
- 開啟 [Issue](https://github.com/PhilChenTech/spring-boot-web-template/issues)
- 聯絡專案維護者：[@PhilChenTech](https://github.com/PhilChenTech)
- 造訪公司官網：[nice-npc.com](https://nice-npc.com)

## 致謝

感謝所有為此專案做出貢獻的開發者！

---

**Powered by [Nice NPC](https://nice-npc.com) - Making Technology Nice & Professional & Creative**
