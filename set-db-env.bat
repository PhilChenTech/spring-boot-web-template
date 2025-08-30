@echo off
REM 設定 PostgreSQL 環境變數
REM 此腳本會設定目前命令提示字元會話的環境變數

REM 設定控制台編碼為 UTF-8 以避免亂碼
chcp 65001 >nul

echo Setting PostgreSQL environment variables...
echo 正在設定 PostgreSQL 環境變數...
echo.

REM 預設資料庫連接設定
set DB_HOST=localhost
set DB_PORT=5432
set DB_USERNAME=postgres
set DB_PASSWORD=test

REM 根據環境設定不同的資料庫名稱
set ENVIRONMENT=%1
if "%ENVIRONMENT%"=="" set ENVIRONMENT=dev

if "%ENVIRONMENT%"=="dev" (
    set DB_NAME=springboot_template_dev
    echo Setting development database: %DB_NAME%
    echo 設定開發環境資料庫: %DB_NAME%
) else if "%ENVIRONMENT%"=="prod" (
    set DB_NAME=springboot_template_prod
    echo Setting production database: %DB_NAME%
    echo 設定生產環境資料庫: %DB_NAME%
) else if "%ENVIRONMENT%"=="test" (
    echo Test environment uses H2 in-memory database
    echo 測試環境使用 H2 記憶體資料庫
    goto :end
) else (
    set DB_NAME=springboot_template
    echo Setting default database: %DB_NAME%
    echo 設定預設資料庫: %DB_NAME%
)

echo.
echo Current environment variables:
echo 目前環境變數設定:
echo DB_HOST=%DB_HOST%
echo DB_PORT=%DB_PORT%
echo DB_NAME=%DB_NAME%
echo DB_USERNAME=%DB_USERNAME%
echo DB_PASSWORD=[Set/已設定]
echo.
echo Note: These environment variables are only valid in the current command prompt session.
echo 注意: 這些環境變數只在目前的命令提示字元會話中有效。
echo For permanent settings, use Windows system environment variables.
echo 如需永久設定，請使用 Windows 系統環境變數設定。

:end
echo.
echo Usage: set-db-env.bat [dev^|prod^|default]
echo 使用方式: set-db-env.bat [dev^|prod^|default]
echo Example: set-db-env.bat dev
echo 範例: set-db-env.bat dev
