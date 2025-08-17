#!/bin/bash

# Agribot Deployment Script
# This script sets up and deploys the Agribot agricultural chatbot system

echo "🚀 Starting Agribot Deployment..."

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Check if Node.js is installed
check_nodejs() {
    print_status "Checking Node.js installation..."
    if ! command -v node &> /dev/null; then
        print_error "Node.js is not installed. Please install Node.js 18 or higher."
        exit 1
    fi
    
    NODE_VERSION=$(node -v | cut -d'v' -f2 | cut -d'.' -f1)
    if [ "$NODE_VERSION" -lt 18 ]; then
        print_error "Node.js version 18 or higher is required. Current version: $(node -v)"
        exit 1
    fi
    
    print_success "Node.js $(node -v) is installed"
}

# Check if npm is installed
check_npm() {
    print_status "Checking npm installation..."
    if ! command -v npm &> /dev/null; then
        print_error "npm is not installed."
        exit 1
    fi
    
    print_success "npm $(npm -v) is installed"
}

# Install frontend dependencies
install_frontend() {
    print_status "Installing frontend dependencies..."
    cd /Users/pablo/Downloads/farm-talk-ghana-main
    
    if npm install; then
        print_success "Frontend dependencies installed successfully"
    else
        print_error "Failed to install frontend dependencies"
        exit 1
    fi
}

# Install backend dependencies
install_backend() {
    print_status "Installing backend dependencies..."
    cd /Users/pablo/Downloads/farm-talk-ghana-main/server
    
    if npm install; then
        print_success "Backend dependencies installed successfully"
    else
        print_error "Failed to install backend dependencies"
        exit 1
    fi
}

# Create environment file
setup_environment() {
    print_status "Setting up environment configuration..."
    cd /Users/pablo/Downloads/farm-talk-ghana-main
    
    if [ ! -f .env ]; then
        cat > .env << EOF
# Agribot Environment Configuration

# Server Configuration
NODE_ENV=development
PORT=3001
FRONTEND_URL=http://localhost:8081

# Database Configuration (MongoDB)
MONGODB_URI=mongodb://localhost:27017/agribot

# API Keys (Replace with actual keys in production)
GMET_API_KEY=your_gmet_api_key_here
ESOKO_API_KEY=your_esoko_api_key_here

# External API URLs
GMET_BASE_URL=https://api.gmet.gov.gh
ESOKO_BASE_URL=https://api.esoko.com

# JWT Configuration
JWT_SECRET=agribot_jwt_secret_key_2024
JWT_EXPIRES_IN=7d

# Development Settings
DEBUG=true
ENABLE_MOCK_DATA=true
EOF
        print_success "Environment file created"
    else
        print_warning "Environment file already exists"
    fi
}

# Build frontend
build_frontend() {
    print_status "Building frontend application..."
    cd /Users/pablo/Downloads/farm-talk-ghana-main
    
    if npm run build; then
        print_success "Frontend built successfully"
    else
        print_error "Failed to build frontend"
        exit 1
    fi
}

# Start development servers
start_development() {
    print_status "Starting development servers..."
    
    # Start backend server in background
    cd /Users/pablo/Downloads/farm-talk-ghana-main/server
    print_status "Starting backend server on port 3001..."
    npm run dev &
    BACKEND_PID=$!
    
    # Wait a moment for backend to start
    sleep 3
    
    # Start frontend server
    cd /Users/pablo/Downloads/farm-talk-ghana-main
    print_status "Starting frontend server on port 8081..."
    npm run dev &
    FRONTEND_PID=$!
    
    print_success "Development servers started"
    print_status "Backend PID: $BACKEND_PID"
    print_status "Frontend PID: $FRONTEND_PID"
    
    # Save PIDs for cleanup
    echo $BACKEND_PID > .backend_pid
    echo $FRONTEND_PID > .frontend_pid
    
    print_success "🎉 Agribot is now running!"
    print_status "Frontend: http://localhost:8081"
    print_status "Backend API: http://localhost:3001"
    print_status "Health Check: http://localhost:3001/health"
    
    print_warning "Press Ctrl+C to stop the servers"
    
    # Wait for interrupt
    wait
}

# Cleanup function
cleanup() {
    print_status "Cleaning up..."
    
    if [ -f .backend_pid ]; then
        BACKEND_PID=$(cat .backend_pid)
        kill $BACKEND_PID 2>/dev/null
        rm .backend_pid
    fi
    
    if [ -f .frontend_pid ]; then
        FRONTEND_PID=$(cat .frontend_pid)
        kill $FRONTEND_PID 2>/dev/null
        rm .frontend_pid
    fi
    
    print_success "Cleanup completed"
    exit 0
}

# Trap Ctrl+C and call cleanup
trap cleanup SIGINT

# Main deployment process
main() {
    print_status "Starting Agribot deployment process..."
    
    # Check prerequisites
    check_nodejs
    check_npm
    
    # Install dependencies
    install_frontend
    install_backend
    
    # Setup environment
    setup_environment
    
    # Build frontend (optional for development)
    # build_frontend
    
    # Start development servers
    start_development
}

# Run main function
main "$@" 