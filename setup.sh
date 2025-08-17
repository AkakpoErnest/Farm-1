#!/bin/bash

# Agribot Setup Script
# This script sets up both frontend and backend for development

echo "🌾 Welcome to Agribot - Farm Talk Ghana Setup"
echo "=============================================="

# Check if Node.js is installed
if ! command -v node &> /dev/null; then
    echo "❌ Node.js is not installed. Please install Node.js 18 or higher."
    exit 1
fi

# Check Node.js version
NODE_VERSION=$(node -v | cut -d'v' -f2 | cut -d'.' -f1)
if [ "$NODE_VERSION" -lt 18 ]; then
    echo "❌ Node.js version 18 or higher is required. Current version: $(node -v)"
    exit 1
fi

echo "✅ Node.js version: $(node -v)"

# Install frontend dependencies
echo ""
echo "📦 Installing frontend dependencies..."
npm install

if [ $? -ne 0 ]; then
    echo "❌ Failed to install frontend dependencies"
    exit 1
fi

echo "✅ Frontend dependencies installed"

# Create .env file if it doesn't exist
if [ ! -f .env ]; then
    echo ""
    echo "🔧 Creating environment file..."
    echo "VITE_API_BASE_URL=http://localhost:3001/api" > .env
    echo "✅ Environment file created"
else
    echo "✅ Environment file already exists"
fi

# Setup backend
echo ""
echo "🔧 Setting up backend..."

# Check if backend directory exists
if [ ! -d "backend" ]; then
    echo "❌ Backend directory not found. Please ensure you have the complete project."
    exit 1
fi

# Install backend dependencies
echo "📦 Installing backend dependencies..."
cd backend
npm install

if [ $? -ne 0 ]; then
    echo "❌ Failed to install backend dependencies"
    exit 1
fi

echo "✅ Backend dependencies installed"

# Create backend .env if it doesn't exist
if [ ! -f .env ]; then
    echo "🔧 Creating backend environment file..."
    echo "PORT=3001" > .env
    echo "JWT_SECRET=agribot-dev-secret-key-change-in-production" >> .env
    echo "✅ Backend environment file created"
else
    echo "✅ Backend environment file already exists"
fi

cd ..

echo ""
echo "🎉 Setup completed successfully!"
echo ""
echo "🚀 To start the application:"
echo ""
echo "1. Start the backend server:"
echo "   cd backend && npm run dev"
echo ""
echo "2. In a new terminal, start the frontend:"
echo "   npm run dev"
echo ""
echo "3. Open your browser to: http://localhost:5173"
echo ""
echo "🔑 Demo Accounts:"
echo "   Farmer: farmer@agribot.com / password"
echo "   Customer: customer@agribot.com / password"
echo "   Expert: expert@agribot.com / password"
echo ""
echo "📚 For more information, see the README.md file"
echo ""
echo "🌾 Happy farming with Agribot!" 