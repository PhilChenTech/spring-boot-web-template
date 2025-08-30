@echo off
echo 設置環境變數...

REM 資料庫設定
set DB_HOST=localhost
set DB_PORT=5432
set DB_NAME=springboot_template_db
set DB_USERNAME=postgres
set DB_PASSWORD=test

REM 安全設定
set ADMIN_USERNAME=admin
set ADMIN_PASSWORD=admin123
set ADMIN_ROLES=ADMIN

REM 應用程式設定
set APP_NAME=nice-npc-springboot-template
set APP_VERSION=1.0.0

REM Flyway 設定
set FLYWAY_ENABLED=true

REM 日誌設定
set LOG_LEVEL_APP=INFO
set LOG_LEVEL_ROOT=INFO
set LOG_LEVEL_SQL=WARN

REM 服務器設定
set SERVER_PORT=8080

REM 快取設定
set CACHE_TYPE=caffeine

echo 環境變數設置完成！
echo.
echo 當前資料庫設定:
echo   主機: %DB_HOST%:%DB_PORT%
echo   資料庫: %DB_NAME%
echo   使用者: %DB_USERNAME%
echo.
echo 管理員設定:
echo   使用者名稱: %ADMIN_USERNAME%
echo   密碼: %ADMIN_PASSWORD%
