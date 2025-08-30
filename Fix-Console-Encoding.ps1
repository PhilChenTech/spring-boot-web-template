# Fix-Console-Encoding.ps1
# 修正 PowerShell 控制台編碼問題

Write-Host "Fixing PowerShell console encoding..." -ForegroundColor Green
Write-Host "修正 PowerShell 控制台編碼..." -ForegroundColor Green

# 設定 PowerShell 的輸出編碼
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
$OutputEncoding = [System.Text.Encoding]::UTF8

# 設定控制台代碼頁為 UTF-8
try {
    chcp 65001 | Out-Null
    Write-Host "Console code page set to UTF-8 (65001)" -ForegroundColor Green
    Write-Host "控制台代碼頁已設為 UTF-8 (65001)" -ForegroundColor Green
} catch {
    Write-Host "Warning: Could not set console code page" -ForegroundColor Yellow
    Write-Host "警告: 無法設定控制台代碼頁" -ForegroundColor Yellow
}

# 測試中文顯示
Write-Host ""
Write-Host "Testing Chinese character display:" -ForegroundColor Cyan
Write-Host "測試中文字元顯示:" -ForegroundColor Cyan
Write-Host "資料庫 Database" -ForegroundColor White
Write-Host "環境變數 Environment Variables" -ForegroundColor White
Write-Host "設定 Settings" -ForegroundColor White
Write-Host "連接 Connection" -ForegroundColor White

Write-Host ""
Write-Host "Encoding fix applied successfully!" -ForegroundColor Green
Write-Host "編碼修正已成功套用!" -ForegroundColor Green
Write-Host ""
Write-Host "You can now run other scripts with proper Chinese display:" -ForegroundColor Cyan
Write-Host "現在您可以執行其他腳本並正確顯示中文:" -ForegroundColor Cyan
Write-Host "  .\Set-DbEnv.ps1 dev" -ForegroundColor Yellow
