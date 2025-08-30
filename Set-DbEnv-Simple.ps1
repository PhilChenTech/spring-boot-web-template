# 簡單的環境變數設置腳本
# 設置資料庫環境變數

Write-Host "Setting environment variables..." -ForegroundColor Green

# 資料庫設定
$env:DB_HOST = "localhost"
$env:DB_PORT = "5432"
$env:DB_NAME = "springboot_template_db"
$env:DB_USERNAME = "postgres"
$env:DB_PASSWORD = "test"

# 安全設定
$env:ADMIN_USERNAME = "admin"
$env:ADMIN_PASSWORD = "admin123"
$env:ADMIN_ROLES = "ADMIN"

# 應用程式設定
$env:APP_NAME = "nice-npc-springboot-template"
$env:APP_VERSION = "1.0.0"

# Flyway 設定
$env:FLYWAY_ENABLED = "true"

# 日誌設定
$env:LOG_LEVEL_APP = "INFO"
$env:LOG_LEVEL_ROOT = "INFO"
$env:LOG_LEVEL_SQL = "WARN"

# 服務器設定
$env:SERVER_PORT = "8080"

# 快取設定
$env:CACHE_TYPE = "caffeine"

Write-Host "環境變數設置完成！" -ForegroundColor Green
Write-Host ""
Write-Host "當前資料庫設定:" -ForegroundColor Yellow
Write-Host "  主機: $env:DB_HOST`:$env:DB_PORT" -ForegroundColor Cyan
Write-Host "  資料庫: $env:DB_NAME" -ForegroundColor Cyan
Write-Host "  使用者: $env:DB_USERNAME" -ForegroundColor Cyan
Write-Host ""
Write-Host "Admin setting:" -ForegroundColor Yellow
Write-Host "  Username: $env:ADMIN_USERNAME" -ForegroundColor Cyan
Write-Host "  Password: $env:ADMIN_PASSWORD" -ForegroundColor Cyan
