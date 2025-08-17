@echo off
REM Agribot Setup Script for Windows
REM This script sets up both frontend and backend for development

echo 🌾 Welcome to Agribot - Farm Talk Ghana Setup
echo ==============================================

REM Check if Node.js is installed
node --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Node.js is not installed. Please install Node.js 18 or higher.
    pause
    exit /b 1
)

echo ✅ Node.js version: 
node --version

REM Install frontend dependencies
echo.
echo 📦 Installing frontend dependencies...
call npm install

if %errorlevel% neq 0 (
    echo ❌ Failed to install frontend dependencies
    pause
    exit /b 1
)

echo ✅ Frontend dependencies installed

REM Create .env file if it doesn't exist
if not exist .env (
    echo.
    echo 🔧 Creating environment file...
    echo VITE_API_BASE_URL=http://localhost:3001/api > .env
    echo ✅ Environment file created
) else (
    echo ✅ Environment file already exists
)

REM Setup backend
echo.
echo 🔧 Setting up backend...

REM Check if backend directory exists
if not exist backend (
    echo ❌ Backend directory not found. Please ensure you have the complete project.
    pause
    exit /b 1
)

REM Install backend dependencies
echo 📦 Installing backend dependencies...
cd backend
call npm install

if %errorlevel% neq 0 (
    echo ❌ Failed to install backend dependencies
    pause
    exit /b 1
)

echo ✅ Backend dependencies installed

REM Create backend .env if it doesn't exist
if not exist .env (
    echo 🔧 Creating backend environment file...
    echo PORT=3001 > .env
    echo JWT_SECRET=agribot-dev-secret-key-change-in-production >> .env
    echo ✅ Backend environment file created
) else (
    echo ✅ Backend environment file already exists
)

cd ..

echo.
echo 🎉 Setup completed successfully!
echo.
echo 🚀 To start the application:
echo.
echo 1. Start the backend server:
echo    cd backend ^&^& npm run dev
echo.
echo 2. In a new terminal, start the frontend:
echo    npm run dev
echo.
echo 3. Open your browser to: http://localhost:5173
echo.
echo 🔑 Demo Accounts:
echo    Farmer: farmer@agribot.com / password
echo    Customer: customer@agribot.com / password
echo    Expert: expert@agribot.com / password
echo.
echo 📚 For more information, see the README.md file
echo.
echo 🌾 Happy farming with Agribot!
pause 