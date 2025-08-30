@echo off
REM 彈性應用程式啟動腳本

set APP_TYPE=%1
if "%APP_TYPE%"=="" set APP_TYPE=web

echo Starting application in %APP_TYPE% mode...

REM 啟動 Web 應用程式
echo Starting Web Application...
gradlew :bootstrap:bootRun --args="--spring.profiles.active=web --app.type=web"
