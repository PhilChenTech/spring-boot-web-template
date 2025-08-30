#!/usr/bin/env python3
"""
彈性應用程式啟動腳本
Flexible application startup script
"""

import os
import subprocess
import sys
import argparse
from pathlib import Path

def main():
    parser = argparse.ArgumentParser(
        description='彈性應用程式啟動腳本 / Flexible application startup script'
    )
    parser.add_argument(
        'app_type',
        nargs='?',
        default='web',
        help='應用程式類型 (預設: web) / Application type (default: web)'
    )
    
    args = parser.parse_args()
    app_type = args.app_type
    
    print(f"Starting application in {app_type} mode...")
    
    try:
        # 檢查是否在 Windows 上，決定使用 gradlew.bat 或 gradlew
        if os.name == 'nt':  # Windows
            gradle_cmd = ['gradlew.bat']
        else:  # Unix-like systems
            gradle_cmd = ['./gradlew']
        
        # 啟動 Web 應用程式
        print("Starting Web Application...")
        
        # 建構完整命令
        spring_args = f"--spring.profiles.active=web --app.type=web"
        cmd = gradle_cmd + [':bootstrap:bootRun', f'--args={spring_args}']
        
        # 執行命令
        result = subprocess.run(cmd, cwd=Path.cwd())
        
        return result.returncode
        
    except KeyboardInterrupt:
        print()
        print("Application interrupted by user.")
        return 0
    except FileNotFoundError:
        print("Error: Gradle wrapper not found. Make sure gradlew/gradlew.bat exists in the project root.")
        return 1
    except Exception as e:
        print(f"Error starting application: {e}")
        return 1

if __name__ == '__main__':
    sys.exit(main())