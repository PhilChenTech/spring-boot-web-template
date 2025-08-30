# PostgreSQL 資料庫設定指南

## 1. 安裝 PostgreSQL

### Windows
1. 下載 PostgreSQL 安裝程式：https://www.postgresql.org/download/windows/
2. 執行安裝程式，設定預設密碼為 `postgres`
3. 確認 PostgreSQL 服務已啟動

### macOS
```bash
# 使用 Homebrew 安裝
brew install postgresql
brew services start postgresql
```

### Linux (Ubuntu/Debian)
```bash
sudo apt update
sudo apt install postgresql postgresql-contrib
sudo systemctl start postgresql
sudo systemctl enable postgresql
```

## 2. 建立資料庫

### 使用 psql 命令列工具
```sql
-- 連接到 PostgreSQL
psql -U postgres -h localhost

-- 建立開發環境資料庫
CREATE DATABASE springboot_template_dev;

-- 建立正式環境資料庫
CREATE DATABASE springboot_template_prod;

-- 建立一般環境資料庫
CREATE DATABASE springboot_template;


-- 確認資料庫建立成功
\l
```

### 使用 pgAdmin（圖形化介面）
1. 開啟 pgAdmin
2. 右鍵點擊 "Databases" -> "Create" -> "Database..."
3. 輸入資料庫名稱：`springboot_template`、`springboot_template_dev`、`springboot_template_prod`
4. 點擊 "Save"

## 3. 環境變數設定

### 開發環境
```bash
# Windows PowerShell
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD="test"

# Linux/macOS
export DB_USERNAME=postgres
export DB_PASSWORD=test
```

### 生產環境
建議使用環境變數或配置檔案來設定資料庫連接資訊：

```bash
# 設定環境變數
export DB_USERNAME=postgres
export DB_PASSWORD=test
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=springboot_template_prod
```

## 4. 連接測試

### 測試連接
```bash
# 使用 psql 測試連接
psql -h localhost -U postgres -d springboot_template

```

## 5. 應用程式設定檔案

專案中的配置檔案說明：

- `application.yml` - 預設配置（PostgreSQL）
- `application-dev.yml` - 開發環境配置
- `application-prod.yml` - 生產環境配置

## 6. 啟動應用程式

```bash
# 開發環境
./gradlew :bootstrap:bootRun -Dapp.type=web --args="--spring.profiles.active=dev"

# 生產環境
./gradlew :bootstrap:bootRun -Dapp.type=web --args="--spring.profiles.active=prod"

# 使用預設配置
./gradlew :bootstrap:bootRun -Dapp.type=web
```

## 7. 常見問題排除

### 連接失敗
1. 確認 PostgreSQL 服務正在執行
2. 檢查防火牆設定
3. 確認資料庫名稱、使用者名稱和密碼正確
4. 檢查 `pg_hba.conf` 檔案的認證設定


### 資料庫編碼問題
建立資料庫時指定編碼：
```sql
CREATE DATABASE springboot_template 
    WITH ENCODING 'UTF8' 
    LC_COLLATE='en_US.UTF-8' 
    LC_CTYPE='en_US.UTF-8' 
    TEMPLATE=template0;
```
