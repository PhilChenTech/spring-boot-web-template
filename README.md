# Nice NPC Spring Boot DDD Template

[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://choosealicense.com/licenses/mit/)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.1-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Gradle](https://img.shields.io/badge/Gradle-8.x-blue.svg)](https://gradle.org/)
[![Repository](https://img.shields.io/badge/GitHub-PhilChenTech%2Fspring--boot--web--template-blue.svg)](https://github.com/PhilChenTech/spring-boot-web-template)

🚀 **現代化企業級 Spring Boot 專案模板**

這是一個採用 **Clean Architecture（乾淨架構）** 和 **Domain-Driven Design（領域驅動設計）** 原則設計的 Spring Boot Web 應用程式模板，專為建構可維護、可擴展的企業級應用程式而設計。

## 🎯 為什麼選擇這個模板？

- 📐 **標準化架構** - 遵循業界最佳實踐的分層架構設計
- 🔄 **CQRS 模式** - 清晰分離讀寫操作，提升系統效能
- 🛡️ **型別安全** - 大量使用 Java Record 確保資料不可變性
- 📊 **完整監控** - 內建健康檢查、指標監控和 API 文檔
- 🧪 **測試就緒** - 完整的測試架構和覆蓋率報告
- 🚀 **生產就緒** - 包含安全、快取、資料庫遷移等完整功能

## ✨ 核心特色

### 🏗️ 架構設計
- ✅ **Clean Architecture** - 分層清晰，依賴方向明確
- ✅ **Domain-Driven Design** - 領域模型為核心的設計方法
- ✅ **CQRS 模式** - 指令與查詢責任分離，提升系統效能
- ✅ **模組化設計** - 高內聚低耦合的模組結構

### 🌐 Web 功能
- ✅ **RESTful API** - 符合 REST 原則的 Web API 設計
- ✅ **OpenAPI 文檔** - 自動生成的 Swagger UI 介面
- ✅ **請求驗證** - Bean Validation 自動參數驗證
- ✅ **全域異常處理** - 統一的錯誤回應格式

### 🗄️ 資料持久化
- ✅ **PostgreSQL 整合** - 企業級關聯式資料庫支援
- ✅ **JPA/Hibernate** - ORM 框架簡化資料庫操作
- ✅ **Flyway 遷移** - 資料庫版本控制和自動遷移
- ✅ **連線池管理** - HikariCP 高效能連線池

### 🔒 安全與監控
- ✅ **Spring Security** - 企業級安全框架
- ✅ **CORS 支援** - 跨域請求配置
- ✅ **健康檢查** - Spring Boot Actuator 監控端點
- ✅ **指標監控** - Micrometer + Prometheus 整合

### ⚡ 效能優化
- ✅ **快取支援** - Caffeine 高效能記憶體快取
- ✅ **連線池優化** - 資料庫連線效能調校
- ✅ **JVM 調校** - Java 21 最佳化參數

### 🧪 測試與品質
- ✅ **完整測試** - 單元測試、整合測試、API 測試
- ✅ **測試覆蓋率** - JaCoCo 詳細覆蓋率報告
- ✅ **程式碼品質** - 嚴格的編碼規範和最佳實踐

## 🏗️ 專案架構

### 📁 目錄結構
```
springboot-web-template/
├── 📂 domain/                     # 🎯 領域層 - 核心業務邏輯
│   └── src/main/java/com/nicenpc/domain/
│       ├── User.java              # 使用者領域實體
│       ├── repository/            # 領域倉儲介面
│       └── exception/             # 領域特定異常
│
├── 📂 application/                # 🔄 應用層 - 業務用例協調
│   └── src/main/java/com/nicenpc/application/
│       ├── command/               # CQRS 指令（寫操作）
│       ├── query/                 # CQRS 查詢（讀操作）
│       ├── handler/               # 指令/查詢處理器
│       ├── bus/                   # 指令/查詢匯流排
│       ├── metrics/               # 應用層效能指標
│       └── UserService.java      # 應用服務（業務協調）
│
├── 📂 infrastructure/            # 🔧 基礎設施層 - 技術實現
│   └── src/main/java/com/nicenpc/infrastructure/
│       ├── Application.java       # 🚀 Spring Boot 應用程式啟動點
│       ├── config/                # 📋 應用程式配置
│       │   ├── SecurityConfig.java     # 安全配置
│       │   ├── JpaConfig.java          # JPA 配置
│       │   └── OpenApiConfig.java      # API 文檔配置
│       └── adapter/               # 🔌 適配器實現
│           ├── inbound/           # 入站適配器（接收外部請求）
│           │   ├── controller/    # REST 控制器
│           │   ├── dto/           # 資料傳輸物件
│           │   ├── exception/     # Web 層異常處理
│           │   └── mapper/        # DTO 映射器
│           └── outbound/          # 出站適配器（對接外部系統）
│               ├── entity/        # JPA 實體類別
│               ├── repository/    # JPA 倉儲實現
│               └── mapper/        # 實體映射器
│
├── 📂 common/                     # 🛠️ 共用模組 - 工具與基礎設施
│   └── src/main/java/com/nicenpc/common/
│       ├── mapper/                # 通用映射器工具
│       └── test/                  # 測試工具類別
│
└── 📂 database/                   # 🗄️ 資料庫相關
    ├── init-postgresql.sql        # PostgreSQL 初始化腳本
    └── migrations/                # Flyway 資料庫遷移腳本
        ├── V1__Create_users_table.sql
        └── V2__Restructure_users_table.sql
```

### 🎯 分層架構說明

#### 🟢 Domain Layer（領域層）
- **職責**：定義核心業務邏輯和規則
- **特色**：不依賴任何外部框架，保持純淨
- **包含**：實體、值物件、領域服務、倉儲介面

#### 🔵 Application Layer（應用層）  
- **職責**：協調業務用例的執行流程
- **特色**：實現 CQRS 模式，分離讀寫操作
- **包含**：指令、查詢、處理器、應用服務

#### 🟡 Infrastructure Layer（基礎設施層）
- **職責**：提供技術實現和外部系統整合
- **特色**：包含應用程式啟動和所有適配器
- **包含**：Web 控制器、資料庫實現、配置類別

#### 🟣 Common Layer（共用層）
- **職責**：提供跨層級的工具和基礎設施
- **特色**：被其他所有層級使用
- **包含**：工具類別、常量、測試輔助

## 🛠️ 技術棧

### 核心框架
- **☕ Java 21** - 最新 LTS 版本，效能與安全性最佳化
- **🍃 Spring Boot 3.2.1** - 企業級 Java 應用框架
- **🏗️ Gradle 8.x** - 現代化建構工具

### 資料存取層
- **🗄️ PostgreSQL** - 企業級關聯式資料庫  
- **📊 Spring Data JPA** - 簡化資料持久化操作
- **🔄 Flyway** - 資料庫版本控制與遷移

### Web 層技術
- **🌐 Spring Web MVC** - RESTful Web 服務框架
- **📋 Bean Validation** - 自動參數驗證
- **📚 SpringDoc OpenAPI** - 自動化 API 文檔生成

### 安全與監控
- **🔒 Spring Security** - 企業級安全框架
- **📊 Spring Boot Actuator** - 應用監控與健康檢查
- **📈 Micrometer** - 應用指標收集
- **🎯 Prometheus** - 指標數據格式支援

### 效能優化
- **⚡ Caffeine** - 高效能 JVM 快取引擎
- **🏊 HikariCP** - 高效能資料庫連線池

### 開發工具
- **🗺️ MapStruct** - 高效能 Bean 映射框架
- **🧪 JUnit 5** - 現代化單元測試框架
- **📊 JaCoCo** - 程式碼覆蓋率分析

## 🚀 快速開始

### 📋 系統需求

- **Java 21+** - [下載 OpenJDK](https://adoptium.net/)
- **PostgreSQL 15+** - [安裝指南](https://www.postgresql.org/download/)
- **Git** - 版本控制工具

### 🗄️ 資料庫準備

#### 1️⃣ 安裝 PostgreSQL
```bash
# macOS (使用 Homebrew)
brew install postgresql@15

# Ubuntu/Debian
sudo apt install postgresql-15

# Windows - 下載官方安裝程式
# https://www.postgresql.org/download/windows/
```

#### 2️⃣ 建立資料庫
```sql
-- 連接到 PostgreSQL
psql -U postgres

-- 建立專案資料庫
CREATE DATABASE nice_npc_db;
CREATE DATABASE nice_npc_db_dev;
CREATE DATABASE nice_npc_db_test;

-- 建立專用使用者（可選）
CREATE USER nice_npc_user WITH ENCRYPTED PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE nice_npc_db TO nice_npc_user;
```

### ⚙️ 環境變數設定


#### 方法一：使用 .env 檔案（推薦）
```bash
# 複製環境範本檔案
cp infrastructure/src/main/resources/application-local.yml.example .env

# 編輯環境變數
DB_HOST=localhost
DB_PORT=5432
# 開發環境：
DB_NAME=springboot_template_db_dev
# 生產環境：
DB_NAME=springboot_template_db_prod
# 預設（一般環境）：
DB_NAME=springboot_template
DB_USERNAME=postgres
DB_PASSWORD=your_password
```

#### 方法二：手動設定環境變數
```bash

# Linux/macOS
export DB_HOST=localhost
export DB_PORT=5432
# 開發環境
export DB_NAME=springboot_template_db_dev
# 生產環境
export DB_NAME=springboot_template_db_prod
# 預設
export DB_NAME=springboot_template
export DB_USERNAME=postgres
export DB_PASSWORD=your_password

# Windows PowerShell
$env:DB_HOST="localhost"
$env:DB_PORT="5432"
# 開發環境
$env:DB_NAME="springboot_template_db_dev"
# 生產環境
$env:DB_NAME="springboot_template_db_prod"
# 預設
$env:DB_NAME="springboot_template"
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD="your_password"
```

#### 方法三：Windows 批次檔案
```cmd
# 執行提供的環境設定批次檔案
set-db-env.bat
```

### 🚀 啟動應用程式

#### 開發模式啟動
```bash
# 使用 Gradle Wrapper（推薦）
./gradlew :infrastructure:bootRun

# Windows
.\gradlew.bat :infrastructure:bootRun

# 指定開發環境 profile
./gradlew :infrastructure:bootRun --args='--spring.profiles.active=dev'
```

#### 使用 VS Code Tasks
```bash
# 如果你使用 VS Code，可以直接執行預設的任務
# Ctrl+Shift+P > Tasks: Run Task > Run Infrastructure Application
```

✅ **成功啟動後，應用程式將在 http://localhost:8080 運行**

### 🏗️ 建構與測試

```bash
# 🧪 執行所有測試
./gradlew test

# 📊 生成測試覆蓋率報告
./gradlew jacocoTestReport

# 🔧 只編譯不執行測試
./gradlew compileJava

# 📦 完整建構（包含測試）
./gradlew build

# 🧹 清理建構檔案
./gradlew clean
```

### ✅ 驗證安裝

訪問以下端點確認應用程式正常運行：

- **🏠 首頁**: http://localhost:8080/api/
- **💚 健康檢查**: http://localhost:8080/api/health  
- **📚 API 文檔**: http://localhost:8080/swagger-ui.html
- **🔍 監控端點**: http://localhost:8080/actuator/health

## 📋 API 文檔

### 🌐 訪問 API 文檔

應用程式啟動後，可透過以下 URL 存取完整的 API 文檔：

- **📚 Swagger UI**: http://localhost:8080/swagger-ui.html
- **📄 OpenAPI JSON**: http://localhost:8080/v3/api-docs

### 🔗 主要 API 端點

#### 👤 使用者管理 API (`/api/v1/users`)
| 方法 | 端點 | 描述 | 範例 |
|-----|------|------|------|
| `GET` | `/api/v1/users` | 📋 取得所有使用者列表 | [查看所有使用者](http://localhost:8080/api/v1/users) |
| `GET` | `/api/v1/users/{id}` | 🔍 根據 ID 取得特定使用者 | [查看用戶1](http://localhost:8080/api/v1/users/1) |
| `POST` | `/api/v1/users` | ➕ 建立新使用者 | 需要 JSON 請求體 |
| `PUT` | `/api/v1/users/{id}` | ✏️ 更新使用者資訊 | 需要 JSON 請求體 |
| `DELETE` | `/api/v1/users/{id}` | ❌ 刪除指定使用者 | 刪除操作 |

#### 🧪 示範功能 API (`/api/v1/users/demo`)
| 方法 | 端點 | 描述 |
|-----|------|------|
| `GET` | `/api/v1/users/demo/stats` | 📊 取得使用者統計資訊 |
| `GET` | `/api/v1/users/demo/search-by-domain` | 🔎 根據郵件網域搜尋使用者 |
| `POST` | `/api/v1/users/demo/create-test-users` | 👥 批量建立測試使用者 |
| `DELETE` | `/api/v1/users/demo/clear-all` | 🧹 清除所有使用者資料 |

#### 💚 健康檢查 API
| 方法 | 端點 | 描述 |
|-----|------|------|
| `GET` | `/api/health` | ❤️ 應用程式健康狀態檢查 |
| `GET` | `/api/` | 🏠 根路徑歡迎訊息 |

### 📝 API 請求範例

#### 建立使用者
```bash
curl -X POST http://localhost:8080/api/v1/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "張三",
    "email": "zhangsan@example.com"
  }'
```

#### 查詢使用者統計
```bash
curl http://localhost:8080/api/v1/users/demo/stats
```

### 🔒 API 安全說明

- 所有 API 端點都啟用了 CORS 支援
- Spring Security 已配置但預設允許所有請求（開發模式）
- 生產環境建議啟用適當的認證機制

## 📊 監控與健康檢查

### 🩺 Spring Boot Actuator 端點

應用程式內建完整的監控和健康檢查功能：

| 端點 | URL | 描述 | 用途 |
|-----|-----|------|------|
| **健康檢查** | http://localhost:8080/actuator/health | ❤️ 應用程式整體健康狀態 | 負載均衡器健康檢查 |
| **應用資訊** | http://localhost:8080/actuator/info | ℹ️ 應用程式版本與建構資訊 | 版本追蹤 |  
| **效能指標** | http://localhost:8080/actuator/metrics | 📈 JVM 與應用程式指標 | 效能監控 |
| **Prometheus** | http://localhost:8080/actuator/prometheus | 🎯 Prometheus 格式指標 | 監控系統整合 |

### 📈 自定義業務指標

應用程式自動收集以下業務指標：

#### 使用者相關指標
- **`user.creation.count`** - 使用者建立總次數
- **`user.query.count`** - 使用者查詢總次數  
- **`user.creation.time`** - 使用者建立操作耗時
- **`user.active.count`** - 目前系統中活躍使用者數量

#### 系統效能指標
- **JVM 記憶體使用率** - 堆疊與非堆疊記憶體
- **垃圾回收統計** - GC 次數與耗時
- **資料庫連線池** - 活躍/閒置連線數
- **HTTP 請求統計** - 請求次數、回應時間、錯誤率

### 🔍 健康檢查細節

#### 健康檢查回應範例
```json
{
  "status": "UP",
  "components": {
    "db": {
      "status": "UP",
      "details": {
        "database": "PostgreSQL",
        "validationQuery": "isValid()"
      }
    },
    "diskSpace": {
      "status": "UP",
      "details": {
        "total": 250790436864,
        "free": 137289896960,
        "threshold": 10485760,
        "path": "/Users/app/."
      }
    }
  }
}
```

### 📊 監控系統整合

#### Prometheus 整合
```yaml
# prometheus.yml 配置範例
scrape_configs:
  - job_name: 'nice-npc-app'
    static_configs:
      - targets: ['localhost:8080']
    metrics_path: '/actuator/prometheus'
    scrape_interval: 15s
```

#### Grafana 儀表板
- 可使用 Micrometer 提供的標準 Grafana 儀表板
- 自定義業務指標視覺化
- 實時系統效能監控

### 🚨 告警設定建議

建議針對以下指標設定告警：
- 應用程式健康檢查失敗
- JVM 記憶體使用率 > 85%
- 資料庫連線失敗
- HTTP 5xx 錯誤率 > 1%
- 平均回應時間 > 2 秒


## ⚙️ 配置說明

### 🌍 多環境配置

專案採用 Spring Boot Profile 機制支援多環境部署：

| 配置檔案 | 用途 | 說明 |
|---------|------|------|
| `application.yml` | 🔧 基礎配置 | 所有環境共用的配置項 |
| `application-dev.yml` | 🚧 開發環境 | 開發階段使用，啟用詳細日誌 |
| `application-prod.yml` | 🚀 生產環境 | 生產部署配置，效能最佳化 |
| `application-web.yml` | 🌐 Web 模式 | Web 應用特定配置 |

### 🔑 核心配置項目

#### 應用程式基本設定
```yaml
app:
  name: nice-npc-springboot-template
  version: 1.0.0
  description: "Nice NPC Spring Boot DDD Template"
```

#### 資料庫連線配置
```yaml
app:
  database:
    host: ${DB_HOST:localhost}        # 資料庫主機位址
    port: ${DB_PORT:5432}             # PostgreSQL 預設埠號
    name: ${DB_NAME:nice_npc_db}      # 資料庫名稱
    username: ${DB_USERNAME:postgres}  # 資料庫使用者名稱
    password: ${DB_PASSWORD:password} # 資料庫密碼

spring:
  datasource:
    url: jdbc:postgresql://${app.database.host}:${app.database.port}/${app.database.name}
    username: ${app.database.username}
    password: ${app.database.password}
    driver-class-name: org.postgresql.Driver
    
  # JPA/Hibernate 配置
  jpa:
    hibernate:
      ddl-auto: validate              # 生產環境建議使用 validate
    show-sql: false                   # 生產環境關閉 SQL 日誌
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.PostgreSQLDialect
```

#### 快取系統配置
```yaml
app:
  cache:
    type: ${CACHE_TYPE:caffeine}      # 快取類型
    spec: ${CACHE_SPEC:maximumSize=1000,expireAfterAccess=600s,expireAfterWrite=300s}

spring:
  cache:
    type: caffeine
    caffeine:
      spec: ${app.cache.spec}
```

#### 監控與健康檢查
```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus  # 暴露的監控端點
  endpoint:
    health:
      show-details: when-authorized              # 健康檢查詳細資訊
  metrics:
    export:
      prometheus:
        enabled: true                            # 啟用 Prometheus 指標匯出
```

#### 安全配置
```yaml
spring:
  security:
    web:
      ignoring: /actuator/health,/api/health    # 忽略安全檢查的端點
```

### 🔧 環境變數對照表

| 環境變數 | 預設值 | 說明 | 範例 |
|---------|--------|------|------|
| `DB_HOST` | `localhost` | 資料庫主機 | `db.example.com` |
| `DB_PORT` | `5432` | 資料庫埠號 | `5432` |
| `DB_NAME` | `nice_npc_db` | 資料庫名稱 | `production_db` |
| `DB_USERNAME` | `postgres` | 資料庫使用者 | `app_user` |
| `DB_PASSWORD` | `password` | 資料庫密碼 | `secure_password` |
| `CACHE_TYPE` | `caffeine` | 快取類型 | `redis` |
| `CACHE_SPEC` | 見上述配置 | 快取規格 | 自定義快取參數 |

### 📝 配置最佳實踐

1. **🔒 敏感資訊管理**
   - 生產環境密碼必須使用環境變數
   - 避免在配置檔案中硬編碼敏感資訊
   - 考慮使用 Spring Cloud Config 或 Kubernetes Secrets

2. **🏃 效能調校**
   - 生產環境關閉 `show-sql`
   - 調整資料庫連線池大小
   - 根據實際需求配置快取策略

3. **📊 監控配置**
   - 生產環境限制暴露的 Actuator 端點
   - 配置適當的健康檢查逾時時間
   - 設定業務指標收集

## 🧪 測試策略

### 🎯 測試金字塔架構

```
        🔺 E2E Tests (端到端測試)
       ────────────────────────────
      🔷🔷 Integration Tests (整合測試)  
    ────────────────────────────────────
  🟦🟦🟦🟦 Unit Tests (單元測試)
──────────────────────────────────────────
```

### 📋 測試層級說明

#### 🟦 Unit Tests（單元測試）- 70%
- **測試範圍**：Domain 和 Application 層的業務邏輯
- **測試工具**：JUnit 5 + Mockito
- **測試重點**：
  - 領域實體的業務規則驗證
  - 應用服務的業務流程協調
  - 指令/查詢處理器的邏輯正確性

#### 🔷 Integration Tests（整合測試）- 20%
- **測試範圍**：Repository 層與資料庫的整合
- **測試工具**：Spring Boot Test + TestContainers
- **測試重點**：
  - JPA Repository 的 CRUD 操作
  - 資料庫查詢的正確性
  - 事務管理的正確性

#### 🔺 API Tests（API 測試）- 10%
- **測試範圍**：Controller 層的 REST API
- **測試工具**：MockMvc + TestContainers
- **測試重點**：
  - HTTP 端點的回應格式
  - 請求參數驗證
  - 錯誤處理機制

### 🚀 執行測試

#### 執行所有測試
```bash
# 執行全專案測試套件
./gradlew test

# 執行測試並生成覆蓋率報告
./gradlew test jacocoTestReport
```

#### 按模組執行測試
```bash
# 只測試 Domain 層
./gradlew :domain:test

# 只測試 Application 層  
./gradlew :application:test

# 只測試 Infrastructure 層
./gradlew :infrastructure:test
```

#### 執行特定測試類別
```bash
# 執行特定測試類別
./gradlew test --tests "UserServiceTest"

# 執行包含特定模式的測試
./gradlew test --tests "*User*"
```

### 📊 測試覆蓋率報告

#### 查看覆蓋率報告
```bash
# 生成覆蓋率報告
./gradlew jacocoTestReport

# 開啟覆蓋率報告 (Windows)
start build/reports/jacoco/test/html/index.html

# 開啟覆蓋率報告 (macOS)
open build/reports/jacoco/test/html/index.html

# 開啟覆蓋率報告 (Linux)
xdg-open build/reports/jacoco/test/html/index.html
```

#### 覆蓋率目標
| 層級 | 最低覆蓋率 | 目標覆蓋率 |
|------|-----------|-----------|
| **Domain** | 90% | 95% |
| **Application** | 85% | 90% |
| **Infrastructure** | 70% | 80% |
| **整體專案** | 80% | 85% |

### 🛠️ 測試最佳實踐

#### 1️⃣ 測試命名規範
```java
// Given-When-Then 模式
@Test
void shouldCreateUser_WhenValidDataProvided_ThenReturnUserWithId() {
    // Given (準備測試資料)
    // When (執行測試動作)  
    // Then (驗證測試結果)
}
```

#### 2️⃣ 測試資料管理
```java
// 使用 Test Fixtures 管理測試資料
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {
    
    @BeforeAll
    void setupTestData() {
        // 初始化共用測試資料
    }
    
    @AfterEach  
    void cleanupTestData() {
        // 清理測試資料
    }
}
```

#### 3️⃣ Mock 使用原則
- 對外部依賴進行 Mock（資料庫、外部 API）
- Domain 層儘量避免使用 Mock
- 使用 @MockitoJUnitJupiter 簡化 Mock 管理

### 🔧 測試配置

#### 測試專用配置檔案
```yaml
# application-test.yml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
```

#### TestContainers 整合
```java
@SpringBootTest
@Testcontainers
class DatabaseIntegrationTest {
    
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");
}
```


## 🔧 開發指南

### 📚 編程規範

本專案的詳細編程規範已定義在 [`CODING_RULES.yaml`](./CODING_RULES.yaml) 文件中。開發前請詳閱該文件，以確保程式碼的一致性和可維護性。

### 🌍 環境配置管理

#### 開發環境快速設定
```bash
# 1. 複製環境範本
cp infrastructure/src/main/resources/application-local.yml.example .env

# 2. 編輯資料庫配置
vim .env  # 或使用你喜歡的編輯器

# 3. Windows 用戶可使用批次檔
set-db-env.bat
```

#### 生產環境部署建議
- 使用環境變數管理敏感資訊
- 啟用適當的 Spring Profile
- 配置外部配置服務（如 Spring Cloud Config）

### 📝 程式碼提交規範

#### Git 提交訊息格式
```
<類型>(<範圍>): <簡短描述>

<詳細描述>

<相關 Issue>
```

#### 提交類型
- `feat`: 新功能
- `fix`: Bug 修復
- `docs`: 文檔更新
- `style`: 程式碼格式調整
- `refactor`: 代碼重構
- `test`: 測試相關
- `chore`: 建構工具或輔助工具變動

## 📚 相關文檔

- [編碼規範](CODING_RULES.yaml) - 專案編碼標準與最佳實踐

## 🤝 貢獻指南

1. Fork 專案
2. 建立功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交變更 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 開啟 Pull Request

## 📄 授權

本專案採用 MIT 授權條款 - 查看 [LICENSE](LICENSE) 檔案了解詳情。


## 🐳 Docker 部署

### 使用 Docker Compose
```yaml
version: '3.8'
services:
  app:
    build: .
    ports:
      - "8080:8080"
    depends_on:
      - postgres
  postgres:
    image: postgres:15
    environment:
      POSTGRES_DB: nice_npc_db
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: password
```

## 🔧 IDE 設定建議

### VS Code 推薦外掛
- Extension Pack for Java
- Spring Boot Extension Pack
- Thunder Client (API 測試)
- GitLens

### IntelliJ IDEA 設定
- 啟用 Lombok 外掛
- 配置 Code Style
- 設定 Live Templates

## 🚨 常見問題排除

### 應用程式無法啟動
1. 檢查 Java 版本是否為 21+
2. 確認資料庫連線設定
3. 檢查埠號是否被占用

### 資料庫連線問題
1. 確認 PostgreSQL 服務是否啟動
2. 檢查防火牆設定
3. 驗證資料庫認證資訊

## ⚡ 效能調優

### JVM 參數建議
```bash
-Xms512m -Xmx2g
-XX:+UseG1GC
-XX:MaxGCPauseMillis=200
```

### 資料庫最佳化
- 設定適當的連線池大小
- 啟用查詢快取
- 定期分析慢查詢

## 🆕 版本更新提醒

> 本專案目前使用 Spring Boot 3.2.1，建議定期檢查依賴庫是否有新版本，並依照官方升級指南進行更新。

## 👥 團隊

- **Nice NPC Team** - 初始開發團隊

## 📞 支援

如有問題或建議，請：

1. 開啟 [GitHub Issue](https://github.com/PhilChenTech/spring-boot-web-template/issues)
2. 查看專案文檔
3. 聯繫開發團隊

---

**Happy Coding! 🚀**