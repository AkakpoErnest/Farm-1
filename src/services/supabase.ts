import { supabase } from '@/lib/supabase';
import type { Tables, Inserts, Updates } from '@/lib/supabase';

// Chat Messages Service
export const chatService = {
  // Get chat history for a user
  async getChatHistory(userId: string): Promise<Tables<'chat_messages'>[]> {
    try {
      const { data, error } = await supabase
        .from('chat_messages')
        .select('*')
        .eq('user_id', userId)
        .order('created_at', { ascending: false })
        .limit(50);

      if (error) throw error;
      return data || [];
    } catch (error) {
      console.error('Error fetching chat history:', error);
      throw error;
    }
  },

  // Save a chat message
  async saveMessage(message: Inserts<'chat_messages'>): Promise<Tables<'chat_messages'>> {
    try {
      const { data, error } = await supabase
        .from('chat_messages')
        .insert(message)
        .select()
        .single();

      if (error) throw error;
      return data;
    } catch (error) {
      console.error('Error saving chat message:', error);
      throw error;
    }
  },

  // Get messages by language
  async getMessagesByLanguage(language: string): Promise<Tables<'chat_messages'>[]> {
    try {
      const { data, error } = await supabase
        .from('chat_messages')
        .select('*')
        .eq('language', language)
        .order('created_at', { ascending: false })
        .limit(20);

      if (error) throw error;
      return data || [];
    } catch (error) {
      console.error('Error fetching messages by language:', error);
      throw error;
    }
  },
};

// Weather Data Service
export const weatherService = {
  // Get weather data for a location
  async getWeatherData(location: string): Promise<Tables<'weather_data'>[]> {
    try {
      const { data, error } = await supabase
        .from('weather_data')
        .select('*')
        .eq('location', location)
        .order('created_at', { ascending: false })
        .limit(7); // Last 7 days

      if (error) throw error;
      return data || [];
    } catch (error) {
      console.error('Error fetching weather data:', error);
      throw error;
    }
  },

  // Save weather data
  async saveWeatherData(weatherData: Inserts<'weather_data'>): Promise<Tables<'weather_data'>> {
    try {
      const { data, error } = await supabase
        .from('weather_data')
        .insert(weatherData)
        .select()
        .single();

      if (error) throw error;
      return data;
    } catch (error) {
      console.error('Error saving weather data:', error);
      throw error;
    }
  },

  // Get latest weather for multiple locations
  async getLatestWeather(locations: string[]): Promise<Tables<'weather_data'>[]> {
    try {
      const { data, error } = await supabase
        .from('weather_data')
        .select('*')
        .in('location', locations)
        .order('created_at', { ascending: false });

      if (error) throw error;
      return data || [];
    } catch (error) {
      console.error('Error fetching latest weather:', error);
      throw error;
    }
  },
};

// Market Prices Service
export const marketService = {
  // Get market prices for crops
  async getMarketPrices(cropNames?: string[]): Promise<Tables<'market_prices'>[]> {
    try {
      let query = supabase
        .from('market_prices')
        .select('*')
        .order('created_at', { ascending: false });

      if (cropNames && cropNames.length > 0) {
        query = query.in('crop_name', cropNames);
      }

      const { data, error } = await query.limit(50);

      if (error) throw error;
      return data || [];
    } catch (error) {
      console.error('Error fetching market prices:', error);
      throw error;
    }
  },

  // Save market price data
  async saveMarketPrice(priceData: Inserts<'market_prices'>): Promise<Tables<'market_prices'>> {
    try {
      const { data, error } = await supabase
        .from('market_prices')
        .insert(priceData)
        .select()
        .single();

      if (error) throw error;
      return data;
    } catch (error) {
      console.error('Error saving market price:', error);
      throw error;
    }
  },

  // Get prices by location
  async getPricesByLocation(location: string): Promise<Tables<'market_prices'>[]> {
    try {
      const { data, error } = await supabase
        .from('market_prices')
        .select('*')
        .eq('location', location)
        .order('created_at', { ascending: false })
        .limit(20);

      if (error) throw error;
      return data || [];
    } catch (error) {
      console.error('Error fetching prices by location:', error);
      throw error;
    }
  },

  // Get price trends for a crop
  async getPriceTrends(cropName: string, days: number = 30): Promise<Tables<'market_prices'>[]> {
    try {
      const { data, error } = await supabase
        .from('market_prices')
        .select('*')
        .eq('crop_name', cropName)
        .gte('created_at', new Date(Date.now() - days * 24 * 60 * 60 * 1000).toISOString())
        .order('created_at', { ascending: true });

      if (error) throw error;
      return data || [];
    } catch (error) {
      console.error('Error fetching price trends:', error);
      throw error;
    }
  },
};

// Users Service
export const userService = {
  // Get user profile
  async getUserProfile(userId: string): Promise<Tables<'users'> | null> {
    try {
      const { data, error } = await supabase
        .from('users')
        .select('*')
        .eq('id', userId)
        .single();

      if (error) throw error;
      return data;
    } catch (error) {
      console.error('Error fetching user profile:', error);
      return null;
    }
  },

  // Update user profile
  async updateUserProfile(userId: string, updates: Updates<'users'>): Promise<Tables<'users'>> {
    try {
      const { data, error } = await supabase
        .from('users')
        .update(updates)
        .eq('id', userId)
        .select()
        .single();

      if (error) throw error;
      return data;
    } catch (error) {
      console.error('Error updating user profile:', error);
      throw error;
    }
  },

  // Create user profile
  async createUserProfile(userData: Inserts<'users'>): Promise<Tables<'users'>> {
    try {
      const { data, error } = await supabase
        .from('users')
        .insert(userData)
        .select()
        .single();

      if (error) throw error;
      return data;
    } catch (error) {
      console.error('Error creating user profile:', error);
      throw error;
    }
  },

  // Get users by role
  async getUsersByRole(role: string): Promise<Tables<'users'>[]> {
    try {
      const { data, error } = await supabase
        .from('users')
        .select('*')
        .eq('role', role)
        .order('created_at', { ascending: false });

      if (error) throw error;
      return data || [];
    } catch (error) {
      console.error('Error fetching users by role:', error);
      throw error;
    }
  },
};

// Real-time subscriptions
export const realtimeService = {
  // Subscribe to chat messages
  subscribeToChat(userId: string, callback: (payload: any) => void) {
    return supabase
      .channel(`chat:${userId}`)
      .on('postgres_changes', {
        event: 'INSERT',
        schema: 'public',
        table: 'chat_messages',
        filter: `user_id=eq.${userId}`,
      }, callback)
      .subscribe();
  },

  // Subscribe to weather updates
  subscribeToWeather(location: string, callback: (payload: any) => void) {
    return supabase
      .channel(`weather:${location}`)
      .on('postgres_changes', {
        event: 'INSERT',
        schema: 'public',
        table: 'weather_data',
        filter: `location=eq.${location}`,
      }, callback)
      .subscribe();
  },

  // Subscribe to market price updates
  subscribeToMarketPrices(callback: (payload: any) => void) {
    return supabase
      .channel('market_prices')
      .on('postgres_changes', {
        event: 'INSERT',
        schema: 'public',
        table: 'market_prices',
      }, callback)
      .subscribe();
  },
};



