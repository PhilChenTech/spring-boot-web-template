# Nice NPC Spring Boot DDD Template

[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://choosealicense.com/licenses/mit/)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.1-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Gradle](https://img.shields.io/badge/Gradle-8.x-blue.svg)](https://gradle.org/)
[![Repository](https://img.shields.io/badge/GitHub-PhilChenTech%2Fspring--boot--web--template-blue.svg)](https://github.com/PhilChenTech/spring-boot-web-template)

一個基於 **Clean Architecture** 和 **Domain-Driven Design (DDD)** 原則的 Spring Boot Web 應用程式模板專案。

## 🚀 特色功能

- ✅ **Clean Architecture** - 清晰的層次結構和依賴方向
- ✅ **Domain-Driven Design** - 領域驅動設計最佳實踐
- ✅ **CQRS 模式** - 指令與查詢責任分離
- ✅ **Web 應用程式** - 完整的 Web API 支援
- ✅ **PostgreSQL 整合** - 生產級資料庫支援
- ✅ **Spring Security** - 基本認證和 CORS 配置
- ✅ **API 文檔** - OpenAPI 3.0 (Swagger)
- ✅ **監控指標** - Micrometer + Prometheus
- ✅ **健康檢查** - Spring Boot Actuator
- ✅ **快取支援** - Caffeine 高性能快取
- ✅ **測試覆蓋率** - JaCoCo 報告

## 🏗️ 專案架構

```
springboot-web-template/
├── bootstrap/              # 引導模組 - 應用程式啟動和配置
├── domain/                 # 領域層 - 核心業務邏輯
├── application/            # 應用層 - 業務用例協調
│   ├── command/           # CQRS 指令
│   ├── query/             # CQRS 查詢
│   ├── handler/           # 指令/查詢處理器
│   └── bus/               # 指令/查詢匯流排
├── infrastructure/        # 基礎設施層 - 外部依賴實現
├── adapter-inbound/       # 入站適配器 - REST 控制器和 API
├── adapter-outbound/      # 出站適配器 - 資料庫存取層
├── adapter-web/           # Web 適配器 - Web 特定配置
├── common/                # 公共模組 - 共用工具和基礎類別
└── database/              # 資料庫遷移腳本和初始化文件
```

## 🛠️ 技術棧

- **Java 21** - 最新 LTS 版本
- **Spring Boot 3.2.1** - 主要框架
- **Spring Data JPA** - 資料持久化
- **PostgreSQL** - 主要資料庫
- **Spring Security** - 安全框架
- **MapStruct** - 物件映射
- **Caffeine** - 快取引擎
- **Micrometer** - 指標監控
- **SpringDoc OpenAPI** - API 文檔
- **JUnit 5** - 單元測試
- **JaCoCo** - 測試覆蓋率

## 🚀 快速開始

### 前置需求

- Java 21+
- PostgreSQL 15+

### 環境設定

1. **安裝 PostgreSQL**
   ```bash
   # macOS
   brew install postgresql@15
   
   # Ubuntu
   sudo apt install postgresql-15
   
   # Windows - 下載官方安裝程式
   ```

2. **建立資料庫**
   ```sql
   CREATE DATABASE springboot_template_db;
   CREATE DATABASE springboot_template_db_dev;
   CREATE DATABASE springboot_template_db_test;
   ```

3. **設定環境變數**
   
   複製範例環境檔案：
   ```bash
   cp .env.example .env
   ```
   
   或者手動設定環境變數：
   ```bash
   export DB_HOST=localhost
   export DB_PORT=5432
   export DB_USERNAME=postgres
   export DB_PASSWORD=test
   export DB_NAME=springboot_template_dev
   ```
   
   在 Windows 中可以使用提供的 PowerShell 腳本：
   ```powershell
   .\Set-DbEnv-Simple.ps1
   ```

### 執行應用程式

#### Web 模式 (預設)
```bash
./gradlew bootRun
```
或指定 profile：
```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

應用程式將在 http://localhost:8080 啟動

### 建構和測試

```bash
# 執行所有測試
./gradlew test

# 生成測試覆蓋率報告
./gradlew jacocoTestReport

# 建構應用程式
./gradlew build
```

## 📋 API 文檔

應用程式啟動後，可以透過以下 URL 存取 API 文檔：

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs

### 主要 API 端點

#### 使用者管理 API
- `GET /api/v1/users` - 取得所有使用者
- `GET /api/v1/users/{id}` - 根據 ID 取得使用者
- `POST /api/v1/users` - 建立新使用者
- `PUT /api/v1/users/{id}` - 更新使用者資訊
- `DELETE /api/v1/users/{id}` - 刪除使用者

#### 示範 API (UserDemo)
- `GET /api/v1/users/demo/stats` - 取得使用者統計資訊
- `GET /api/v1/users/demo/search-by-domain` - 根據郵件網域搜尋使用者
- `POST /api/v1/users/demo/create-test-users` - 建立測試使用者
- `DELETE /api/v1/users/demo/clear-all` - 清除所有使用者

#### 健康檢查 API
- `GET /health` - 應用程式健康狀態
- `GET /` - 根路徑歡迎訊息

## 📊 監控和健康檢查

### Actuator 端點

- **健康檢查**: http://localhost:8080/actuator/health
- **應用資訊**: http://localhost:8080/actuator/info
- **指標資料**: http://localhost:8080/actuator/metrics
- **Prometheus 指標**: http://localhost:8080/actuator/prometheus

### 自定義指標

- `user.creation.count` - 使用者建立總次數
- `user.query.count` - 使用者查詢總次數
- `user.creation.time` - 使用者建立耗時
- `user.active.count` - 目前活躍使用者數


## 🔧 配置說明

### 環境配置

專案支援多個環境配置：

- `application.yml` - 通用配置
- `application-dev.yml` - 開發環境
- `application-prod.yml` - 生產環境
- `application-web.yml` - Web 模式特定配置

### 重要配置項

```yaml
# 應用程式配置
app:
  name: nice-npc-ddd-template
  database:
    host: ${DB_HOST:localhost}
    port: ${DB_PORT:5432}
    name: ${DB_NAME:springboot_template}
    username: ${DB_USERNAME:postgres}
    password: ${DB_PASSWORD:test}
  cache:
    type: ${CACHE_TYPE:caffeine}
    spec: ${CACHE_SPEC:maximumSize=1000,expireAfterAccess=600s,expireAfterWrite=300s}

# Spring Boot 配置
spring:
  datasource:
    url: jdbc:postgresql://${app.database.host}:${app.database.port}/${app.database.name}
    username: ${app.database.username}
    password: ${app.database.password}

# 監控配置
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
```

## 🧪 測試策略

### 測試層級

1. **單元測試** - Domain 和 Application 層
2. **整合測試** - Repository 和 Service 層
3. **API 測試** - Controller 層
4. **端到端測試** - 完整流程測試

### 執行測試

```bash
# 執行所有測試
./gradlew test

# 執行特定模組測試
./gradlew :domain:test

# 生成覆蓋率報告
./gradlew jacocoTestReport

# 查看覆蓋率報告
open build/reports/jacoco/test/html/index.html
```


## 📚 開發指南

### 新增功能

1. **領域實體** - 在 `domain` 模組中定義
2. **應用服務** - 在 `application` 模組中實現 CQRS 模式
3. **資料庫存取** - 在 `infrastructure` 模組中實現 Repository
4. **API 端點** - 在 `adapter-inbound` 模組中實現 Controller

### 最佳實踐

- 遵循 Clean Architecture 依賴規則
- 使用 CQRS 分離讀寫操作
- 在 Domain 層實現業務驗證
- 使用 MapStruct 進行物件轉換
- 添加適當的快取策略
- 編寫充足的單元測試

## 🤝 貢獻指南

1. Fork 專案
2. 建立功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交變更 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 開啟 Pull Request

## 📄 授權

本專案採用 MIT 授權條款 - 查看 [LICENSE](LICENSE) 檔案了解詳情。

## 👥 團隊

- **Nice NPC Team** - 初始開發團隊

## 📞 支援

如有問題或建議，請：

1. 開啟 [GitHub Issue](https://github.com/PhilChenTech/spring-boot-web-template/issues)
2. 查看專案文檔
3. 聯繫開發團隊

---

**Happy Coding! 🚀**