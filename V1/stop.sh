#!/bin/bash

# Smart Healthcare Management System - Stop Script
# This script stops the running Spring Boot application

echo "🛑 Stopping Smart Healthcare Management System..."

# Find the process ID of the running application
PID=$(pgrep -f "shms" | head -1)

if [ -z "$PID" ]; then
    echo "❌ No running application found"
    exit 1
fi

echo "🔍 Found application running with PID: $PID"

# Try graceful shutdown first
echo "📤 Sending SIGTERM for graceful shutdown..."
kill -TERM $PID

# Wait for the process to stop
echo "⏳ Waiting for graceful shutdown..."
for i in {1..10}; do
    if ! ps -p $PID > /dev/null; then
        echo "✅ Application stopped gracefully"
        exit 0
    fi
    sleep 1
done

# If still running, force kill
if ps -p $PID > /dev/null; then
    echo "⚠️  Graceful shutdown failed, forcing termination..."
    kill -KILL $PID

    # Wait a moment for forced termination
    sleep 2

    if ps -p $PID > /dev/null; then
        echo "❌ Failed to stop application"
        exit 1
    else
        echo "✅ Application stopped forcefully"
    fi
fi

echo "🎯 Application stopped successfully!"
