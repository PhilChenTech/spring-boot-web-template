# 環境變數配置指南

## 概述

為了提高安全性，專案現在支援使用環境變數來配置資料庫連接資訊。這樣可以避免將敏感資訊（如密碼）硬編碼在配置檔案中。

## 支援的環境變數

| 環境變數 | 說明 | 預設值 | 範例 |
|---------|------|--------|------|
| `DB_HOST` | 資料庫主機位址 | `localhost` | `localhost`, `db.example.com` |
| `DB_PORT` | 資料庫連接埠 | `5432` | `5432`, `5433` |
| `DB_NAME` | 資料庫名稱 | 依環境而定 | `springboot_template_dev` |
| `DB_USERNAME` | 資料庫使用者名稱 | `postgres` | `myuser`, `app_user` |
| `DB_PASSWORD` | 資料庫密碼 | `postgres` | `mypassword` |

## 設定方式

### 1. 使用提供的腳本

#### Windows 批次檔
```bash
# 設定開發環境
.\set-db-env.bat dev

# 設定生產環境
.\set-db-env.bat prod
```

#### PowerShell 腳本
```powershell
# 設定開發環境
.\Set-DbEnv.ps1 dev

# 設定生產環境
.\Set-DbEnv.ps1 prod
```

### 2. 手動設定環境變數

#### Windows PowerShell
```powershell
$env:DB_HOST = "localhost"
$env:DB_PORT = "5432"
$env:DB_USERNAME = "myuser"
$env:DB_PASSWORD = "mypassword"
$env:DB_NAME = "springboot_template_dev"
```

#### Windows 命令提示字元
```cmd
set DB_HOST=localhost
set DB_PORT=5432
set DB_USERNAME=myuser
set DB_PASSWORD=mypassword
set DB_NAME=springboot_template_dev
```

#### Linux/macOS
```bash
export DB_HOST=localhost
export DB_PORT=5432
export DB_USERNAME=myuser
export DB_PASSWORD=mypassword
export DB_NAME=springboot_template_dev
```

### 3. 使用 .env 檔案

1. 複製範例檔案：
   ```bash
   copy .env.example .env
   ```

2. 編輯 `.env` 檔案設定您的資料庫資訊

3. 使用支援 `.env` 檔案的工具或 IDE 執行應用程式

## 不同環境的資料庫名稱

| 環境 | 預設資料庫名稱 |
|------|----------------|
| 開發環境 (`dev`) | `springboot_template_dev` |
| 生產環境 (`prod`) | `springboot_template_prod` |
| 預設環境 | `springboot_template` |

## 安全最佳實踐

### 1. 密碼管理
- ❌ **不要** 將密碼寫在配置檔案中
- ✅ **使用** 環境變數或密鑰管理系統
- ✅ **使用** 強密碼
- ✅ **定期** 更換密碼

### 2. 環境隔離
- ✅ 不同環境使用不同的資料庫
- ✅ 不同環境使用不同的使用者帳號
- ✅ 生產環境使用專用的高權限帳號

### 3. 版本控制
- ❌ **絕對不要** 將 `.env` 檔案提交到 Git
- ✅ **提交** `.env.example` 檔案作為範本
- ✅ **確保** `.gitignore` 包含 `.env`

### 4. 部署建議

#### 開發環境
```bash
# 設定環境變數
.\Set-DbEnv.ps1 dev

# 啟動應用程式
.\start-postgresql-app.bat web dev
```

#### 生產環境
```bash
# 在系統層級設定環境變數（推薦）
# 或使用配置管理工具（如 Docker secrets, Kubernetes secrets）

# 啟動應用程式
.\start-postgresql-app.bat web prod
```

## 故障排除

### 1. 檢查環境變數是否已設定
```powershell
# PowerShell
echo $env:DB_USERNAME
echo $env:DB_PASSWORD

# Windows CMD
echo %DB_USERNAME%
echo %DB_PASSWORD%
```

### 2. 應用程式啟動時的資料庫設定顯示
啟動腳本會顯示目前使用的資料庫設定：
```
資料庫設定:
  主機: localhost:5432
  資料庫: springboot_template_dev
  使用者: postgres
```

### 3. 常見問題

**問題**: 應用程式無法連接到資料庫
**解決方案**: 
1. 檢查環境變數是否正確設定
2. 確認 PostgreSQL 服務正在執行
3. 驗證資料庫名稱、使用者名稱和密碼

**問題**: 環境變數在重新開啟終端後消失
**解決方案**: 
1. 使用系統環境變數設定
2. 在啟動腳本中設定環境變數
3. 使用 `.env` 檔案搭配支援的工具

## 支援的 IDE 整合

### IntelliJ IDEA
1. Run Configuration → Environment Variables
2. 設定需要的環境變數

### VS Code
1. 安裝 `DotENV` 擴展
2. 在 `.vscode/launch.json` 中設定環境變數

### Eclipse
1. Run Configurations → Environment
2. 添加環境變數

---

**注意**: 請根據您的具體環境和安全要求調整這些設定。
