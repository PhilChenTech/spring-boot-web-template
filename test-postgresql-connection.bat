@echo off
REM PostgreSQL 連接測試腳本

echo 正在測試 PostgreSQL 連接...
echo.

REM 測試不同環境的資料庫連接
echo 測試預設資料庫連接...
psql -h localhost -U postgres -d springboot_template_db -c "SELECT version();"

if %ERRORLEVEL% EQU 0 (
    echo [✓] 預設資料庫連接成功
) else (
    echo [✗] 預設資料庫連接失敗
)

echo.
echo 測試開發環境資料庫連接...
psql -h localhost -U postgres -d springboot_template_db_dev -c "SELECT version();"

if %ERRORLEVEL% EQU 0 (
    echo [✓] 開發環境資料庫連接成功
) else (
    echo [✗] 開發環境資料庫連接失敗
)

echo.
echo 測試生產環境資料庫連接...
psql -h localhost -U postgres -d springboot_template_db_prod -c "SELECT version();"

if %ERRORLEVEL% EQU 0 (
    echo [✓] 生產環境資料庫連接成功
) else (
    echo [✗] 生產環境資料庫連接失敗
)

echo.
echo 列出所有資料庫...
psql -h localhost -U postgres -c "\l"

echo.
echo 測試完成！
pause
