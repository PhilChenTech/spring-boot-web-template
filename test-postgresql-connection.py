#!/usr/bin/env python3
"""
PostgreSQL 連接測試腳本
PostgreSQL connection test script
"""

import subprocess
import sys

def test_database_connection(db_name, display_name):
    """測試特定資料庫的連接"""
    print(f"測試{display_name}連接...")
    
    try:
        result = subprocess.run([
            'psql', '-h', 'localhost', '-U', 'postgres', '-d', db_name, '-c', 'SELECT version();'
        ], capture_output=True, text=True, timeout=10)
        
        if result.returncode == 0:
            print(f"[✓] {display_name}連接成功")
            return True
        else:
            print(f"[✗] {display_name}連接失敗")
            if result.stderr:
                print(f"    錯誤訊息: {result.stderr.strip()}")
            return False
            
    except subprocess.TimeoutExpired:
        print(f"[✗] {display_name}連接超時")
        return False
    except FileNotFoundError:
        print("錯誤：找不到 psql 命令。請確認 PostgreSQL 已正確安裝。")
        return False
    except Exception as e:
        print(f"[✗] {display_name}連接發生錯誤: {e}")
        return False

def list_databases():
    """列出所有資料庫"""
    print("列出所有資料庫...")
    
    try:
        result = subprocess.run([
            'psql', '-h', 'localhost', '-U', 'postgres', '-c', '\\l'
        ], timeout=10)
        
        return result.returncode == 0
        
    except subprocess.TimeoutExpired:
        print("列出資料庫操作超時")
        return False
    except FileNotFoundError:
        print("錯誤：找不到 psql 命令。")
        return False
    except Exception as e:
        print(f"列出資料庫時發生錯誤: {e}")
        return False

def main():
    print("正在測試 PostgreSQL 連接...")
    print()
    
    # 測試各個資料庫連接
    databases = [
        ('springboot_template_db', '預設資料庫'),
        ('springboot_template_db_dev', '開發環境資料庫'),
        ('springboot_template_db_prod', '生產環境資料庫')
    ]
    
    success_count = 0
    total_count = len(databases)
    
    for db_name, display_name in databases:
        if test_database_connection(db_name, display_name):
            success_count += 1
        print()
    
    # 列出所有資料庫
    list_databases()
    
    print()
    print("測試完成！")
    print(f"成功連接: {success_count}/{total_count} 個資料庫")
    
    if success_count == 0:
        print()
        print("建議檢查項目：")
        print("1. PostgreSQL 服務是否正在執行")
        print("2. 使用者 'postgres' 是否存在且密碼正確")
        print("3. 資料庫是否已建立（可執行 python setup-postgresql.py）")
        print("4. 防火牆設定是否允許連接")
    
    # 等待用戶按鍵
    input("按 Enter 鍵繼續...")
    
    # 回傳狀態碼
    return 0 if success_count > 0 else 1

if __name__ == '__main__':
    sys.exit(main())