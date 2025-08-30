#!/usr/bin/env python3
"""
PostgreSQL 資料庫設定腳本
"""

import os
import subprocess
import sys
import tempfile
from pathlib import Path

def check_psql_available():
    """檢查 PostgreSQL 是否已安裝"""
    try:
        result = subprocess.run(['psql', '--version'], 
                              capture_output=True, text=True, check=True)
        return True
    except (subprocess.CalledProcessError, FileNotFoundError):
        return False

def create_databases():
    """建立資料庫"""
    print("正在設定 PostgreSQL 資料庫...")
    
    # 檢查 PostgreSQL 是否已安裝
    if not check_psql_available():
        print("錯誤：找不到 psql 命令。請確認 PostgreSQL 已正確安裝並添加到 PATH 環境變數中。")
        print("您可以從以下網址下載 PostgreSQL: https://www.postgresql.org/download/windows/")
        input("按 Enter 鍵繼續...")
        return 1
    
    print("PostgreSQL 已找到，正在建立資料庫...")
    
    # 建立資料庫的 SQL 腳本
    sql_commands = [
        "CREATE DATABASE springboot_template_db;",
        "CREATE DATABASE springboot_template_db_dev;", 
        "CREATE DATABASE springboot_template_db_prod;"
    ]
    
    # 建立暫存 SQL 檔案
    with tempfile.NamedTemporaryFile(mode='w', suffix='.sql', delete=False, encoding='utf-8') as temp_file:
        for command in sql_commands:
            temp_file.write(command + '\n')
        temp_sql_file = temp_file.name
    
    try:
        # 執行 SQL 腳本
        print("請輸入 PostgreSQL postgres 使用者的密碼（預設通常是 postgres）:")
        result = subprocess.run([
            'psql', '-U', 'postgres', '-h', 'localhost', '-f', temp_sql_file
        ], check=False)
        
        if result.returncode == 0:
            print()
            print("資料庫設定完成！")
            print("已建立以下資料庫：")
            print("- springboot_template_db")
            print("- springboot_template_db_dev")
            print("- springboot_template_db_prod")
            print()
        else:
            print()
            print("資料庫設定過程中發生錯誤。")
            print("請檢查 PostgreSQL 是否正在執行，以及使用者密碼是否正確。")
            
        return result.returncode
        
    finally:
        # 清理暫存檔案
        try:
            os.unlink(temp_sql_file)
        except OSError:
            pass

def main():
    try:
        exit_code = create_databases()
        print()
        input("按任意鍵繼續...")
        return exit_code
    except KeyboardInterrupt:
        print("\n操作已取消。")
        return 1
    except Exception as e:
        print(f"發生未預期的錯誤: {e}")
        return 1

if __name__ == '__main__':
    sys.exit(main())