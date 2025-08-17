const express = require('express');
const router = express.Router();

// GET /api/subsidies - Get available subsidies
router.get('/', (req, res) => {
  res.json({
    success: true,
    data: [
      {
        id: 1,
        name: 'Fertilizer Subsidy',
        description: 'Government subsidy for fertilizer purchase',
        amount: '50% discount',
        eligibility: 'Registered farmers',
        deadline: '2024-12-31'
      },
      {
        id: 2,
        name: 'Seed Subsidy',
        description: 'Subsidy for certified seeds',
        amount: '30% discount',
        eligibility: 'Small-scale farmers',
        deadline: '2024-11-30'
      }
    ]
  });
});

// GET /api/subsidies/:id - Get specific subsidy
router.get('/:id', (req, res) => {
  const { id } = req.params;
  res.json({
    success: true,
    data: {
      id: parseInt(id),
      name: 'Fertilizer Subsidy',
      description: 'Government subsidy for fertilizer purchase',
      amount: '50% discount',
      eligibility: 'Registered farmers',
      deadline: '2024-12-31',
      applicationProcess: 'Apply through local agricultural office'
    }
  });
});

module.exports = router;



