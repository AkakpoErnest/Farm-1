# Agribot Backend API

Backend server for Agribot - Farm Talk Ghana authentication and user management.

## Features

- 🔐 **JWT Authentication** - Secure token-based authentication
- 👥 **User Management** - Register, login, profile management
- 🛡️ **Password Hashing** - Secure password storage with bcrypt
- ✅ **Input Validation** - Request validation with express-validator
- 🔄 **Token Refresh** - Automatic token refresh mechanism
- 🌍 **CORS Support** - Cross-origin resource sharing enabled

## Quick Start

### Prerequisites

- Node.js (version 18 or higher)
- npm or yarn

### Installation

1. **Navigate to backend directory**
   ```bash
   cd backend
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Set environment variables** (optional)
   ```bash
   # Create .env file
   echo "PORT=3001" > .env
   echo "JWT_SECRET=your-super-secret-key-change-this-in-production" >> .env
   ```

4. **Start the server**
   ```bash
   # Development mode (with auto-restart)
   npm run dev
   
   # Production mode
   npm start
   ```

5. **Verify the server is running**
   ```bash
   curl http://localhost:3001/api/health
   ```

## API Endpoints

### Authentication

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/auth/register` | Register new user | No |
| POST | `/api/auth/login` | Login user | No |
| GET | `/api/auth/profile` | Get user profile | Yes |
| PUT | `/api/auth/profile` | Update user profile | Yes |
| POST | `/api/auth/refresh` | Refresh token | Yes |
| POST | `/api/auth/logout` | Logout user | Yes |

### Health Check

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/health` | Server health check |

## Request/Response Examples

### Register User

**Request:**
```bash
POST /api/auth/register
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "role": "farmer",
  "phone": "+233 24 123 4567",
  "location": "Kumasi, Ghana"
}
```

**Response:**
```json
{
  "message": "User registered successfully",
  "user": {
    "id": "1234567890",
    "name": "John Doe",
    "email": "john@example.com",
    "role": "farmer",
    "phone": "+233 24 123 4567",
    "location": "Kumasi, Ghana",
    "avatar": "/avatars/farmer.jpg",
    "createdAt": "2024-01-01T00:00:00.000Z",
    "lastLogin": "2024-01-01T00:00:00.000Z"
  },
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Login User

**Request:**
```bash
POST /api/auth/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "message": "Login successful",
  "user": {
    "id": "1234567890",
    "name": "John Doe",
    "email": "john@example.com",
    "role": "farmer",
    "phone": "+233 24 123 4567",
    "location": "Kumasi, Ghana",
    "avatar": "/avatars/farmer.jpg",
    "createdAt": "2024-01-01T00:00:00.000Z",
    "lastLogin": "2024-01-01T12:00:00.000Z"
  },
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Get Profile (Authenticated)

**Request:**
```bash
GET /api/auth/profile
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**Response:**
```json
{
  "user": {
    "id": "1234567890",
    "name": "John Doe",
    "email": "john@example.com",
    "role": "farmer",
    "phone": "+233 24 123 4567",
    "location": "Kumasi, Ghana",
    "avatar": "/avatars/farmer.jpg",
    "createdAt": "2024-01-01T00:00:00.000Z",
    "lastLogin": "2024-01-01T12:00:00.000Z"
  }
}
```

## Demo Accounts

For testing purposes, the following demo accounts are pre-configured:

| Role | Email | Password | Features |
|------|-------|----------|----------|
| 🌾 Farmer | `farmer@agribot.com` | `password` | Crop management, AI assistance |
| 🛒 Customer | `customer@agribot.com` | `password` | Product browsing, farmer connections |
| 👨‍🌾 Expert | `expert@agribot.com` | `password` | Expert consultations, service management |

## Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| `PORT` | `3001` | Server port |
| `JWT_SECRET` | `your-secret-key-change-in-production` | JWT signing secret |

## Security Features

- **Password Hashing**: Passwords are hashed using bcrypt with salt rounds of 10
- **JWT Tokens**: Secure token-based authentication with 24-hour expiration
- **Input Validation**: All inputs are validated using express-validator
- **CORS**: Cross-origin requests are properly handled
- **Error Handling**: Comprehensive error handling and logging

## Development

### File Structure

```
backend/
├── server.js          # Main server file
├── package.json       # Dependencies and scripts
├── README.md         # This file
└── .env              # Environment variables (create this)
```

### Adding New Endpoints

1. Add the route handler in `server.js`
2. Include proper validation if needed
3. Add authentication middleware if required
4. Update this README with the new endpoint

### Database Integration

Currently using in-memory storage. To integrate with a real database:

1. Install database driver (e.g., `pg` for PostgreSQL, `mongoose` for MongoDB)
2. Replace the `users` array with database queries
3. Add connection pooling and error handling
4. Implement proper database migrations

## Production Deployment

### Environment Setup

1. **Set secure JWT secret:**
   ```bash
   export JWT_SECRET="your-super-secure-random-secret-key"
   ```

2. **Use environment variables:**
   ```bash
   export PORT=3001
   export NODE_ENV=production
   ```

3. **Use PM2 for process management:**
   ```bash
   npm install -g pm2
   pm2 start server.js --name "agribot-api"
   ```

### Security Checklist

- [ ] Change default JWT secret
- [ ] Use HTTPS in production
- [ ] Implement rate limiting
- [ ] Add request logging
- [ ] Set up proper CORS origins
- [ ] Use environment variables for secrets
- [ ] Implement database connection pooling
- [ ] Add health check monitoring

## Troubleshooting

### Common Issues

1. **Port already in use:**
   ```bash
   # Find process using port 3001
   lsof -i :3001
   # Kill the process
   kill -9 <PID>
   ```

2. **CORS errors:**
   - Ensure the frontend URL is properly configured
   - Check that the backend is running on the correct port

3. **JWT token errors:**
   - Verify JWT_SECRET is set correctly
   - Check token expiration
   - Ensure proper Authorization header format

### Logs

The server logs all requests and errors to the console. In production, consider using a logging service like Winston or Bunyan.

## License

MIT License - see the main project LICENSE file for details. 