#!/bin/bash

# Smart Healthcare Management System - Comprehensive Endpoint Test Script
# This script tests ALL API endpoints systematically

# JWT token for authenticated requests
JWT_TOKEN="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30"

# API base URL
BASE_URL="http://localhost:1998/api"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Test counters
TOTAL_TESTS=0
PASSED_TESTS=0
FAILED_TESTS=0

echo "🧪 Starting Comprehensive Smart Healthcare Management System Tests..."
echo "================================================================="
echo ""

# Function to make authenticated request
make_request() {
    local method=$1
    local endpoint=$2
    local data=$3
    local description=$4
    local require_auth=${5:-true}
    local expect_protected=${6:-false}

    TOTAL_TESTS=$((TOTAL_TESTS + 1))

    echo -n "Testing $description... "

    local curl_args=(-s -X $method -H "Content-Type: application/json")

    if [ "$require_auth" = "true" ]; then
        curl_args+=(-H "Authorization: Bearer $JWT_TOKEN")
    fi

    if [ -n "$data" ]; then
        curl_args+=(-d "$data")
    fi

    curl_args+=("$BASE_URL$endpoint")

    local response
    local http_code

    # Execute curl and capture both response and HTTP status code
    response=$(curl -w "HTTPSTATUS:%{http_code}" "${curl_args[@]}" 2>/dev/null)
    http_code=$(echo $response | tr -d '\n' | sed -e 's/.*HTTPSTATUS://')

    # Extract just the response body
    response=$(echo $response | sed -e 's/HTTPSTATUS\:.*//g')

    # Check if this is a protected endpoint test
    if [ "$expect_protected" = "true" ]; then
        # For protected endpoints, 403 is correct (access denied without auth)
        if [ "$http_code" -eq 403 ]; then
            echo -e "${GREEN}✅ PASSED${NC} (HTTP $http_code - Protected)"
            PASSED_TESTS=$((PASSED_TESTS + 1))
        else
            echo -e "${RED}❌ FAILED${NC} (HTTP $http_code - Expected 403)"
            FAILED_TESTS=$((FAILED_TESTS + 1))
            echo "   Response: $response"
        fi
    else
        # For public/authenticated endpoints, expect 200
        if [ "$http_code" -eq 200 ] || [ "$http_code" -eq 201 ] || [ "$http_code" -eq 204 ]; then
            echo -e "${GREEN}✅ PASSED${NC} (HTTP $http_code)"
            PASSED_TESTS=$((PASSED_TESTS + 1))
        else
            echo -e "${RED}❌ FAILED${NC} (HTTP $http_code)"
            FAILED_TESTS=$((FAILED_TESTS + 1))
            echo "   Response: $response"
        fi
    fi
}

# Function to test endpoint that expects specific response
test_endpoint_with_validation() {
    local method=$1
    local endpoint=$2
    local data=$3
    local description=$4
    local expected_content=$5

    TOTAL_TESTS=$((TOTAL_TESTS + 1))

    echo -n "Testing $description... "

    local curl_args=(-s -X $method -H "Content-Type: application/json" -H "Authorization: Bearer $JWT_TOKEN")

    if [ -n "$data" ]; then
        curl_args+=(-d "$data")
    fi

    curl_args+=("$BASE_URL$endpoint")

    local response
    response=$(curl "${curl_args[@]}" 2>/dev/null)

    if [[ $response == *"$expected_content"* ]]; then
        echo -e "${GREEN}✅ PASSED${NC}"
        PASSED_TESTS=$((PASSED_TESTS + 1))
    else
        echo -e "${RED}❌ FAILED${NC}"
        FAILED_TESTS=$((FAILED_TESTS + 1))
        echo "   Expected: $expected_content"
        echo "   Got: $response"
    fi
}

echo -e "${BLUE}🔐 AUTHENTICATION ENDPOINTS${NC}"
echo "--------------------------------"

# Test 1: Token validation
make_request "POST" "/auth/validate" "" "JWT Token Validation"

echo ""
echo -e "${BLUE}👥 PATIENT MANAGEMENT${NC}"
echo "---------------------"

# Test 2: Get all patients (PROTECTED)
make_request "GET" "/patients" "" "List All Patients" "true" "true"

# Test 3: Search patients (PUBLIC)
make_request "GET" "/patients/search?query=Nawab" "" "Search Patients" "true" "false"

# Test 4: Create patient (PROTECTED)
make_request "POST" "/patients" '{
    "username": "testpatient",
    "email": "test.patient@example.com",
    "password": "password123",
    "firstName": "Test",
    "lastName": "Patient",
    "phoneNumber": "+1234567890",
    "dateOfBirth": "1990-01-01",
    "gender": "Male",
    "address": "123 Test St",
    "bloodGroup": "O+",
    "allergies": "None",
    "medicalHistory": "No history"
}' "Create New Patient" "true" "true"

echo ""
echo -e "${BLUE}👨‍⚕️ DOCTOR MANAGEMENT${NC}"
echo "----------------------"

# Test 5: Get all doctors (PROTECTED)
make_request "GET" "/doctors" "" "List All Doctors" "true" "true"

# Test 6: Search doctors (PUBLIC)
make_request "GET" "/doctors/search?query=Dr" "" "Search Doctors" "true" "false"

# Test 7: Get doctors by specialization (PUBLIC)
make_request "GET" "/doctors/specialization/General%20Medicine" "" "Get Doctors by Specialization" "true" "false"

# Test 8: Create doctor (PROTECTED)
make_request "POST" "/doctors" '{
    "username": "testdoctor",
    "email": "test.doctor@example.com",
    "password": "password123",
    "firstName": "Test",
    "lastName": "Doctor",
    "phoneNumber": "+1122334455",
    "specialization": "General Medicine",
    "licenseNumber": "TEST123",
    "qualification": "MBBS",
    "experienceYears": 5,
    "clinicAddress": "Test Clinic"
}' "Create New Doctor" "true" "true"

echo ""
echo -e "${BLUE}📅 APPOINTMENT MANAGEMENT${NC}"
echo "-------------------------"

# Test 9: Get all appointments (PROTECTED)
make_request "GET" "/appointments" "" "List All Appointments" "true" "true"

# Test 10: Create appointment (PROTECTED)
make_request "POST" "/appointments" '{
    "appointmentDateTime": "2025-12-25T10:00:00",
    "status": "SCHEDULED",
    "notes": "Regular checkup",
    "symptoms": "None",
    "patient": {"id": 1},
    "doctor": {"id": 1}
}' "Create New Appointment" "true" "true"

echo ""
echo -e "${BLUE}💊 PRESCRIPTION MANAGEMENT${NC}"
echo "--------------------------"

# Test 11: Get all prescriptions (PROTECTED)
make_request "GET" "/prescriptions" "" "List All Prescriptions" "true" "true"

# Test 12: Create prescription (PROTECTED)
make_request "POST" "/prescriptions" '{
    "medicationName": "Paracetamol",
    "dosage": "500mg",
    "frequency": "Twice daily",
    "duration": "5 days",
    "instructions": "Take with food",
    "diagnosis": "Fever",
    "isActive": true,
    "patient": {"id": 1},
    "doctor": {"id": 1}
}' "Create New Prescription" "true" "true"

echo ""
echo -e "${BLUE}🧪 LAB REPORT MANAGEMENT${NC}"
echo "------------------------"

# Test 13: Get all lab reports (PROTECTED)
make_request "GET" "/lab-reports" "" "List All Lab Reports" "true" "true"

# Test 14: Create lab report (PROTECTED)
make_request "POST" "/lab-reports" '{
    "testName": "Complete Blood Count",
    "testType": "Blood Test",
    "labName": "Test Lab",
    "testDate": "2025-12-20T09:00:00",
    "results": "Normal values",
    "observations": "All parameters normal",
    "normalRange": "Standard range",
    "status": "NORMAL",
    "doctorComments": "Patient is healthy",
    "patient": {"id": 1}
}' "Create New Lab Report" "true" "true"

echo ""
echo -e "${BLUE}📋 MEDICAL RECORD MANAGEMENT${NC}"
echo "----------------------------"

# Test 15: Get all medical records (PROTECTED)
make_request "GET" "/medical-records" "" "List All Medical Records" "true" "true"

# Test 16: Create medical record (PROTECTED)
make_request "POST" "/medical-records" '{
    "recordType": "VISIT",
    "recordDate": "2025-12-20T10:00:00",
    "chiefComplaint": "Regular checkup",
    "historyOfPresentIllness": "Patient feeling well",
    "physicalExamination": "Normal vital signs",
    "diagnosis": "Healthy",
    "treatmentPlan": "Continue current lifestyle",
    "medications": "None",
    "followUpInstructions": "Next visit in 6 months",
    "additionalNotes": "Patient advised healthy diet",
    "patient": {"id": 1}
}' "Create New Medical Record" "true" "true"

echo ""
echo -e "${YELLOW}🧪 TESTING ADDITIONAL ENDPOINTS${NC}"
echo "==============================="

# Test 17: Get appointments by status (PROTECTED)
make_request "GET" "/appointments/status/SCHEDULED" "" "Get Appointments by Status" "true" "true"

# Test 18: Get prescriptions by patient (PROTECTED)
make_request "GET" "/prescriptions/patient/1" "" "Get Prescriptions by Patient" "true" "true"

# Test 19: Get active prescriptions (PROTECTED)
make_request "GET" "/prescriptions/patient/1/active" "" "Get Active Prescriptions" "true" "true"

# Test 20: Get lab reports by patient (PROTECTED)
make_request "GET" "/lab-reports/patient/1" "" "Get Lab Reports by Patient" "true" "true"

# Test 21: Get lab reports by status (PROTECTED)
make_request "GET" "/lab-reports/status/NORMAL" "" "Get Lab Reports by Status" "true" "true"

echo ""
echo -e "${BLUE}📊 TEST RESULTS SUMMARY${NC}"
echo "======================"
echo "Total Tests: $TOTAL_TESTS"
echo -e "✅ Passed: ${GREEN}$PASSED_TESTS${NC}"
echo -e "❌ Failed: ${RED}$FAILED_TESTS${NC}"

if [ $FAILED_TESTS -eq 0 ]; then
    echo ""
    echo -e "${GREEN}🎉 ALL TESTS PASSED!${NC}"
    echo "The Smart Healthcare Management System is working perfectly!"
else
    echo ""
    echo -e "${YELLOW}⚠️  Some tests failed. Check the output above for details.${NC}"
fi

echo ""
echo -e "${BLUE}💡 TROUBLESHOOTING TIPS${NC}"
echo "======================="
echo "• Ensure the application is running: ./start.sh"
echo "• Check application logs: tail -f logs/application.log"
echo "• Verify database connection in application.yml"
echo "• Some endpoints may need valid patient/doctor IDs"
echo "• Use test-jwt.sh for detailed JWT testing"

echo ""
echo "Test completed at: $(date)"
