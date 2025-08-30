# 多階段構建 Dockerfile
FROM openjdk:17-jdk-alpine as builder

WORKDIR /app

# 複製 Gradle Wrapper 和構建檔案
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# 複製所有模組的構建檔案
COPY */build.gradle ./*/
COPY */src ./*/src/

# 賦予執行權限並構建應用程式
RUN chmod +x gradlew && \
    ./gradlew bootJar -x test --no-daemon

# 執行階段
FROM openjdk:17-jre-alpine

# 安裝 curl 用於健康檢查
RUN apk add --no-cache curl

# 創建非 root 使用者
RUN addgroup -g 1000 appgroup && \
    adduser -u 1000 -G appgroup -h /home/appuser -D appuser

# 設定工作目錄
WORKDIR /app

# 從構建階段複製 JAR 檔案
COPY --from=builder /app/bootstrap/build/libs/*.jar app.jar

# 更改擁有者
RUN chown appuser:appgroup app.jar

# 切換到非 root 使用者
USER appuser

# 暴露端口
EXPOSE 8080

# 健康檢查
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# 環境變數
ENV JAVA_OPTS="-Xms256m -Xmx512m"
ENV SPRING_PROFILES_ACTIVE=prod

# 啟動命令
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]