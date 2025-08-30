#!/usr/bin/env python3
"""
Starting Spring Boot Application
"""

import os
import subprocess
import sys
import time
from pathlib import Path

def main():
    print("Starting Spring Boot Application...")
    print()
    
    # 設定 JAVA_OPTS 以避免記憶體問題
    java_opts = "-Xmx1024m -XX:+UseG1GC"
    env = os.environ.copy()
    env['JAVA_OPTS'] = java_opts
    
    # 使用 Gradle 啟動，但設定延遲
    time.sleep(2)
    
    try:
        # 檢查是否在 Windows 上，決定使用 gradlew.bat 或 gradlew
        if os.name == 'nt':  # Windows
            gradle_cmd = ['gradlew.bat']
        else:  # Unix-like systems
            gradle_cmd = ['./gradlew']
        
        # 建構完整命令
        cmd = gradle_cmd + ['--no-daemon', ':adapter-inbound:bootRun', '--info']
        
        # 執行命令
        result = subprocess.run(cmd, env=env, cwd=Path.cwd())
        
        print()
        print("Application stopped.")
        
        # 等待用戶按鍵
        input("Press Enter to continue...")
        
        return result.returncode
        
    except KeyboardInterrupt:
        print()
        print("Application interrupted by user.")
        return 0
    except FileNotFoundError:
        print("Error: Gradle wrapper not found. Make sure gradlew/gradlew.bat exists in the project root.")
        input("Press Enter to continue...")
        return 1
    except Exception as e:
        print(f"Error starting application: {e}")
        input("Press Enter to continue...")
        return 1

if __name__ == '__main__':
    sys.exit(main())