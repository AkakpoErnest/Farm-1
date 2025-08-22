#!/bin/bash

echo "🚀 Building and Running Agribot Android App"
echo "=============================================="

# Navigate to android directory
cd android

echo "📱 Cleaning project..."
./gradlew clean

echo "🔨 Building project..."
./gradlew build

echo "✅ Build completed!"
echo ""
echo "📱 To run the app:"
echo "1. Open Android Studio"
echo "2. Open the 'android' folder as a project"
echo "3. Wait for Gradle sync to complete"
echo "4. Click the Run button (▶️)"
echo ""
echo "🔧 If you still get errors:"
echo "1. File → Invalidate Caches / Restart"
echo "2. File → Sync Project with Gradle Files"
echo "3. Build → Clean Project"
echo "4. Build → Rebuild Project"
