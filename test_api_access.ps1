# Test script to reproduce API access issue
Write-Host "Testing API access to verify security restrictions..."

# Start the application in background (if not already running)
Write-Host "Make sure the Spring Boot application is running on port 8080"

# Test 1: Try to access user API endpoint
Write-Host "`nTest 1: Accessing /api/v1/users (should fail due to authentication requirement)"
try {
    $response = Invoke-RestMethod -Uri "http://localhost:8080/api/v1/users" -Method GET -TimeoutSec 10
    Write-Host "SUCCESS: Got response: $response"
} catch {
    Write-Host "FAILED: $($_.Exception.Message)"
    Write-Host "Status Code: $($_.Exception.Response.StatusCode.Value__)"
}

# Test 2: Try to access health endpoint (should work - public)
Write-Host "`nTest 2: Accessing /actuator/health (should work - public endpoint)"
try {
    $response = Invoke-RestMethod -Uri "http://localhost:8080/actuator/health" -Method GET -TimeoutSec 10
    Write-Host "SUCCESS: Got response: $($response | ConvertTo-Json)"
} catch {
    Write-Host "FAILED: $($_.Exception.Message)"
}

# Test 3: Try to create a user via POST (should fail due to authentication requirement)  
Write-Host "`nTest 3: Creating user via POST /api/v1/users (should fail due to authentication requirement)"
$userData = @{
    name = "Test User"
    email = "test@example.com"
} | ConvertTo-Json

try {
    $response = Invoke-RestMethod -Uri "http://localhost:8080/api/v1/users" -Method POST -Body $userData -ContentType "application/json" -TimeoutSec 10
    Write-Host "SUCCESS: Created user: $($response | ConvertTo-Json)"
} catch {
    Write-Host "FAILED: $($_.Exception.Message)"
    Write-Host "Status Code: $($_.Exception.Response.StatusCode.Value__)"
}

Write-Host "`nAPI access test completed."