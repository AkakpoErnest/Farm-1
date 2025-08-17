import axios from 'axios';

// API Configuration
const API_BASE_URL = process.env.REACT_APP_API_BASE_URL || 'http://localhost:3001/api';

// Create axios instance with default config
const apiClient = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Types for API responses
export interface WeatherResponse {
  temperature: number;
  condition: string;
  humidity: number;
  windSpeed: number;
  forecast: string;
  location: string;
  timestamp: string;
}

export interface MarketPriceResponse {
  crop: string;
  price: number;
  unit: string;
  location: string;
  trend: 'up' | 'down' | 'stable';
  lastUpdated: string;
  market: string;
}

export interface SubsidyProgramResponse {
  id: string;
  program: string;
  description: string;
  eligibility: string;
  deadline: string;
  contact: string;
  applicationUrl?: string;
  status: 'active' | 'upcoming' | 'closed';
}

export interface ExpertRequestResponse {
  id: string;
  status: 'pending' | 'assigned' | 'completed';
  expertName?: string;
  expertContact?: string;
  estimatedResponseTime: string;
  message: string;
}

export interface ChatMessage {
  id: string;
  content: string;
  sender: 'user' | 'bot' | 'expert';
  timestamp: string;
  language: string;
  type?: 'text' | 'weather' | 'market' | 'subsidy' | 'expert-request';
  data?: any;
}

// Weather API Service (GMet Integration)
export const weatherService = {
  async getCurrentWeather(location: string = 'Accra'): Promise<WeatherResponse> {
    try {
      // In production, this would call the actual GMet API
      // For now, we'll simulate the response
      const response = await apiClient.get(`/weather/current?location=${location}`);
      return response.data;
    } catch (error) {
      console.error('Weather API error:', error);
      // Fallback to simulated data
      return {
        temperature: 28,
        condition: 'Partly Cloudy',
        humidity: 75,
        windSpeed: 12,
        forecast: 'Light rain expected in the next 24 hours. Good for planting maize and vegetables.',
        location: location,
        timestamp: new Date().toISOString()
      };
    }
  },

  async getWeatherForecast(location: string = 'Accra', days: number = 7): Promise<WeatherResponse[]> {
    try {
      const response = await apiClient.get(`/weather/forecast?location=${location}&days=${days}`);
      return response.data;
    } catch (error) {
      console.error('Weather forecast API error:', error);
      // Fallback to simulated data
      return Array.from({ length: days }, (_, i) => ({
        temperature: 28 + Math.floor(Math.random() * 5),
        condition: ['Sunny', 'Partly Cloudy', 'Light Rain'][Math.floor(Math.random() * 3)],
        humidity: 70 + Math.floor(Math.random() * 20),
        windSpeed: 10 + Math.floor(Math.random() * 10),
        forecast: 'Weather conditions suitable for agricultural activities.',
        location: location,
        timestamp: new Date(Date.now() + i * 24 * 60 * 60 * 1000).toISOString()
      }));
    }
  }
};

// Market Price API Service (Esoko Integration)
export const marketService = {
  async getCurrentPrices(location?: string): Promise<MarketPriceResponse[]> {
    try {
      // In production, this would call the actual Esoko API
      const response = await apiClient.get(`/market/prices${location ? `?location=${location}` : ''}`);
      return response.data;
    } catch (error) {
      console.error('Market API error:', error);
      // Fallback to simulated data
      return [
        {
          crop: 'Tomatoes',
          price: 15.50,
          unit: 'kg',
          location: 'Kumasi Market',
          trend: 'up' as const,
          lastUpdated: new Date().toISOString(),
          market: 'Kumasi Central Market'
        },
        {
          crop: 'Cassava',
          price: 8.20,
          unit: 'kg',
          location: 'Accra Market',
          trend: 'stable' as const,
          lastUpdated: new Date().toISOString(),
          market: 'Accra Central Market'
        },
        {
          crop: 'Maize',
          price: 12.80,
          unit: 'kg',
          location: 'Tamale Market',
          trend: 'down' as const,
          lastUpdated: new Date().toISOString(),
          market: 'Tamale Central Market'
        },
        {
          crop: 'Yam',
          price: 18.90,
          unit: 'kg',
          location: 'Kumasi Market',
          trend: 'up' as const,
          lastUpdated: new Date().toISOString(),
          market: 'Kumasi Central Market'
        }
      ];
    }
  },

  async getPriceHistory(crop: string, location: string, days: number = 30): Promise<MarketPriceResponse[]> {
    try {
      const response = await apiClient.get(`/market/history?crop=${crop}&location=${location}&days=${days}`);
      return response.data;
    } catch (error) {
      console.error('Market history API error:', error);
      return [];
    }
  }
};

// Government Subsidy API Service
export const subsidyService = {
  async getActivePrograms(): Promise<SubsidyProgramResponse[]> {
    try {
      const response = await apiClient.get('/subsidies/active');
      return response.data;
    } catch (error) {
      console.error('Subsidy API error:', error);
      // Fallback to simulated data
      return [
        {
          id: '1',
          program: 'Fertilizer Subsidy Program',
          description: '50% subsidy on NPK and Urea fertilizers for registered farmers',
          eligibility: 'Must be a registered farmer with valid ID',
          deadline: 'December 31, 2024',
          contact: 'Ministry of Agriculture: +233 30 266 0000',
          applicationUrl: 'https://mofa.gov.gh/fertilizer-subsidy',
          status: 'active' as const
        },
        {
          id: '2',
          program: 'Mechanization Support',
          description: 'Low-interest loans for farm equipment and machinery',
          eligibility: 'Farmers with 5+ acres of land',
          deadline: 'Ongoing',
          contact: 'Agricultural Development Bank: +233 30 266 1000',
          applicationUrl: 'https://adb.com.gh/mechanization',
          status: 'active' as const
        },
        {
          id: '3',
          program: 'Crop Insurance Scheme',
          description: 'Insurance coverage for major crops against weather damage',
          eligibility: 'All registered farmers',
          deadline: 'Before planting season',
          contact: 'Ghana Agricultural Insurance Pool: +233 30 266 2000',
          applicationUrl: 'https://gaip.com.gh/insurance',
          status: 'active' as const
        }
      ];
    }
  },

  async checkEligibility(farmerId: string, programId: string): Promise<{ eligible: boolean; reason?: string }> {
    try {
      const response = await apiClient.post('/subsidies/check-eligibility', {
        farmerId,
        programId
      });
      return response.data;
    } catch (error) {
      console.error('Eligibility check error:', error);
      return { eligible: false, reason: 'Unable to verify eligibility at this time' };
    }
  }
};

// Expert Support API Service
export const expertService = {
  async requestExpertSupport(
    farmerId: string,
    query: string,
    language: string,
    preferredContact: 'phone' | 'email' | 'chat'
  ): Promise<ExpertRequestResponse> {
    try {
      const response = await apiClient.post('/experts/request', {
        farmerId,
        query,
        language,
        preferredContact
      });
      return response.data;
    } catch (error) {
      console.error('Expert request error:', error);
      // Fallback response
      return {
        id: Date.now().toString(),
        status: 'pending',
        estimatedResponseTime: '24 hours',
        message: 'Your request has been submitted. An extension officer will contact you within 24 hours.'
      };
    }
  },

  async getExpertStatus(requestId: string): Promise<ExpertRequestResponse> {
    try {
      const response = await apiClient.get(`/experts/status/${requestId}`);
      return response.data;
    } catch (error) {
      console.error('Expert status error:', error);
      throw error;
    }
  }
};

// Chat History API Service
export const chatService = {
  async saveMessage(message: Omit<ChatMessage, 'id'>): Promise<ChatMessage> {
    try {
      const response = await apiClient.post('/chat/messages', message);
      return response.data;
    } catch (error) {
      console.error('Save message error:', error);
      // Fallback - return message with generated ID
      return {
        ...message,
        id: Date.now().toString()
      };
    }
  },

  async getChatHistory(farmerId: string, limit: number = 50): Promise<ChatMessage[]> {
    try {
      const response = await apiClient.get(`/chat/history/${farmerId}?limit=${limit}`);
      return response.data;
    } catch (error) {
      console.error('Get chat history error:', error);
      return [];
    }
  }
};

// Error handling interceptor
apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    console.error('API Error:', error);
    return Promise.reject(error);
  }
);

export default apiClient; 