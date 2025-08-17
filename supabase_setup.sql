-- Farm Talk Ghana - Supabase Database Setup
-- Run this SQL in your Supabase SQL Editor

-- Enable Row Level Security
ALTER TABLE auth.users ENABLE ROW LEVEL SECURITY;

-- Create users table
CREATE TABLE IF NOT EXISTS public.users (
    id UUID REFERENCES auth.users(id) ON DELETE CASCADE PRIMARY KEY,
    email TEXT UNIQUE NOT NULL,
    full_name TEXT NOT NULL,
    role TEXT CHECK (role IN ('farmer', 'expert', 'customer')) DEFAULT 'farmer',
    phone TEXT,
    location TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Create chat_messages table
CREATE TABLE IF NOT EXISTS public.chat_messages (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    user_id UUID REFERENCES auth.users(id) ON DELETE CASCADE NOT NULL,
    message TEXT NOT NULL,
    response TEXT NOT NULL,
    language TEXT DEFAULT 'en',
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Create weather_data table
CREATE TABLE IF NOT EXISTS public.weather_data (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    location TEXT NOT NULL,
    temperature DECIMAL(4,1) NOT NULL,
    humidity INTEGER NOT NULL,
    description TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Create market_prices table
CREATE TABLE IF NOT EXISTS public.market_prices (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    crop_name TEXT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    unit TEXT NOT NULL,
    location TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Create subsidies table
CREATE TABLE IF NOT EXISTS public.subsidies (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    title TEXT NOT NULL,
    description TEXT NOT NULL,
    amount DECIMAL(12,2),
    eligibility_criteria TEXT,
    application_deadline DATE,
    status TEXT DEFAULT 'active',
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Create experts table
CREATE TABLE IF NOT EXISTS public.experts (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    user_id UUID REFERENCES auth.users(id) ON DELETE CASCADE,
    specialization TEXT NOT NULL,
    experience_years INTEGER,
    certifications TEXT[],
    availability TEXT DEFAULT 'available',
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Enable RLS on all tables
ALTER TABLE public.users ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.chat_messages ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.weather_data ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.market_prices ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.subsidies ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.experts ENABLE ROW LEVEL SECURITY;

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

-- Anyone can view weather data (public)
CREATE POLICY "Anyone can view weather data" ON public.weather_data
    FOR SELECT USING (true);

-- Anyone can view market prices (public)
CREATE POLICY "Anyone can view market prices" ON public.market_prices
    FOR SELECT USING (true);

-- Anyone can view subsidies (public)
CREATE POLICY "Anyone can view subsidies" ON public.subsidies
    FOR SELECT USING (true);

-- Anyone can view experts (public)
CREATE POLICY "Anyone can view experts" ON public.experts
    FOR SELECT USING (true);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_users_email ON public.users(email);
CREATE INDEX IF NOT EXISTS idx_chat_messages_user_id ON public.chat_messages(user_id);
CREATE INDEX IF NOT EXISTS idx_chat_messages_created_at ON public.chat_messages(created_at);
CREATE INDEX IF NOT EXISTS idx_weather_data_location ON public.weather_data(location);
CREATE INDEX IF NOT EXISTS idx_market_prices_crop_location ON public.market_prices(crop_name, location);
CREATE INDEX IF NOT EXISTS idx_subsidies_status ON public.subsidies(status);
CREATE INDEX IF NOT EXISTS idx_experts_specialization ON public.experts(specialization);

-- Insert some sample data for testing

-- Sample weather data
INSERT INTO public.weather_data (location, temperature, humidity, description) VALUES
('Kumasi', 28.5, 75, 'Partly cloudy with light rain'),
('Accra', 30.2, 80, 'Sunny and humid'),
('Ho', 26.8, 70, 'Clear skies, mild temperature'),
('Tamale', 32.1, 65, 'Hot and dry');

-- Sample market prices
INSERT INTO public.market_prices (crop_name, price, unit, location) VALUES
('Maize', 2.50, 'kg', 'Kumasi'),
('Cassava', 1.80, 'kg', 'Accra'),
('Yam', 3.20, 'kg', 'Ho'),
('Rice', 4.10, 'kg', 'Tamale');

-- Sample subsidies
INSERT INTO public.subsidies (title, description, amount, eligibility_criteria, application_deadline) VALUES
('Youth in Agriculture Grant', 'Financial support for young farmers aged 18-35', 5000.00, 'Age 18-35, first-time farmer', '2024-12-31'),
('Fertilizer Subsidy Program', 'Reduced cost fertilizers for registered farmers', 200.00, 'Registered farmer, valid ID', '2024-11-30'),
('Equipment Loan Scheme', 'Low-interest loans for farming equipment', 10000.00, 'Established farmer, good credit', '2024-10-31');

-- Create a function to automatically create user profile on signup
CREATE OR REPLACE FUNCTION public.handle_new_user()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO public.users (id, email, full_name, role, phone, location)
    VALUES (
        NEW.id,
        NEW.email,
        COALESCE(NEW.raw_user_meta_data->>'full_name', 'User'),
        COALESCE(NEW.raw_user_meta_data->>'role', 'farmer'),
        COALESCE(NEW.raw_user_meta_data->>'phone', ''),
        COALESCE(NEW.raw_user_meta_data->>'location', '')
    );
    RETURN NEW;
END;
$$ LANGUAGE plpgsql SECURITY DEFINER;

-- Create trigger to automatically create user profile
DROP TRIGGER IF EXISTS on_auth_user_created ON auth.users;
CREATE TRIGGER on_auth_user_created
    AFTER INSERT ON auth.users
    FOR EACH ROW EXECUTE FUNCTION public.handle_new_user();

-- Grant necessary permissions
GRANT USAGE ON SCHEMA public TO anon, authenticated;
GRANT ALL ON public.users TO anon, authenticated;
GRANT ALL ON public.chat_messages TO anon, authenticated;
GRANT ALL ON public.weather_data TO anon, authenticated;
GRANT ALL ON public.market_prices TO anon, authenticated;
GRANT ALL ON public.subsidies TO anon, authenticated;
GRANT ALL ON public.experts TO anon, authenticated;
