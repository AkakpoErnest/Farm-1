import { GoogleGenerativeAI } from '@google/generative-ai';

// Initialize Gemini AI with your API key
const genAI = new GoogleGenerativeAI('YOUR_GEMINI_API_KEY_HERE'); // Add your key here

export interface AIResponse {
  text: string;
  confidence: number;
  model: string;
  language: string;
}

export class GeminiService {
  private static instance: GeminiService;
  private model: any;
  
  static getInstance(): GeminiService {
    if (!GeminiService.instance) {
      GeminiService.instance = new GeminiService();
    }
    return GeminiService.instance;
  }

  constructor() {
    console.log('🚀 Initializing Gemini service...');
    // Initialize the Gemini 1.5 Flash model (better free tier)
    this.model = genAI.getGenerativeModel({ model: 'gemini-1.5-flash' });
    console.log('✅ Gemini service initialized with model:', this.model);
  }

  // Main chat method
  async chat(message: string, language: string = 'en'): Promise<AIResponse> {
    try {
      console.log('🤖 Gemini chat called with message:', message);
      console.log('🌍 Language:', language);
      
      // Create agricultural context prompt
      const prompt = `You are Agribot, a helpful agricultural assistant for farmers in Ghana. 
You provide expert advice on farming, crop management, weather, market prices, and agricultural best practices.
Always respond in a helpful, friendly manner. If you don't know something, say so honestly.
Focus on practical, actionable advice that Ghanaian farmers can use.
Consider local Ghanaian farming conditions, climate, and available resources.

User Question: ${message}

Please provide a helpful, practical response for Ghanaian farmers:`;

      console.log('📝 Sending prompt to Gemini:', prompt);
      const result = await this.model.generateContent(prompt);
      console.log('📨 Gemini raw result:', result);
      const response = await result.response;
      console.log('📬 Gemini response object:', response);
      const text = response.text();
      console.log('📝 Gemini response text:', text);

      return {
        text: text || 'I apologize, I could not generate a response.',
        confidence: 0.9,
        model: 'gemini-1.5-flash',
        language
      };
    } catch (error) {
      console.error('❌ Gemini chat error:', error);
      return {
        text: 'I apologize, but I am experiencing technical difficulties. Please try again later.',
        confidence: 0.0,
        model: 'error',
        language
      };
    }
  }

  // Question and answer method
  async answerQuestion(question: string, context: string = ''): Promise<AIResponse> {
    try {
      const prompt = `Context: ${context}\n\nQuestion: ${question}\n\nPlease provide a clear, helpful answer:`;
      
      const result = await this.model.generateContent(prompt);
      const response = await result.response;
      const text = response.text();

      return {
        text: text || 'I could not find an answer to that question.',
        confidence: 0.85,
        model: 'gemini-1.5-flash',
        language: 'en'
      };
    } catch (error) {
      console.error('Gemini Q&A error:', error);
      return {
        text: 'I apologize, but I could not process your question at this time.',
        confidence: 0.0,
        model: 'error',
        language: 'en'
      };
    }
  }

  // Agricultural advice method
  async getAgriculturalAdvice(topic: string, crop?: string, region?: string): Promise<AIResponse> {
    try {
      const prompt = `Agricultural Advice Request:
Topic: ${topic}
Crop: ${crop || 'General'}
Region: ${region || 'Ghana'}

Please provide practical, actionable agricultural advice for Ghanaian farmers:`;
      
      const result = await this.model.generateContent(prompt);
      const response = await result.response;
      const text = response.text();

      return {
        text: text || 'I could not generate agricultural advice at this time.',
        confidence: 0.9,
        model: 'gemini-1.5-flash',
        language: 'en'
      };
    } catch (error) {
      console.error('Gemini agricultural advice error:', error);
      return {
        text: 'I apologize, but I could not provide agricultural advice at this time.',
        confidence: 0.0,
        model: 'error',
        language: 'en'
      };
    }
  }

  // Multilingual response method
  async respondInLanguage(message: string, targetLanguage: string): Promise<AIResponse> {
    try {
      const languageNames = {
        'en': 'English',
        'tw': 'Twi (Akan)',
        'ee': 'Ewe',
        'ga': 'Ga'
      };

      const prompt = `Please translate and respond to this message in ${languageNames[targetLanguage as keyof typeof languageNames] || 'English'}:

Original message: ${message}

Please provide a helpful response in the requested language:`;
      
      const result = await this.model.generateContent(prompt);
      const response = await result.response;
      const text = response.text();

      return {
        text: text || 'I could not respond in the requested language.',
        confidence: 0.8,
        model: 'gemini-1.5-flash',
        language: targetLanguage
      };
    } catch (error) {
      console.error('Gemini language response error:', error);
      return {
        text: 'I apologize, but I could not respond in the requested language.',
        confidence: 0.0,
        model: 'error',
        language: targetLanguage
      };
    }
  }

  // Check if service is available
  async isAvailable(): Promise<boolean> {
    try {
      // Simple test to check if the service is working
      const result = await this.model.generateContent('Hello');
      await result.response;
      return true;
    } catch (error) {
      console.error('Gemini availability check failed:', error);
      return false;
    }
  }

  // Get available models
  getAvailableModels(): string[] {
    return ['gemini-1.5-pro', 'gemini-1.5-flash', 'gemini-1.5-pro-latest'];
  }

  // Get model information
  getModelInfo(modelName: string): { name: string; type: string; description: string } | null {
    const models = {
      'gemini-1.5-pro': {
        name: 'Gemini 1.5 Pro',
        type: 'Text Generation',
        description: 'Advanced language model for text generation and conversation'
      },
      'gemini-1.5-flash': {
        name: 'Gemini 1.5 Flash',
        type: 'Fast Text Generation',
        description: 'Fast and efficient text generation model'
      },
      'gemini-1.5-pro-latest': {
        name: 'Gemini 1.5 Pro Latest',
        type: 'Latest Text Generation',
        description: 'Most recent version of the Pro model'
      }
    };
    
    return models[modelName as keyof typeof models] || null;
  }
}

export const geminiService = GeminiService.getInstance();
