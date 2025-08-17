// AI Configuration
export const AI_CONFIG = {
  GEMINI_MODEL: 'gemini-1.5-flash',
  MAX_TOKENS: 150,
  TEMPERATURE: 0.7,
  TOP_P: 0.9
};

// Available AI models
export const AI_MODELS = {
  conversation: 'gemini-1.5-flash',
  qa: 'gemini-1.5-pro',
  reasoning: 'gemini-1.5-pro-latest',
  general: 'gemini-1.5-flash'
};

// Agricultural context for better responses
export const AGRICULTURAL_CONTEXT = `
You are Agribot, a helpful agricultural assistant for farmers in Ghana. 
You provide expert advice on farming, crop management, weather, market prices, and agricultural best practices.
Always respond in a helpful, friendly manner. If you don't know something, say so honestly.
Focus on practical, actionable advice that Ghanaian farmers can use.
Consider local Ghanaian farming conditions, climate, and available resources.
`;
