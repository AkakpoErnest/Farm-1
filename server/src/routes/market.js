const express = require('express');
const axios = require('axios');
const router = express.Router();

// Esoko API configuration
const ESOKO_API_KEY = process.env.ESOKO_API_KEY;
const ESOKO_BASE_URL = process.env.ESOKO_BASE_URL || 'https://api.esoko.com';

// Cache for market data (in production, use Redis)
const marketCache = new Map();
const CACHE_DURATION = 60 * 60 * 1000; // 1 hour

// Helper function to get cached market data
const getCachedMarketData = (location) => {
  const cached = marketCache.get(location);
  if (cached && Date.now() - cached.timestamp < CACHE_DURATION) {
    return cached.data;
  }
  return null;
};

// Helper function to set cached market data
const setCachedMarketData = (location, data) => {
  marketCache.set(location, {
    data,
    timestamp: Date.now()
  });
};

// Get current market prices
router.get('/prices', async (req, res) => {
  try {
    const { location, crop } = req.query;
    
    // Check cache first
    const cacheKey = location || 'all';
    const cachedData = getCachedMarketData(cacheKey);
    if (cachedData && !crop) {
      return res.json(cachedData);
    }

    let marketData;
    
    if (ESOKO_API_KEY) {
      try {
        // Real Esoko API call (uncomment when API is available)
        /*
        const response = await axios.get(`${ESOKO_BASE_URL}/prices`, {
          params: {
            location,
            crop,
            api_key: ESOKO_API_KEY
          },
          timeout: 5000
        });
        marketData = response.data;
        */
        
        // Simulated response for development
        marketData = generateSimulatedMarketData(location, crop);
      } catch (error) {
        console.error('Esoko API error:', error);
        marketData = generateSimulatedMarketData(location, crop);
      }
    } else {
      marketData = generateSimulatedMarketData(location, crop);
    }

    // Cache the data if not filtering by specific crop
    if (!crop) {
      setCachedMarketData(cacheKey, marketData);
    }

    res.json(marketData);
  } catch (error) {
    console.error('Market API error:', error);
    res.status(500).json({
      error: 'Failed to fetch market data',
      message: error.message
    });
  }
});

// Get price history for a specific crop
router.get('/history', async (req, res) => {
  try {
    const { crop, location, days = 30 } = req.query;
    
    if (!crop || !location) {
      return res.status(400).json({
        error: 'Crop and location are required'
      });
    }

    const history = generatePriceHistory(crop, location, parseInt(days));
    
    res.json({
      crop,
      location,
      history,
      analysis: analyzePriceTrend(history)
    });
  } catch (error) {
    console.error('Market history API error:', error);
    res.status(500).json({
      error: 'Failed to fetch price history',
      message: error.message
    });
  }
});

// Get market trends and analysis
router.get('/trends', async (req, res) => {
  try {
    const { location } = req.query;
    
    const trends = generateMarketTrends(location);
    
    res.json({
      location: location || 'Ghana',
      trends,
      timestamp: new Date().toISOString()
    });
  } catch (error) {
    console.error('Market trends API error:', error);
    res.status(500).json({
      error: 'Failed to fetch market trends',
      message: error.message
    });
  }
});

// Get nearby markets
router.get('/markets', async (req, res) => {
  try {
    const { location } = req.query;
    
    const markets = [
      {
        name: 'Kumasi Central Market',
        location: 'Kumasi',
        distance: '0 km',
        specialties: ['Tomatoes', 'Yam', 'Cassava'],
        tradingDays: ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],
        contact: '+233 32 202 0000'
      },
      {
        name: 'Accra Central Market',
        location: 'Accra',
        distance: '0 km',
        specialties: ['Cassava', 'Plantain', 'Vegetables'],
        tradingDays: ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],
        contact: '+233 30 266 0000'
      },
      {
        name: 'Tamale Central Market',
        location: 'Tamale',
        distance: '0 km',
        specialties: ['Maize', 'Groundnuts', 'Rice'],
        tradingDays: ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],
        contact: '+233 37 202 0000'
      },
      {
        name: 'Cape Coast Market',
        location: 'Cape Coast',
        distance: '0 km',
        specialties: ['Fish', 'Cassava', 'Cocoa'],
        tradingDays: ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],
        contact: '+233 33 202 0000'
      }
    ];

    // Filter by location if provided
    const filteredMarkets = location 
      ? markets.filter(market => market.location.toLowerCase().includes(location.toLowerCase()))
      : markets;

    res.json(filteredMarkets);
  } catch (error) {
    console.error('Markets API error:', error);
    res.status(500).json({
      error: 'Failed to fetch markets',
      message: error.message
    });
  }
});

// Helper function to generate simulated market data
function generateSimulatedMarketData(location, crop) {
  const basePrices = {
    'Tomatoes': { base: 15.50, variance: 3 },
    'Cassava': { base: 8.20, variance: 2 },
    'Maize': { base: 12.80, variance: 2.5 },
    'Yam': { base: 18.90, variance: 4 },
    'Plantain': { base: 10.50, variance: 2 },
    'Rice': { base: 25.00, variance: 5 },
    'Groundnuts': { base: 22.00, variance: 4 },
    'Cocoa': { base: 35.00, variance: 8 },
    'Fish': { base: 30.00, variance: 6 }
  };

  const locations = ['Kumasi Market', 'Accra Market', 'Tamale Market', 'Cape Coast Market'];
  const trends = ['up', 'down', 'stable'];
  
  if (crop) {
    const basePrice = basePrices[crop] || { base: 15.00, variance: 3 };
    const price = basePrice.base + (Math.random() - 0.5) * basePrice.variance;
    const selectedLocation = location || locations[Math.floor(Math.random() * locations.length)];
    
    return [{
      crop,
      price: Math.round(price * 100) / 100,
      unit: 'kg',
      location: selectedLocation,
      trend: trends[Math.floor(Math.random() * trends.length)],
      lastUpdated: new Date().toISOString(),
      market: selectedLocation
    }];
  }

  // Return all crops
  return Object.keys(basePrices).map(cropName => {
    const basePrice = basePrices[cropName];
    const price = basePrice.base + (Math.random() - 0.5) * basePrice.variance;
    const selectedLocation = location || locations[Math.floor(Math.random() * locations.length)];
    
    return {
      crop: cropName,
      price: Math.round(price * 100) / 100,
      unit: 'kg',
      location: selectedLocation,
      trend: trends[Math.floor(Math.random() * trends.length)],
      lastUpdated: new Date().toISOString(),
      market: selectedLocation
    };
  });
}

// Helper function to generate price history
function generatePriceHistory(crop, location, days) {
  const history = [];
  const basePrice = 15 + Math.random() * 10;
  
  for (let i = days - 1; i >= 0; i--) {
    const date = new Date();
    date.setDate(date.getDate() - i);
    
    // Add some realistic price variation
    const variation = (Math.random() - 0.5) * 2;
    const price = Math.max(0, basePrice + variation);
    
    history.push({
      date: date.toISOString().split('T')[0],
      price: Math.round(price * 100) / 100,
      volume: Math.floor(Math.random() * 1000) + 100
    });
  }
  
  return history;
}

// Helper function to analyze price trends
function analyzePriceTrend(history) {
  if (history.length < 2) {
    return { trend: 'stable', confidence: 0 };
  }
  
  const recent = history.slice(-7); // Last 7 days
  const older = history.slice(-14, -7); // Previous 7 days
  
  const recentAvg = recent.reduce((sum, item) => sum + item.price, 0) / recent.length;
  const olderAvg = older.reduce((sum, item) => sum + item.price, 0) / older.length;
  
  const change = ((recentAvg - olderAvg) / olderAvg) * 100;
  
  let trend = 'stable';
  if (change > 5) trend = 'up';
  else if (change < -5) trend = 'down';
  
  return {
    trend,
    change: Math.round(change * 100) / 100,
    confidence: Math.min(Math.abs(change) / 10, 1)
  };
}

// Helper function to generate market trends
function generateMarketTrends(location) {
  const crops = ['Tomatoes', 'Cassava', 'Maize', 'Yam', 'Plantain'];
  
  return crops.map(crop => {
    const trend = Math.random() > 0.5 ? 'up' : 'down';
    const change = (Math.random() * 10 - 5).toFixed(2);
    
    return {
      crop,
      trend,
      change: parseFloat(change),
      reason: generateTrendReason(crop, trend, change)
    };
  });
}

// Helper function to generate trend reasons
function generateTrendReason(crop, trend, change) {
  const reasons = {
    'Tomatoes': {
      up: 'High demand from restaurants and households',
      down: 'Increased supply from new harvests'
    },
    'Cassava': {
      up: 'Export demand increasing',
      down: 'Good harvest season'
    },
    'Maize': {
      up: 'Feed industry demand',
      down: 'Bumper harvest this season'
    },
    'Yam': {
      up: 'Festival season demand',
      down: 'New yam harvest available'
    },
    'Plantain': {
      up: 'Processing industry demand',
      down: 'Abundant supply from farms'
    }
  };
  
  return reasons[crop]?.[trend] || 'Market conditions affecting prices';
}

module.exports = router; 