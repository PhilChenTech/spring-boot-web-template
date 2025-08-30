@echo off
REM 彈性應用程式啟動腳本

set APP_TYPE=%1
if "%APP_TYPE%"=="" set APP_TYPE=web

echo Starting application in %APP_TYPE% mode...

REM 根據應用程式類型設定不同的依賴
if "%APP_TYPE%"=="web" (
    echo Starting Web Application...
    gradlew :bootstrap:bootRun --args="--spring.profiles.active=web --app.type=web"
) else if "%APP_TYPE%"=="batch" (
    echo Starting Batch Application...
    gradlew :bootstrap:bootRun --args="--spring.profiles.active=batch --app.type=batch"
) else (
    echo Unknown application type: %APP_TYPE%
    echo Usage: start-app.bat [web^|batch]
    exit /b 1
)
