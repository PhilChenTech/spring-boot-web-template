#!/usr/bin/env python3
"""
Set-DbEnv-Simple.py
簡化版的環境變數設定腳本，專注於功能而非複雜的編碼處理
"""

import os
import sys
import argparse

def main():
    parser = argparse.ArgumentParser(
        description='簡化版環境變數設定腳本 / Simplified environment variables setup script'
    )
    parser.add_argument(
        'environment',
        nargs='?', 
        default='dev',
        help='環境設定 (dev|prod|test|default)'
    )
    
    args = parser.parse_args()
    environment = args.environment.lower()
    
    print(f"Setting PostgreSQL environment variables for: {environment}")
    
    # 預設資料庫連接設定
    os.environ['DB_HOST'] = 'localhost'
    os.environ['DB_PORT'] = '5432'
    os.environ['DB_USERNAME'] = 'postgres'
    os.environ['DB_PASSWORD'] = 'test'
    
    # 根據環境設定不同的資料庫名稱
    if environment == 'dev':
        os.environ['DB_NAME'] = 'springboot_template_db_dev'
        print(f"Development database: {os.environ['DB_NAME']}")
    elif environment == 'prod':
        os.environ['DB_NAME'] = 'springboot_template_db_prod'
        print(f"Production database: {os.environ['DB_NAME']}")
    else:
        os.environ['DB_NAME'] = 'springboot_template_db'
        print(f"Default database: {os.environ['DB_NAME']}")
    
    print()
    print("Environment variables set:")
    print(f"DB_HOST={os.environ['DB_HOST']}")
    print(f"DB_PORT={os.environ['DB_PORT']}")
    print(f"DB_NAME={os.environ['DB_NAME']}")
    print(f"DB_USERNAME={os.environ['DB_USERNAME']}")
    print("DB_PASSWORD=[Set]")
    print()
    print("Usage: python set-db-env-simple.py [dev|prod|test|default]")

if __name__ == '__main__':
    main()