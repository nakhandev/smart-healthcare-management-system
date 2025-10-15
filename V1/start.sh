#!/bin/bash

# Smart Healthcare Management System - Start Script
# This script starts the Spring Boot application

echo "🚀 Starting Smart Healthcare Management System..."

# Check if the application is already running
if pgrep -f "shms" > /dev/null; then
    echo "❌ Application is already running!"
    echo "Use stop.sh to stop the application first, or check running processes."
    exit 1
fi

# Check if Maven is available
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven is not installed or not in PATH"
    exit 1
fi

# Check if Java is available
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed or not in PATH"
    exit 1
fi

# Navigate to the project directory
cd "$(dirname "$0")"

# Create logs directory if it doesn't exist
mkdir -p logs

# Start the application in background
echo "📦 Building and starting application..."
mvn spring-boot:run > logs/application.log 2>&1 &

# Get the process ID
PID=$!

# Wait a moment for the application to start
sleep 3

# Check if the process is still running
if ps -p $PID > /dev/null; then
    echo "✅ Application started successfully!"
    echo "🔗 Application PID: $PID"
    echo "🌐 Application URL: http://localhost:1998"
    echo "📋 Check logs: tail -f logs/application.log"
    echo ""
    echo "💡 To stop the application, run: ./stop.sh"
else
    echo "❌ Failed to start application"
    echo "🔍 Check logs for details: cat logs/application.log"
    exit 1
fi
