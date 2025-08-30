# Console 亂碼問題解決方案

## 問題描述
在 Windows 環境下執行 PowerShell 或批次檔時，中文字元可能會顯示為亂碼。

## 解決方案

### 1. 立即修正編碼（推薦）
執行編碼修正腳本：
```powershell
.\Fix-Console-Encoding.ps1
```

### 2. 手動設定 PowerShell 編碼
```powershell
# 設定控制台編碼為 UTF-8
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
$OutputEncoding = [System.Text.Encoding]::UTF8
chcp 65001
```

### 3. 手動設定命令提示字元編碼
```cmd
REM 設定控制台代碼頁為 UTF-8
chcp 65001
```

## 已修正的腳本

### PowerShell 腳本
- ✅ `Set-DbEnv.ps1` - 已加入編碼修正和雙語顯示
- ✅ `Fix-Console-Encoding.ps1` - 專門的編碼修正腳本

### 批次檔案
- ✅ `set-db-env.bat` - 已加入編碼修正和雙語顯示
- ✅ `start-postgresql-app.bat` - 已加入編碼修正和雙語顯示

## 特點

### 雙語顯示
所有腳本現在都提供英文和中文雙語顯示：
```
Setting PostgreSQL environment variables...
正在設定 PostgreSQL 環境變數...
```

### 自動編碼修正
所有腳本都會自動設定正確的編碼：
- PowerShell: UTF-8 輸出編碼
- 批次檔: UTF-8 代碼頁 (65001)

### 保持向後相容
- 保留了原有的功能
- 加入了英文版本的訊息
- 不影響現有的工作流程

## 使用方式

### 環境變數設定
```bash
# PowerShell (推薦)
.\Set-DbEnv.ps1 dev

# 批次檔
.\set-db-env.bat dev
```

### 應用程式啟動
```bash
# 啟動應用程式 (已修正編碼)
.\start-postgresql-app.bat web dev
```

### 編碼測試
```bash
# 測試編碼是否正常
.\Fix-Console-Encoding.ps1
```

## 常見問題

### Q: 為什麼還是有亂碼？
A: 請確認：
1. 執行 `.\Fix-Console-Encoding.ps1`
2. 使用 PowerShell 7 或更新版本
3. 確認終端機支援 UTF-8

### Q: 批次檔中文顯示不正常？
A: 批次檔會自動設定 `chcp 65001`，如果還有問題：
1. 手動執行 `chcp 65001`
2. 使用 PowerShell 版本的腳本

### Q: VS Code 中顯示不正常？
A: 在 VS Code 中：
1. 設定 `"terminal.integrated.defaultProfile.windows": "PowerShell"`
2. 確認檔案編碼為 UTF-8
3. 重新啟動終端機

## 技術細節

### PowerShell 編碼設定
```powershell
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
$OutputEncoding = [System.Text.Encoding]::UTF8
```

### 批次檔編碼設定
```cmd
chcp 65001 >nul
```

### 顏色顯示
PowerShell 腳本使用顏色來區分不同類型的訊息：
- 綠色：成功訊息
- 黃色：警告訊息
- 藍色：資訊訊息
- 紅色：錯誤訊息

---

**解決方案已完成！現在所有腳本都支援正確的中文顯示。**
