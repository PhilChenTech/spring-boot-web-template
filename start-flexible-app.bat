@echo off
REM 彈性應用程式啟動腳本

set APP_TYPE=%1
if "%APP_TYPE%"=="" set APP_TYPE=web

echo Starting application in %APP_TYPE% mode...

REM 根據應用程式類型設定不同的依賴
if "%APP_TYPE%"=="web" (
    echo Starting Web Application...
    gradlew :bootstrap:bootRun --args="--spring.profiles.active=web --app.type=web"
) else if "%APP_TYPE%"=="desktop" (
    echo Starting Desktop Application...
    gradlew :bootstrap:bootRun --args="--spring.profiles.active=desktop --app.type=desktop"
) else if "%APP_TYPE%"=="batch" (
    echo Starting Batch Application...
    gradlew :bootstrap:bootRun --args="--spring.profiles.active=batch --app.type=batch"
) else (
    echo Unknown application type: %APP_TYPE%
    echo Usage: start-app.bat [web^|desktop^|batch]
    exit /b 1
)
