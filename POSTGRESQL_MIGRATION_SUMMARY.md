# PostgreSQL 遷移完成總結

## 已完成的變更

### 1. 依賴配置更新
- ✅ 在 `adapter-outbound/build.gradle` 中添加了 PostgreSQL 驅動依賴

### 2. 資料庫配置檔案更新
- ✅ `application.yml` - 預設配置改為 PostgreSQL
- ✅ `application-dev.yml` - 開發環境 PostgreSQL 配置
- ✅ `application-prod.yml` - 新增生產環境配置
- ✅ `application-local.yml.example` - 本地開發配置範例

### 3. 實體類別更新
- ✅ `UserEntity.java` 添加了 `createdAt` 和 `updatedAt` 時間戳記欄位
- ✅ `UserMapper.java` 更新了映射規則，忽略時間戳記欄位以保持領域層純淨

### 4. 資料庫初始化腳本
- ✅ `database/init-postgresql.sql` - 手動執行的 SQL 初始化腳本
- ✅ `database/migrations/V1__Create_users_table.sql` - 資料表建立和初始資料
- ✅ `setup-postgresql.bat` - Windows 自動化設定腳本
- ✅ `test-postgresql-connection.bat` - 連接測試腳本

### 5. 啟動腳本
- ✅ `start-postgresql-app.bat` - 支援多環境的新啟動腳本

### 6. 文件更新
- ✅ `POSTGRESQL_SETUP.md` - 詳細的 PostgreSQL 設定指南
- ✅ `README.md` - 更新了快速開始指南和資料庫配置說明

## 資料庫配置摘要

### 環境配置
- **開發環境 (dev)**: `springboot_template_dev`
- **生產環境 (prod)**: `springboot_template_prod`
- **預設環境**: `springboot_template`

### 連接參數
```yaml
# 預設連接設定
url: jdbc:postgresql://localhost:5432/springboot_template
username: postgres
password: postgres
```

## 快速使用方式

### 1. 設定資料庫
```bash
# 方法一：自動化腳本
.\setup-postgresql.bat

# 方法二：手動執行 SQL
psql -U postgres -f database\init-postgresql.sql
```

### 2. 測試連接
```bash
.\test-postgresql-connection.bat
```

### 3. 啟動應用程式
```bash
# 開發環境
.\start-postgresql-app.bat web dev

# 生產環境
.\start-postgresql-app.bat web prod
```

## 注意事項

### 安全性考量
1. **密碼安全**: 生產環境請使用環境變數設定資料庫密碼
2. **用戶權限**: 建議建立專用的應用程式使用者而非使用 postgres 超級使用者
3. **網路安全**: 正式環境請適當配置防火牆和 SSL 連接

### 效能最佳化
1. **連接池**: 已配置 HikariCP 連接池參數
2. **索引**: 在 email 和 name 欄位上建立了索引
3. **JPA 設定**: 適當配置了 Hibernate 方言和 DDL 策略

### 開發建議
2. **環境切換**: 使用 Spring Profiles 輕鬆在不同環境間切換
3. **本地配置**: 複製 `application-local.yml.example` 來建立個人的本地配置

## 故障排除

### 常見問題
1. **連接失敗**: 檢查 PostgreSQL 服務是否啟動
2. **權限問題**: 確認使用者有足夠的資料庫權限
3. **編碼問題**: 確保資料庫建立時指定了正確的編碼（UTF8）

### 檢查清單
- [ ] PostgreSQL 服務正在執行
- [ ] 資料庫已建立
- [ ] 使用者權限正確
- [ ] 應用程式配置檔案正確
- [ ] 網路連接正常

---

**遷移完成！您的 Spring Boot 專案現在已成功配置為使用 PostgreSQL 資料庫。**
