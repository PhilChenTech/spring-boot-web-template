# 設定 PostgreSQL 環境變數 (PowerShell 版本)
param(
    [string]$Environment = "dev"
)

# 設定控制台編碼為 UTF-8 以避免亂碼
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
$OutputEncoding = [System.Text.Encoding]::UTF8

Write-Host "Setting PostgreSQL environment variables..." -ForegroundColor Green
Write-Host "正在設定 PostgreSQL 環境變數..." -ForegroundColor Green
Write-Host ""

# 預設資料庫連接設定
$env:DB_HOST = "localhost"
$env:DB_PORT = "5432"
$env:DB_USERNAME = "postgres"
$env:DB_PASSWORD = "test"

# 根據環境設定不同的資料庫名稱
switch ($Environment.ToLower()) {
    "dev" {
        $env:DB_NAME = "springboot_template_dev"
        Write-Host "Setting development database: $($env:DB_NAME)" -ForegroundColor Yellow
        Write-Host "設定開發環境資料庫: $($env:DB_NAME)" -ForegroundColor Yellow
    }
    "prod" {
        $env:DB_NAME = "springboot_template_prod"
        Write-Host "Setting production database: $($env:DB_NAME)" -ForegroundColor Red
        Write-Host "設定生產環境資料庫: $($env:DB_NAME)" -ForegroundColor Red
    }
    default {
        $env:DB_NAME = "springboot_template"
        Write-Host "Setting default database: $($env:DB_NAME)" -ForegroundColor Blue
        Write-Host "設定預設資料庫: $($env:DB_NAME)" -ForegroundColor Blue
    }
}

Write-Host ""
Write-Host "Current environment variables:" -ForegroundColor Green
Write-Host "目前環境變數設定:" -ForegroundColor Green
Write-Host "DB_HOST=$($env:DB_HOST)"
Write-Host "DB_PORT=$($env:DB_PORT)"
Write-Host "DB_NAME=$($env:DB_NAME)"
Write-Host "DB_USERNAME=$($env:DB_USERNAME)"
Write-Host "DB_PASSWORD=[Set/已設定]"
Write-Host ""
Write-Host "Note: These environment variables are only valid in the current PowerShell session." -ForegroundColor Yellow
Write-Host "注意: 這些環境變數只在目前的 PowerShell 會話中有效。" -ForegroundColor Yellow
Write-Host ""
Write-Host "Usage: .\Set-DbEnv.ps1 [dev|prod|test|default]" -ForegroundColor Cyan
Write-Host "使用方式: .\Set-DbEnv.ps1 [dev|prod|test|default]" -ForegroundColor Cyan
Write-Host "Example: .\Set-DbEnv.ps1 dev" -ForegroundColor Cyan
Write-Host "範例: .\Set-DbEnv.ps1 dev" -ForegroundColor Cyan
