const express = require('express');
const router = express.Router();

// GET /api/experts - Get available agricultural experts
router.get('/', (req, res) => {
  res.json({
    success: true,
    data: [
      {
        id: 1,
        name: 'Dr. Kwame Asante',
        specialization: 'Crop Science',
        experience: '15 years',
        contact: '+233 20 123 4567',
        availability: 'Mon-Fri 9AM-5PM'
      },
      {
        id: 2,
        name: 'Dr. Ama Osei',
        specialization: 'Soil Science',
        experience: '12 years',
        contact: '+233 24 987 6543',
        availability: 'Mon-Sat 8AM-6PM'
      }
    ]
  });
});

// GET /api/experts/:id - Get specific expert
router.get('/:id', (req, res) => {
  const { id } = req.params;
  res.json({
    success: true,
    data: {
      id: parseInt(id),
      name: 'Dr. Kwame Asante',
      specialization: 'Crop Science',
      experience: '15 years',
      contact: '+233 20 123 4567',
      availability: 'Mon-Fri 9AM-5PM',
      qualifications: 'PhD in Agricultural Sciences',
      languages: ['English', 'Twi', 'Ga']
    }
  });
});

module.exports = router;



