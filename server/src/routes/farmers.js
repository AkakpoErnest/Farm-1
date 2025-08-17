const express = require('express');
const router = express.Router();

// GET /api/farmers - Get farmer information
router.get('/', (req, res) => {
  res.json({
    success: true,
    data: [
      {
        id: 1,
        name: 'John Doe',
        location: 'Kumasi',
        farmSize: '5 acres',
        crops: ['Maize', 'Cassava'],
        experience: '10 years'
      },
      {
        id: 2,
        name: 'Jane Smith',
        location: 'Tamale',
        farmSize: '3 acres',
        crops: ['Yam', 'Groundnut'],
        experience: '8 years'
      }
    ]
  });
});

// GET /api/farmers/:id - Get specific farmer
router.get('/:id', (req, res) => {
  const { id } = req.params;
  res.json({
    success: true,
    data: {
      id: parseInt(id),
      name: 'John Doe',
      location: 'Kumasi',
      farmSize: '5 acres',
      crops: ['Maize', 'Cassava'],
      experience: '10 years',
      contact: '+233 20 123 4567',
      registrationDate: '2020-01-15'
    }
  });
});

// POST /api/farmers - Register new farmer
router.post('/', (req, res) => {
  const { name, location, farmSize, crops } = req.body;
  
  res.json({
    success: true,
    data: {
      id: 3,
      name,
      location,
      farmSize,
      crops,
      message: 'Farmer registered successfully'
    }
  });
});

module.exports = router;



