#!/bin/bash

# Smart Healthcare Management System - Test Script
# This script runs the test suite

echo "🧪 Running Smart Healthcare Management System Tests..."

# Check if Maven is available
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven is not installed or not in PATH"
    exit 1
fi

# Navigate to the project directory
cd "$(dirname "$0")"

# Create test results directory if it doesn't exist
mkdir -p test-results

echo "📦 Compiling and running tests..."

# Run Maven tests with detailed output
mvn clean test > test-results/test-results.log 2>&1

# Capture the exit code
TEST_EXIT_CODE=$?

# Check if tests passed
if [ $TEST_EXIT_CODE -eq 0 ]; then
    echo "✅ All tests passed successfully!"
    echo ""
    echo "📊 Test Summary:"
    echo "   - Tests: PASSED"
    echo "   - Exit Code: $TEST_EXIT_CODE"
    echo ""
    echo "📋 Detailed results: cat test-results/test-results.log"
else
    echo "❌ Some tests failed!"
    echo ""
    echo "📊 Test Summary:"
    echo "   - Tests: FAILED"
    echo "   - Exit Code: $TEST_EXIT_CODE"
    echo ""
    echo "🔍 Check details: cat test-results/test-results.log"
    echo ""
    echo "💡 Common issues:"
    echo "   - Database connection problems"
    echo "   - Missing dependencies"
    echo "   - Test configuration issues"
fi

# Show a snippet of the test results
echo ""
echo "📄 Last few lines of test output:"
tail -10 test-results/test-results.log

exit $TEST_EXIT_CODE
