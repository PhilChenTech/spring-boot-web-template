# Test script to verify user API functionality
# Tests all CRUD operations for user management

$baseUrl = "http://localhost:8080/api/v1/users"

Write-Host "Testing User API endpoints..." -ForegroundColor Green

# Test 1: Create a new user
Write-Host "`n1. Testing POST /api/v1/users (Create User)" -ForegroundColor Yellow
$createUserBody = @{
    name = "Test User"
    email = "testuser@example.com"
} | ConvertTo-Json

try {
    $response = Invoke-RestMethod -Uri $baseUrl -Method Post -Body $createUserBody -ContentType "application/json"
    Write-Host "✓ User created successfully" -ForegroundColor Green
    $userId = $response.data.id
    Write-Host "  User ID: $userId" -ForegroundColor Cyan
} catch {
    Write-Host "✗ Failed to create user: $($_.Exception.Message)" -ForegroundColor Red
    exit 1
}

# Test 2: Get all users
Write-Host "`n2. Testing GET /api/v1/users (Get All Users)" -ForegroundColor Yellow
try {
    $response = Invoke-RestMethod -Uri $baseUrl -Method Get
    Write-Host "✓ Successfully retrieved all users" -ForegroundColor Green
    Write-Host "  Total users: $($response.data.Count)" -ForegroundColor Cyan
} catch {
    Write-Host "✗ Failed to get all users: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 3: Get user by ID
Write-Host "`n3. Testing GET /api/v1/users/{id} (Get User by ID)" -ForegroundColor Yellow
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/$userId" -Method Get
    Write-Host "✓ Successfully retrieved user by ID" -ForegroundColor Green
    Write-Host "  User: $($response.data.name) ($($response.data.email))" -ForegroundColor Cyan
} catch {
    Write-Host "✗ Failed to get user by ID: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 4: Update user
Write-Host "`n4. Testing PUT /api/v1/users/{id} (Update User)" -ForegroundColor Yellow
$updateUserBody = @{
    name = "Updated Test User"
    email = "updated.testuser@example.com"
} | ConvertTo-Json

try {
    $response = Invoke-RestMethod -Uri "$baseUrl/$userId" -Method Put -Body $updateUserBody -ContentType "application/json"
    Write-Host "✓ User updated successfully" -ForegroundColor Green
    Write-Host "  Updated User: $($response.data.name) ($($response.data.email))" -ForegroundColor Cyan
} catch {
    Write-Host "✗ Failed to update user: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 5: Delete user
Write-Host "`n5. Testing DELETE /api/v1/users/{id} (Delete User)" -ForegroundColor Yellow
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/$userId" -Method Delete
    Write-Host "✓ User deleted successfully" -ForegroundColor Green
} catch {
    Write-Host "✗ Failed to delete user: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 6: Verify user is deleted
Write-Host "`n6. Testing GET /api/v1/users/{id} (Verify Deletion)" -ForegroundColor Yellow
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/$userId" -Method Get
    Write-Host "✗ User still exists after deletion" -ForegroundColor Red
} catch {
    if ($_.Exception.Response.StatusCode -eq 404) {
        Write-Host "✓ User successfully deleted (404 Not Found)" -ForegroundColor Green
    } else {
        Write-Host "✗ Unexpected error: $($_.Exception.Message)" -ForegroundColor Red
    }
}

Write-Host "`nUser API testing completed!" -ForegroundColor Green