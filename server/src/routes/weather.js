const express = require('express');
const axios = require('axios');
const router = express.Router();

// GMet API configuration
const GMET_API_KEY = process.env.GMET_API_KEY;
const GMET_BASE_URL = process.env.GMET_BASE_URL || 'https://api.gmet.gov.gh';

// Cache for weather data (in production, use Redis)
const weatherCache = new Map();
const CACHE_DURATION = 30 * 60 * 1000; // 30 minutes

// Helper function to get cached weather data
const getCachedWeather = (location) => {
  const cached = weatherCache.get(location);
  if (cached && Date.now() - cached.timestamp < CACHE_DURATION) {
    return cached.data;
  }
  return null;
};

// Helper function to set cached weather data
const setCachedWeather = (location, data) => {
  weatherCache.set(location, {
    data,
    timestamp: Date.now()
  });
};

// Get current weather for a location
router.get('/current', async (req, res) => {
  try {
    const { location = 'Accra' } = req.query;
    
    // Check cache first
    const cachedData = getCachedWeather(location);
    if (cachedData) {
      return res.json(cachedData);
    }

    // In production, this would call the actual GMet API
    // For now, we'll simulate the API call
    let weatherData;
    
    if (GMET_API_KEY) {
      try {
        // Real GMet API call (uncomment when API is available)
        /*
        const response = await axios.get(`${GMET_BASE_URL}/weather/current`, {
          params: {
            location,
            api_key: GMET_API_KEY
          },
          timeout: 5000
        });
        weatherData = response.data;
        */
        
        // Simulated response for development
        weatherData = {
          temperature: 28 + Math.floor(Math.random() * 5),
          condition: ['Sunny', 'Partly Cloudy', 'Light Rain', 'Overcast'][Math.floor(Math.random() * 4)],
          humidity: 70 + Math.floor(Math.random() * 20),
          windSpeed: 10 + Math.floor(Math.random() * 10),
          pressure: 1013 + Math.floor(Math.random() * 10),
          visibility: 10 + Math.floor(Math.random() * 5),
          location: location,
          timestamp: new Date().toISOString()
        };
      } catch (error) {
        console.error('GMet API error:', error);
        // Fallback to simulated data
        weatherData = {
          temperature: 28,
          condition: 'Partly Cloudy',
          humidity: 75,
          windSpeed: 12,
          pressure: 1013,
          visibility: 10,
          location: location,
          timestamp: new Date().toISOString()
        };
      }
    } else {
      // Development fallback
      weatherData = {
        temperature: 28,
        condition: 'Partly Cloudy',
        humidity: 75,
        windSpeed: 12,
        pressure: 1013,
        visibility: 10,
        location: location,
        timestamp: new Date().toISOString()
      };
    }

    // Add agricultural recommendations based on weather
    weatherData.forecast = generateAgriculturalForecast(weatherData);
    weatherData.recommendations = generateAgriculturalRecommendations(weatherData);

    // Cache the data
    setCachedWeather(location, weatherData);

    res.json(weatherData);
  } catch (error) {
    console.error('Weather API error:', error);
    res.status(500).json({
      error: 'Failed to fetch weather data',
      message: error.message
    });
  }
});

// Get weather forecast for multiple days
router.get('/forecast', async (req, res) => {
  try {
    const { location = 'Accra', days = 7 } = req.query;
    const numDays = Math.min(parseInt(days), 14); // Max 14 days

    const forecast = [];
    
    for (let i = 0; i < numDays; i++) {
      const date = new Date();
      date.setDate(date.getDate() + i);
      
      const dayWeather = {
        date: date.toISOString().split('T')[0],
        temperature: {
          min: 20 + Math.floor(Math.random() * 5),
          max: 28 + Math.floor(Math.random() * 8)
        },
        condition: ['Sunny', 'Partly Cloudy', 'Light Rain', 'Overcast'][Math.floor(Math.random() * 4)],
        humidity: 70 + Math.floor(Math.random() * 20),
        windSpeed: 10 + Math.floor(Math.random() * 10),
        precipitation: Math.floor(Math.random() * 10),
        location: location,
        agriculturalAdvice: generateDailyAgriculturalAdvice(i)
      };
      
      forecast.push(dayWeather);
    }

    res.json(forecast);
  } catch (error) {
    console.error('Forecast API error:', error);
    res.status(500).json({
      error: 'Failed to fetch weather forecast',
      message: error.message
    });
  }
});

// Get agricultural weather alerts
router.get('/alerts', async (req, res) => {
  try {
    const { location = 'Accra' } = req.query;
    
    // Simulate weather alerts based on conditions
    const alerts = [];
    
    // Check for extreme weather conditions
    const currentWeather = getCachedWeather(location);
    if (currentWeather) {
      if (currentWeather.temperature > 35) {
        alerts.push({
          type: 'heat_warning',
          severity: 'moderate',
          message: 'High temperature alert. Consider irrigation for crops.',
          recommendation: 'Water crops early morning or late evening to reduce evaporation.'
        });
      }
      
      if (currentWeather.humidity > 90) {
        alerts.push({
          type: 'humidity_warning',
          severity: 'low',
          message: 'High humidity conditions. Monitor for fungal diseases.',
          recommendation: 'Ensure proper crop spacing for air circulation.'
        });
      }
      
      if (currentWeather.windSpeed > 20) {
        alerts.push({
          type: 'wind_warning',
          severity: 'moderate',
          message: 'Strong winds detected. Protect young crops.',
          recommendation: 'Use windbreaks or temporary covers for vulnerable crops.'
        });
      }
    }

    res.json({
      location,
      alerts,
      timestamp: new Date().toISOString()
    });
  } catch (error) {
    console.error('Alerts API error:', error);
    res.status(500).json({
      error: 'Failed to fetch weather alerts',
      message: error.message
    });
  }
});

// Helper function to generate agricultural forecast
function generateAgriculturalForecast(weatherData) {
  const { temperature, condition, humidity } = weatherData;
  
  if (condition.includes('Rain')) {
    return 'Rain expected. Good conditions for planting maize, vegetables, and root crops. Avoid spraying pesticides during rain.';
  } else if (temperature > 30) {
    return 'High temperatures. Ensure adequate irrigation for crops. Consider shade for young plants.';
  } else if (humidity > 85) {
    return 'High humidity. Monitor crops for fungal diseases. Ensure proper ventilation in greenhouses.';
  } else {
    return 'Favorable weather conditions for agricultural activities. Good time for planting and crop maintenance.';
  }
}

// Helper function to generate agricultural recommendations
function generateAgriculturalRecommendations(weatherData) {
  const { temperature, condition, humidity, windSpeed } = weatherData;
  const recommendations = [];

  if (temperature >= 25 && temperature <= 32) {
    recommendations.push('Optimal temperature for most crops. Good time for planting.');
  }
  
  if (condition.includes('Rain')) {
    recommendations.push('Rain provides natural irrigation. Reduce manual watering.');
    recommendations.push('Avoid applying fertilizers during heavy rain.');
  }
  
  if (humidity > 80) {
    recommendations.push('High humidity increases disease risk. Monitor for fungal infections.');
  }
  
  if (windSpeed > 15) {
    recommendations.push('Strong winds may damage crops. Consider wind protection measures.');
  }

  return recommendations;
}

// Helper function to generate daily agricultural advice
function generateDailyAgriculturalAdvice(dayOffset) {
  const advice = [
    'Good day for planting maize and vegetables.',
    'Consider applying organic fertilizers.',
    'Monitor soil moisture levels.',
    'Check for pest infestations.',
    'Harvest mature crops if ready.',
    'Prepare soil for next planting cycle.',
    'Irrigate crops if no rain expected.'
  ];
  
  return advice[dayOffset % advice.length];
}

module.exports = router; 