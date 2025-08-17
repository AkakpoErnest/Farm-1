const express = require('express');
const router = express.Router();

// POST /api/chat - Send a chat message
router.post('/', (req, res) => {
  const { message, language = 'en' } = req.body;
  
  // Simple response logic
  let response = '';
  if (message.toLowerCase().includes('weather')) {
    response = 'I can help you with weather information. Please specify your location.';
  } else if (message.toLowerCase().includes('price') || message.toLowerCase().includes('market')) {
    response = 'I can provide current market prices for various crops. What crop are you interested in?';
  } else if (message.toLowerCase().includes('subsidy')) {
    response = 'There are several government subsidies available for farmers. Would you like to know more?';
  } else {
    response = 'Hello! I\'m Agribot, your agricultural assistant. How can I help you today?';
  }
  
  res.json({
    success: true,
    data: {
      message: response,
      timestamp: new Date().toISOString(),
      language: language
    }
  });
});

// GET /api/chat/history - Get chat history
router.get('/history', (req, res) => {
  res.json({
    success: true,
    data: [
      {
        id: 1,
        message: 'Hello, how can I help you?',
        timestamp: new Date().toISOString(),
        type: 'bot'
      }
    ]
  });
});

module.exports = router;



