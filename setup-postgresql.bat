@echo off
REM PostgreSQL 資料庫設定腳本

echo 正在設定 PostgreSQL 資料庫...

REM 檢查 PostgreSQL 是否已安裝
where psql >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo 錯誤：找不到 psql 命令。請確認 PostgreSQL 已正確安裝並添加到 PATH 環境變數中。
    echo 您可以從以下網址下載 PostgreSQL: https://www.postgresql.org/download/windows/
    pause
    exit /b 1
)

echo PostgreSQL 已找到，正在建立資料庫...

REM 建立資料庫的 SQL 腳本
echo CREATE DATABASE IF NOT EXISTS springboot_template_db; > temp_setup.sql
echo CREATE DATABASE IF NOT EXISTS springboot_template_db_dev; >> temp_setup.sql
echo CREATE DATABASE IF NOT EXISTS springboot_template_db_prod; >> temp_setup.sql

REM 執行 SQL 腳本
echo 請輸入 PostgreSQL postgres 使用者的密碼（預設通常是 postgres）:
psql -U postgres -h localhost -f temp_setup.sql

if %ERRORLEVEL% EQU 0 (
    echo.
    echo 資料庫設定完成！
    echo 已建立以下資料庫：
    echo - springboot_template_db
    echo - springboot_template_db_dev  
    echo - springboot_template_db_prod
    echo.
) else (
    echo.
    echo 資料庫設定過程中發生錯誤。
    echo 請檢查 PostgreSQL 是否正在執行，以及使用者密碼是否正確。
)

REM 清理暫存檔案
del temp_setup.sql

echo.
echo 按任意鍵繼續...
pause >nul
