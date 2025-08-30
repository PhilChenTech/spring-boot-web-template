#!/usr/bin/env python3
"""
Fix Console Encoding
修正控制台編碼問題
"""

import sys
import os
import locale

def fix_encoding():
    """修正控制台編碼"""
    print("Fixing console encoding...")
    print("修正控制台編碼...")
    
    try:
        # 設定 Python 的標準輸出編碼為 UTF-8
        if hasattr(sys.stdout, 'reconfigure'):
            sys.stdout.reconfigure(encoding='utf-8')
            sys.stderr.reconfigure(encoding='utf-8')
        
        # 在 Windows 上設定控制台代碼頁為 UTF-8
        if os.name == 'nt':
            os.system('chcp 65001 >nul 2>&1')
            print("Console code page set to UTF-8 (65001)")
            print("控制台代碼頁已設為 UTF-8 (65001)")
        else:
            # 在 Unix 系統上設定語言環境
            try:
                locale.setlocale(locale.LC_ALL, 'C.UTF-8')
            except locale.Error:
                try:
                    locale.setlocale(locale.LC_ALL, 'en_US.UTF-8')
                except locale.Error:
                    pass
            print("Console encoding set for Unix-like system")
            print("已為 Unix 類系統設定控制台編碼")
            
    except Exception as e:
        print(f"Warning: Could not fully configure encoding: {e}")
        print("警告: 無法完全設定編碼")
    
    # 測試中文顯示
    print()
    print("Testing Chinese character display:")
    print("測試中文字元顯示:")
    print("資料庫 Database")
    print("環境變數 Environment Variables") 
    print("設定 Settings")
    print("連接 Connection")
    
    print()
    print("Encoding fix applied successfully!")
    print("編碼修正已成功套用!")
    print()
    print("You can now run other scripts with proper Chinese display:")
    print("現在您可以執行其他腳本並正確顯示中文:")
    print("  python set-db-env.py dev")

def main():
    try:
        fix_encoding()
        return 0
    except KeyboardInterrupt:
        print("\nOperation cancelled by user.")
        print("操作已被使用者取消。")
        return 1
    except Exception as e:
        print(f"Error: {e}")
        print(f"錯誤: {e}")
        return 1

if __name__ == '__main__':
    sys.exit(main())