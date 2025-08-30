# Test script to reproduce the ExistsByEmailQuery handler error
# Tests the /api/users/create-test-users endpoint

$baseUrl = "http://localhost:8080/api/users/create-test-users"

Write-Host "Testing create-test-users endpoint..." -ForegroundColor Green

try {
    $response = Invoke-RestMethod -Uri $baseUrl -Method Post -ContentType "application/json"
    Write-Host "✓ Successfully created test users" -ForegroundColor Green
    Write-Host $response
} catch {
    Write-Host "✗ Failed to create test users: $($_.Exception.Message)" -ForegroundColor Red
    if ($_.Exception.Response) {
        $reader = New-Object System.IO.StreamReader($_.Exception.Response.GetResponseStream())
        $responseBody = $reader.ReadToEnd()
        Write-Host "Response body: $responseBody" -ForegroundColor Yellow
    }
}