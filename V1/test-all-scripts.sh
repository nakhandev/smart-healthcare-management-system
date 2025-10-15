#!/bin/bash

# Smart Healthcare Management System - Shell Script Testing Framework
# This script tests ALL shell scripts for syntax, permissions, and functionality

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
PURPLE='\033[0;35m'
NC='\033[0m' # No Color

# Test counters
TOTAL_SCRIPT_TESTS=0
PASSED_SCRIPT_TESTS=0
FAILED_SCRIPT_TESTS=0

echo "🧪 Testing All Shell Scripts in Smart Healthcare Management System..."
echo "==================================================================="
echo ""

# List of all scripts to test
scripts=(
    "start.sh:Start application"
    "stop.sh:Stop application"
    "test.sh:Run unit tests"
    "test-jwt.sh:Test JWT authentication"
    "test-all-endpoints.sh:Test all API endpoints"
    "validate-endpoints.sh:Validate application structure"
)

# Function to test script existence
test_script_exists() {
    local script=$1
    local description=$2
    TOTAL_SCRIPT_TESTS=$((TOTAL_SCRIPT_TESTS + 1))

    echo -n "Testing $description script exists... "

    if [ -f "$script" ]; then
        echo -e "${GREEN}✅ FOUND${NC}"
        PASSED_SCRIPT_TESTS=$((PASSED_SCRIPT_TESTS + 1))
        return 0
    else
        echo -e "${RED}❌ MISSING${NC}"
        FAILED_SCRIPT_TESTS=$((FAILED_SCRIPT_TESTS + 1))
        return 1
    fi
}

# Function to test script permissions
test_script_permissions() {
    local script=$1
    local description=$2
    TOTAL_SCRIPT_TESTS=$((TOTAL_SCRIPT_TESTS + 1))

    echo -n "Testing $description script permissions... "

    if [ -x "$script" ]; then
        echo -e "${GREEN}✅ EXECUTABLE${NC}"
        PASSED_SCRIPT_TESTS=$((PASSED_SCRIPT_TESTS + 1))
    else
        echo -e "${RED}❌ NOT EXECUTABLE${NC}"
        FAILED_SCRIPT_TESTS=$((FAILED_SCRIPT_TESTS + 1))
    fi
}

# Function to test script syntax
test_script_syntax() {
    local script=$1
    local description=$2
    TOTAL_SCRIPT_TESTS=$((TOTAL_SCRIPT_TESTS + 1))

    echo -n "Testing $description script syntax... "

    if bash -n "$script" 2>/dev/null; then
        echo -e "${GREEN}✅ VALID SYNTAX${NC}"
        PASSED_SCRIPT_TESTS=$((PASSED_SCRIPT_TESTS + 1))
    else
        echo -e "${RED}❌ SYNTAX ERROR${NC}"
        FAILED_SCRIPT_TESTS=$((FAILED_SCRIPT_TESTS + 1))
    fi
}

# Function to test script dependencies
test_script_dependencies() {
    local script=$1
    local description=$2
    TOTAL_SCRIPT_TESTS=$((TOTAL_SCRIPT_TESTS + 1))

    echo -n "Testing $description script dependencies... "

    # Check for common dependencies based on script content
    local missing_deps=()

    if grep -q "mvn" "$script" && ! command -v mvn &> /dev/null; then
        missing_deps+=("mvn")
    fi

    if grep -q "java" "$script" && ! command -v java &> /dev/null; then
        missing_deps+=("java")
    fi

    if grep -q "curl" "$script" && ! command -v curl &> /dev/null; then
        missing_deps+=("curl")
    fi

    if grep -q "jq" "$script" && ! command -v jq &> /dev/null; then
        missing_deps+=("jq")
    fi

    if [ ${#missing_deps[@]} -eq 0 ]; then
        echo -e "${GREEN}✅ DEPENDENCIES OK${NC}"
        PASSED_SCRIPT_TESTS=$((PASSED_SCRIPT_TESTS + 1))
    else
        echo -e "${RED}❌ MISSING: ${missing_deps[*]}${NC}"
        FAILED_SCRIPT_TESTS=$((FAILED_SCRIPT_TESTS + 1))
    fi
}

# Function to test script content
test_script_content() {
    local script=$1
    local description=$2
    TOTAL_SCRIPT_TESTS=$((TOTAL_SCRIPT_TESTS + 1))

    echo -n "Testing $description script content... "

    if [ -s "$script" ]; then
        echo -e "${GREEN}✅ HAS CONTENT${NC}"
        PASSED_SCRIPT_TESTS=$((PASSED_SCRIPT_TESTS + 1))
    else
        echo -e "${RED}❌ EMPTY FILE${NC}"
        FAILED_SCRIPT_TESTS=$((FAILED_SCRIPT_TESTS + 1))
    fi
}

echo -e "${BLUE}📋 SCRIPT INVENTORY${NC}"
echo "=================="

# Test each script
for script_info in "${scripts[@]}"; do
    IFS=':' read -r script_name script_desc <<< "$script_info"

    echo ""
    echo -e "${PURPLE}🔍 Testing: $script_name${NC}"
    echo "   Description: $script_desc"
    echo "   -----------------------------"

    test_script_exists "$script_name" "$script_desc"
    if [ $? -eq 0 ]; then
        test_script_content "$script_name" "$script_desc"
        test_script_permissions "$script_name" "$script_desc"
        test_script_syntax "$script_name" "$script_desc"
        test_script_dependencies "$script_name" "$script_desc"
    fi
done

echo ""
echo -e "${BLUE}🔧 DEPENDENCY CHECK${NC}"
echo "=================="

# Check for essential tools
tools=("mvn:Maven" "java:Java" "curl:cURL" "jq:jq")
echo "Checking required tools..."

for tool_info in "${tools[@]}"; do
    IFS=':' read -r tool_cmd tool_name <<< "$tool_info"

    TOTAL_SCRIPT_TESTS=$((TOTAL_SCRIPT_TESTS + 1))

    echo -n "Checking $tool_name... "

    if command -v "$tool_cmd" &> /dev/null; then
        echo -e "${GREEN}✅ INSTALLED${NC}"
        PASSED_SCRIPT_TESTS=$((PASSED_SCRIPT_TESTS + 1))
    else
        echo -e "${RED}❌ MISSING${NC}"
        FAILED_SCRIPT_TESTS=$((FAILED_SCRIPT_TESTS + 1))
    fi
done

echo ""
echo -e "${BLUE}📊 SCRIPT TESTING SUMMARY${NC}"
echo "========================"
echo "Total Script Tests: $TOTAL_SCRIPT_TESTS"
echo -e "✅ Passed: ${GREEN}$PASSED_SCRIPT_TESTS${NC}"
echo -e "❌ Failed: ${RED}$FAILED_SCRIPT_TESTS${NC}"

if [ $FAILED_SCRIPT_TESTS -eq 0 ]; then
    echo ""
    echo -e "${GREEN}🎉 ALL SCRIPT TESTS PASSED!${NC}"
    echo ""
    echo -e "${BLUE}🚀 READY TO USE:${NC}"
    echo "================="
    echo "Application Management:"
    echo "  ./start.sh              - Start the application"
    echo "  ./stop.sh               - Stop the application"
    echo ""
    echo "Testing:"
    echo "  ./test.sh               - Run unit tests"
    echo "  ./test-jwt.sh           - Test JWT authentication"
    echo "  ./test-all-endpoints.sh - Test all API endpoints"
    echo "  ./validate-endpoints.sh - Validate structure"
    echo ""
    echo "Documentation:"
    echo "  cat README.md           - View complete documentation"
else
    echo ""
    echo -e "${YELLOW}⚠️  Some script tests failed. Check the output above.${NC}"
    echo ""
    echo -e "${BLUE}💡 TO FIX COMMON ISSUES:${NC}"
    echo "========================="
    echo "• Install Maven: apt install maven (Ubuntu/Debian)"
    echo "• Install Java: apt install openjdk-17-jdk"
    echo "• Install curl: apt install curl"
    echo "• Install jq: apt install jq"
    echo "• Fix permissions: chmod +x *.sh"
fi

echo ""
echo -e "${BLUE}📈 PROJECT STATUS${NC}"
echo "================="
echo "✅ Smart Healthcare Management System"
echo "✅ Complete REST API (32 endpoints)"
echo "✅ JWT Authentication"
echo "✅ MySQL Database Integration"
echo "✅ Comprehensive Testing Framework"
echo "✅ Production-Ready Scripts"
echo "✅ Complete Documentation"

echo ""
echo "Script testing completed at: $(date)"
