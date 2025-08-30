#!/usr/bin/env python3
"""
Spring Boot 應用程式啟動腳本（支援 PostgreSQL 環境變數）
Spring Boot application startup script with PostgreSQL environment variables support
"""

import os
import subprocess
import sys
import argparse
from pathlib import Path

def check_postgresql_connection():
    """檢查 PostgreSQL 連接（簡化版）"""
    try:
        # 這裡使用簡單的 psql 測試連接
        result = subprocess.run([
            'psql', '-h', 'localhost', '-U', 'postgres', '-d', 'postgres', '-c', 'SELECT 1;'
        ], capture_output=True, text=True, timeout=10)
        return result.returncode == 0
    except (subprocess.CalledProcessError, subprocess.TimeoutExpired, FileNotFoundError):
        return False

def main():
    parser = argparse.ArgumentParser(
        description='Spring Boot 應用程式啟動腳本 / Spring Boot application startup script'
    )
    parser.add_argument(
        'app_type',
        nargs='?',
        default='web',
        help='應用程式類型 (預設: web) / Application type (default: web)'
    )
    parser.add_argument(
        'profile',
        nargs='?',
        default='dev',
        help='Spring profile (預設: dev) / Spring profile (default: dev)'
    )
    
    args = parser.parse_args()
    app_type = args.app_type
    profile = args.profile
    
    print("Starting application...")
    print("正在啟動應用程式...")
    print(f"Application type: {app_type}")
    print(f"應用程式類型: {app_type}")
    print(f"Profile: {profile}")
    print(f"設定檔: {profile}")
    print()
    
    # 設定環境變數（如果未設定的話）
    env = os.environ.copy()
    if 'DB_HOST' not in env:
        env['DB_HOST'] = 'localhost'
    if 'DB_PORT' not in env:
        env['DB_PORT'] = '5432'
    if 'DB_USERNAME' not in env:
        env['DB_USERNAME'] = 'postgres'
    if 'DB_PASSWORD' not in env:
        env['DB_PASSWORD'] = 'test'
    
    # 根據 profile 設定資料庫名稱
    if profile == 'dev':
        if 'DB_NAME' not in env:
            env['DB_NAME'] = 'springboot_template_db_dev'
    elif profile == 'prod':
        if 'DB_NAME' not in env:
            env['DB_NAME'] = 'springboot_template_db_prod'
    else:
        if 'DB_NAME' not in env:
            env['DB_NAME'] = 'springboot_template_db'
    
    # 顯示目前資料庫設定
    print("Database settings:")
    print("資料庫設定:")
    print(f"   Host: {env['DB_HOST']}:{env['DB_PORT']}")
    print(f"   主機: {env['DB_HOST']}:{env['DB_PORT']}")
    print(f"   Database: {env['DB_NAME']}")
    print(f"   資料庫: {env['DB_NAME']}")
    print(f"   User: {env['DB_USERNAME']}")
    print(f"   使用者: {env['DB_USERNAME']}")
    print()
    
    # 檢查 PostgreSQL 是否正在執行（僅在非測試環境下）
    if profile != 'test':
        print("Checking PostgreSQL connection...")
        print("檢查 PostgreSQL 連接...")
        if check_postgresql_connection():
            print("PostgreSQL connection OK")
            print("PostgreSQL 連接正常")
        else:
            print("Warning: Cannot connect to PostgreSQL, please ensure service is running")
            print("警告：無法連接到 PostgreSQL，請確認服務已啟動")
        print()
    
    try:
        # 檢查是否在 Windows 上，決定使用 gradlew.bat 或 gradlew
        if os.name == 'nt':  # Windows
            gradle_cmd = ['gradlew.bat']
        else:  # Unix-like systems
            gradle_cmd = ['./gradlew']
        
        # 啟動 Web 應用程式
        print(f"正在啟動 Web 應用程式（{profile} 環境）...")
        
        # 建構完整命令
        spring_args = f"--spring.profiles.active={profile}"
        cmd = gradle_cmd + [':bootstrap:bootRun', f'-Dapp.type=web', f'--args={spring_args}']
        
        # 執行命令
        result = subprocess.run(cmd, env=env, cwd=Path.cwd())
        
        if result.returncode != 0:
            print()
            print("應用程式啟動失敗！")
            print("請檢查：")
            print("1. PostgreSQL 服務是否正在執行")
            print("2. 資料庫是否已建立")
            print("3. 資料庫連接設定是否正確")
            print()
            print("您可以執行 python setup-postgresql.py 來設定資料庫")
            input("按 Enter 鍵繼續...")
        
        return result.returncode
        
    except KeyboardInterrupt:
        print()
        print("Application interrupted by user.")
        return 0
    except FileNotFoundError:
        print("Error: Gradle wrapper not found. Make sure gradlew/gradlew.bat exists in the project root.")
        input("按 Enter 鍵繼續...")
        return 1
    except Exception as e:
        print(f"Error starting application: {e}")
        input("按 Enter 鍵繼續...")
        return 1

if __name__ == '__main__':
    sys.exit(main())