# Set-DbEnv-Simple.ps1
# 簡化版的環境變數設定腳本，專注於功能而非複雜的編碼處理

param(
    [string]$Environment = "dev"
)

# 基本編碼設定
chcp 65001 | Out-Null

Write-Host "Setting PostgreSQL environment variables for: $Environment" -ForegroundColor Green

# 預設資料庫連接設定
$env:DB_HOST = "localhost"
$env:DB_PORT = "5432"
$env:DB_USERNAME = "postgres"
$env:DB_PASSWORD = "test"

# 根據環境設定不同的資料庫名稱
switch ($Environment.ToLower()) {
    "dev" {
        $env:DB_NAME = "springboot_template_dev"
        Write-Host "Development database: $($env:DB_NAME)" -ForegroundColor Yellow
    }
    "prod" {
        $env:DB_NAME = "springboot_template_prod"
        Write-Host "Production database: $($env:DB_NAME)" -ForegroundColor Red
    }
    "test" {
        Write-Host "Test environment uses H2 in-memory database" -ForegroundColor Cyan
        return
    }
    default {
        $env:DB_NAME = "springboot_template"
        Write-Host "Default database: $($env:DB_NAME)" -ForegroundColor Blue
    }
}

Write-Host ""
Write-Host "Environment variables set:" -ForegroundColor Green
Write-Host "DB_HOST=$($env:DB_HOST)"
Write-Host "DB_PORT=$($env:DB_PORT)"
Write-Host "DB_NAME=$($env:DB_NAME)"
Write-Host "DB_USERNAME=$($env:DB_USERNAME)"
Write-Host "DB_PASSWORD=[Set]"
Write-Host ""
Write-Host "Usage: .\Set-DbEnv-Simple.ps1 [dev|prod|test|default]" -ForegroundColor Cyan
