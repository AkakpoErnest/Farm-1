# Supabase Setup Guide for Farm Talk Ghana

This guide will help you set up Supabase for the Farm Talk Ghana application.

## 🚀 Quick Start

### 1. Create a Supabase Project

1. Go to [supabase.com](https://supabase.com)
2. Sign up or log in to your account
3. Click "New Project"
4. Choose your organization
5. Enter project details:
   - **Name**: `farm-talk-ghana`
   - **Database Password**: Choose a strong password
   - **Region**: Select the closest region to Ghana (e.g., Europe West)
6. Click "Create new project"

### 2. Get Your Project Credentials

1. In your Supabase dashboard, go to **Settings** → **API**
2. Copy the following values:
   - **Project URL** (e.g., `https://your-project-id.supabase.co`)
   - **Anon/Public Key** (starts with `eyJ...`)

### 3. Configure Environment Variables

Create a `.env` file in your project root with the following variables:

```env
# API Configuration
VITE_API_BASE_URL=http://localhost:3001/api

# Supabase Configuration
VITE_SUPABASE_URL=https://your-project-id.supabase.co
VITE_SUPABASE_ANON_KEY=your_anon_key_here

# Optional: Supabase Service Role Key (for server-side operations)
SUPABASE_SERVICE_ROLE_KEY=your_service_role_key_here
```

### 4. Set Up Database Tables

Run the following SQL in your Supabase SQL Editor:

```sql
-- Enable Row Level Security
ALTER TABLE auth.users ENABLE ROW LEVEL SECURITY;

-- Create users table
CREATE TABLE public.users (
    id UUID REFERENCES auth.users(id) ON DELETE CASCADE PRIMARY KEY,
    email TEXT UNIQUE NOT NULL,
    full_name TEXT NOT NULL,
    role TEXT CHECK (role IN ('farmer', 'expert', 'customer')) DEFAULT 'farmer',
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Create chat_messages table
CREATE TABLE public.chat_messages (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    user_id UUID REFERENCES auth.users(id) ON DELETE CASCADE NOT NULL,
    message TEXT NOT NULL,
    response TEXT NOT NULL,
    language TEXT DEFAULT 'en',
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Create weather_data table
CREATE TABLE public.weather_data (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    location TEXT NOT NULL,
    temperature DECIMAL(4,1) NOT NULL,
    humidity INTEGER NOT NULL,
    description TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Create market_prices table
CREATE TABLE public.market_prices (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    crop_name TEXT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    unit TEXT NOT NULL,
    location TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Enable RLS on all tables
ALTER TABLE public.users ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.chat_messages ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.weather_data ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.market_prices ENABLE ROW LEVEL SECURITY;

-- Create RLS policies

-- Users can read their own profile
CREATE POLICY "Users can view own profile" ON public.users
    FOR SELECT USING (auth.uid() = id);

-- Users can update their own profile
CREATE POLICY "Users can update own profile" ON public.users
    FOR UPDATE USING (auth.uid() = id);

-- Users can insert their own profile
CREATE POLICY "Users can insert own profile" ON public.users
    FOR INSERT WITH CHECK (auth.uid() = id);

-- Users can view their own chat messages
CREATE POLICY "Users can view own chat messages" ON public.chat_messages
    FOR SELECT USING (auth.uid() = user_id);

-- Users can insert their own chat messages
CREATE POLICY "Users can insert own chat messages" ON public.chat_messages
    FOR INSERT WITH CHECK (auth.uid() = user_id);

-- Anyone can read weather data
CREATE POLICY "Anyone can read weather data" ON public.weather_data
    FOR SELECT USING (true);

-- Anyone can read market prices
CREATE POLICY "Anyone can read market prices" ON public.market_prices
    FOR SELECT USING (true);

-- Create indexes for better performance
CREATE INDEX idx_chat_messages_user_id ON public.chat_messages(user_id);
CREATE INDEX idx_chat_messages_created_at ON public.chat_messages(created_at);
CREATE INDEX idx_weather_data_location ON public.weather_data(location);
CREATE INDEX idx_weather_data_created_at ON public.weather_data(created_at);
CREATE INDEX idx_market_prices_crop_name ON public.market_prices(crop_name);
CREATE INDEX idx_market_prices_location ON public.market_prices(location);
CREATE INDEX idx_market_prices_created_at ON public.market_prices(created_at);

-- Create function to handle user creation
CREATE OR REPLACE FUNCTION public.handle_new_user()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO public.users (id, email, full_name, role)
    VALUES (
        NEW.id,
        NEW.email,
        COALESCE(NEW.raw_user_meta_data->>'full_name', NEW.email),
        COALESCE(NEW.raw_user_meta_data->>'role', 'farmer')
    );
    RETURN NEW;
END;
$$ LANGUAGE plpgsql SECURITY DEFINER;

-- Create trigger for new user creation
CREATE TRIGGER on_auth_user_created
    AFTER INSERT ON auth.users
    FOR EACH ROW EXECUTE FUNCTION public.handle_new_user();
```

### 5. Configure Authentication

1. Go to **Authentication** → **Settings**
2. Configure your site URL: `http://localhost:8081`
3. Add redirect URLs:
   - `http://localhost:8081/auth/callback`
   - `http://localhost:8081/reset-password`

### 6. Set Up OAuth Providers (Optional)

#### Google OAuth
1. Go to **Authentication** → **Providers**
2. Enable Google provider
3. Add your Google OAuth credentials

#### GitHub OAuth
1. Go to **Authentication** → **Providers**
2. Enable GitHub provider
3. Add your GitHub OAuth credentials

### 7. Test the Integration

1. Start your development servers:
   ```bash
   # Terminal 1 - Backend
   cd server && npm run dev
   
   # Terminal 2 - Frontend
   npm run dev
   ```

2. Open your browser to `http://localhost:8081`
3. Try registering a new user
4. Test the chat functionality

## 🔧 Advanced Configuration

### Real-time Subscriptions

The app includes real-time subscriptions for:
- Chat messages
- Weather updates
- Market price changes

### Storage (Optional)

If you want to add file uploads (e.g., user avatars, farm photos):

1. Go to **Storage** in your Supabase dashboard
2. Create a new bucket called `avatars`
3. Set up storage policies

### Edge Functions (Optional)

For advanced server-side logic, you can create Supabase Edge Functions:

1. Install Supabase CLI: `npm install -g supabase`
2. Initialize: `supabase init`
3. Create functions in `supabase/functions/`

## 🚨 Troubleshooting

### Common Issues

1. **CORS Errors**: Make sure your site URL is configured correctly in Supabase
2. **RLS Policy Errors**: Check that your RLS policies are set up correctly
3. **Environment Variables**: Ensure your `.env` file is in the project root

### Debug Mode

Enable debug logging by adding to your `.env`:

```env
VITE_SUPABASE_DEBUG=true
```

## 📚 Additional Resources

- [Supabase Documentation](https://supabase.com/docs)
- [Supabase JavaScript Client](https://supabase.com/docs/reference/javascript)
- [Row Level Security Guide](https://supabase.com/docs/guides/auth/row-level-security)

## 🔐 Security Best Practices

1. Never expose your service role key in the frontend
2. Use RLS policies to secure your data
3. Validate all user inputs
4. Use HTTPS in production
5. Regularly rotate your API keys

## 🚀 Production Deployment

When deploying to production:

1. Update your site URL in Supabase
2. Add your production domain to redirect URLs
3. Use environment variables for all sensitive data
4. Enable email confirmations if needed
5. Set up proper backup strategies



