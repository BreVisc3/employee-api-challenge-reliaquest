@echo off
echo ========================================
echo Employee API Endpoint Tester
echo ========================================
echo.

echo 1. Testing GET all employees...
curl -s -o response1.json -w "Status: %%{http_code}\n" http://localhost:8080/api/v1/employee
if exist response1.json (
    echo Success! Response saved to response1.json
    type response1.json
) else (
    echo ERROR: No response received
)
echo.

echo 2. Testing GET employee by UUID...
curl -s -o response2.json -w "Status: %%{http_code}\n" http://localhost:8080/api/v1/employee/550e8400-e29b-41d4-a716-446655440000
if exist response2.json (
    echo Success! Response saved to response2.json
    type response2.json
) else (
    echo ERROR: No response received
)
echo.

echo 3. Testing POST create employee...
curl -s -o response3.json -X POST http://localhost:8080/api/v1/employee ^
  -H "Content-Type: application/json" ^
  -d "{\"firstName\":\"BatchTest\",\"email\":\"batch@test.com\"}" ^
  -w "Status: %%{http_code}\n"
if exist response3.json (
    echo Success! Response saved to response3.json
    type response3.json
) else (
    echo ERROR: No response received
)
echo.

echo 4. Testing error case - invalid UUID...
curl -s -o response4.json -w "Status: %%{http_code}\n" http://localhost:8080/api/v1/employee/invalid-uuid
if exist response4.json (
    echo Response saved to response4.json
    type response4.json
) else (
    echo No response (expected for error)
)
echo.

echo ========================================
echo Test Summary
echo ========================================
echo Check response1.json, response2.json, response3.json for results
echo.
pause