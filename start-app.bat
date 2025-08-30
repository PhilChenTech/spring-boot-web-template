@echo off
echo Starting Spring Boot Application...
echo.

REM 設定 JAVA_OPTS 以避免記憶體問題
set JAVA_OPTS=-Xmx1024m -XX:+UseG1GC

REM 使用 Gradle 啟動，但設定超時
timeout /t 2 /nobreak > nul
.\gradlew.bat --no-daemon :adapter-inbound:bootRun --info

echo.
echo Application stopped.
pause
