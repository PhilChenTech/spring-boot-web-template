@echo off
REM Spring Boot 應用程式啟動腳本（支援 PostgreSQL 環境變數）

REM 設定控制台編碼為 UTF-8 以避免亂碼
chcp 65001 >nul

set APP_TYPE=%1
set PROFILE=%2

if "%APP_TYPE%"=="" set APP_TYPE=web
if "%PROFILE%"=="" set PROFILE=dev

echo Starting application...
echo 正在啟動應用程式...
echo Application type: %APP_TYPE%
echo 應用程式類型: %APP_TYPE%
echo Profile: %PROFILE%
echo 設定檔: %PROFILE%
echo.

REM 設定環境變數（如果未設定的話）
if "%DB_HOST%"=="" set DB_HOST=localhost
if "%DB_PORT%"=="" set DB_PORT=5432
if "%DB_USERNAME%"=="" set DB_USERNAME=postgres
if "%DB_PASSWORD%"=="" set DB_PASSWORD=test

REM 根據 profile 設定資料庫名稱
if "%PROFILE%"=="dev" (
    if "%DB_NAME%"=="" set DB_NAME=springboot_template_db_dev
) else if "%PROFILE%"=="prod" (
    if "%DB_NAME%"=="" set DB_NAME=springboot_template_db_prod
) else (
    if "%DB_NAME%"=="" set DB_NAME=springboot_template_db
)

REM 顯示目前資料庫設定
    echo Database settings:
    echo 資料庫設定:
    echo   Host: %DB_HOST%:%DB_PORT%
    echo   主機: %DB_HOST%:%DB_PORT%
    echo   Database: %DB_NAME%
    echo   資料庫: %DB_NAME%
    echo   User: %DB_USERNAME%
    echo   使用者: %DB_USERNAME%
    echo.
)

REM 檢查 PostgreSQL 是否正在執行（僅在非測試環境下）
if not "%PROFILE%"=="test" (
    echo Checking PostgreSQL connection...
    echo 檢查 PostgreSQL 連接...
    powershell -Command "[Console]::OutputEncoding = [System.Text.Encoding]::UTF8; try { $connection = New-Object System.Data.SqlClient.SqlConnection; $connection.ConnectionString = 'Server=localhost;Database=postgres;Integrated Security=true;'; $connection.Open(); $connection.Close(); Write-Host 'PostgreSQL connection OK' -ForegroundColor Green; Write-Host 'PostgreSQL 連接正常' -ForegroundColor Green } catch { Write-Host 'Warning: Cannot connect to PostgreSQL, please ensure service is running' -ForegroundColor Yellow; Write-Host '警告：無法連接到 PostgreSQL，請確認服務已啟動' -ForegroundColor Yellow }"
    echo.
)

REM 根據應用程式類型和環境啟動
if "%APP_TYPE%"=="web" (
    echo 正在啟動 Web 應用程式（%PROFILE% 環境）...
    .\gradlew :bootstrap:bootRun -Dapp.type=web --args="--spring.profiles.active=%PROFILE%"
) else if "%APP_TYPE%"=="desktop" (
    echo 正在啟動桌面應用程式（%PROFILE% 環境）...
    .\gradlew :bootstrap:bootRun -Dapp.type=desktop --args="--spring.profiles.active=%PROFILE%"
) else if "%APP_TYPE%"=="batch" (
    echo 正在啟動批次應用程式（%PROFILE% 環境）...
    .\gradlew :bootstrap:bootRun -Dapp.type=batch --args="--spring.profiles.active=%PROFILE%"
) else (
    echo 未知的應用程式類型: %APP_TYPE%
    echo.
    echo 使用方式: start-postgresql-app.bat [APP_TYPE] [PROFILE]
    echo APP_TYPE: web^|desktop^|batch （預設: web）
    echo PROFILE: dev^|test^|prod^|local （預設: dev）
    echo.
    echo 範例:
    echo   start-postgresql-app.bat web dev
    echo   start-postgresql-app.bat desktop prod
    echo   start-postgresql-app.bat batch test
    exit /b 1
)

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo 應用程式啟動失敗！
    echo 請檢查：
    echo 1. PostgreSQL 服務是否正在執行
    echo 2. 資料庫是否已建立
    echo 3. 資料庫連接設定是否正確
    echo.
    echo 您可以執行 setup-postgresql.bat 來設定資料庫
    pause
    exit /b %ERRORLEVEL%
)
