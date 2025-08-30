#!/usr/bin/env python3
"""
設定 PostgreSQL 環境變數
此腳本會設定目前會話的環境變數
"""

import os
import sys
import argparse

def main():
    parser = argparse.ArgumentParser(
        description='設定 PostgreSQL 環境變數 / Set PostgreSQL environment variables'
    )
    parser.add_argument(
        'environment', 
        nargs='?', 
        default='dev',
        choices=['dev', 'prod', 'default'],
        help='環境設定 (dev|prod|default)'
    )
    
    args = parser.parse_args()
    environment = args.environment
    
    print("Setting PostgreSQL environment variables...")
    print("正在設定 PostgreSQL 環境變數...")
    print()
    
    # 預設資料庫連接設定
    os.environ['DB_HOST'] = 'localhost'
    os.environ['DB_PORT'] = '5432'
    os.environ['DB_USERNAME'] = 'postgres'
    os.environ['DB_PASSWORD'] = 'test'
    
    # 根據環境設定不同的資料庫名稱
    if environment == 'dev':
        os.environ['DB_NAME'] = 'springboot_template_db_dev'
        print(f"Setting development database: {os.environ['DB_NAME']}")
        print(f"設定開發環境資料庫: {os.environ['DB_NAME']}")
    elif environment == 'prod':
        os.environ['DB_NAME'] = 'springboot_template_db_prod'
        print(f"Setting production database: {os.environ['DB_NAME']}")
        print(f"設定生產環境資料庫: {os.environ['DB_NAME']}")
    else:
        os.environ['DB_NAME'] = 'springboot_template_db'
        print(f"Setting default database: {os.environ['DB_NAME']}")
        print(f"設定預設資料庫: {os.environ['DB_NAME']}")
    
    print()
    print("Current environment variables:")
    print("目前環境變數設定:")
    print(f"DB_HOST={os.environ['DB_HOST']}")
    print(f"DB_PORT={os.environ['DB_PORT']}")
    print(f"DB_NAME={os.environ['DB_NAME']}")
    print(f"DB_USERNAME={os.environ['DB_USERNAME']}")
    print("DB_PASSWORD=[Set/已設定]")
    print()
    print("Note: These environment variables are only valid in the current session.")
    print("注意: 這些環境變數只在目前的會話中有效。")
    print("For permanent settings, use system environment variables.")
    print("如需永久設定，請使用系統環境變數設定。")
    print()
    print("Usage: python set-db-env.py [dev|prod|default]")
    print("使用方式: python set-db-env.py [dev|prod|default]")
    print("Example: python set-db-env.py dev")
    print("範例: python set-db-env.py dev")

if __name__ == '__main__':
    main()