@echo off
echo Starting Desktop Application...
echo.

REM 設定 JAVA_OPTS 以支援桌面應用程式
set JAVA_OPTS=-Xmx1024m -XX:+UseG1GC -Djava.awt.headless=false

echo Testing Desktop Application Startup...
echo.

echo === Method 1: Using Bootstrap with desktop mode ===
echo Command: .\gradlew :bootstrap:bootRun -Dapp.type=desktop --args="--spring.profiles.active=desktop"
timeout /t 2 /nobreak > nul
.\gradlew --no-daemon :bootstrap:bootRun -Dapp.type=desktop --args="--spring.profiles.active=desktop"

echo.
echo === Alternative Method: Using flexible startup script ===
echo Command: .\start-flexible-app.bat desktop
REM .\start-flexible-app.bat desktop

echo.
echo Desktop application test completed.
pause
