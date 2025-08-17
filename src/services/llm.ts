// LLM Service for Agricultural AI Assistant
// Supports multiple free LLM providers

export interface LLMResponse {
  content: string;
  provider: string;
  model: string;
  timestamp: string;
  tokens?: number;
}

export interface LLMRequest {
  prompt: string;
  language: string;
  context?: string;
  maxTokens?: number;
}

// Hugging Face Inference API (Free Tier)
class HuggingFaceLLM {
  private apiUrl = 'https://api-inference.huggingface.co/models/';
  private apiKey: string;

  constructor() {
    // You can get a free API key from https://huggingface.co/settings/tokens
    this.apiKey = process.env.REACT_APP_HUGGINGFACE_API_KEY || '';
  }

  async generateResponse(request: LLMRequest): Promise<LLMResponse> {
    try {
      const model = 'microsoft/DialoGPT-medium'; // Free model
      
      const response = await fetch(`${this.apiUrl}${model}`, {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${this.apiKey}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          inputs: this.buildPrompt(request),
          parameters: {
            max_length: request.maxTokens || 150,
            temperature: 0.7,
            do_sample: true,
          }
        })
      });

      if (!response.ok) {
        throw new Error(`Hugging Face API error: ${response.status}`);
      }

      const data = await response.json();
      
      return {
        content: this.extractResponse(data),
        provider: 'Hugging Face',
        model: model,
        timestamp: new Date().toISOString(),
      };
    } catch (error) {
      console.error('Hugging Face LLM error:', error);
      throw error;
    }
  }

  private buildPrompt(request: LLMRequest): string {
    const basePrompt = `You are an agricultural assistant for Ghanaian farmers. Respond in ${request.language === 'en' ? 'English' : 'the local language'}. 
    
Context: ${request.context || 'General farming advice'}
Question: ${request.prompt}

Response:`;

    return basePrompt;
  }

  private extractResponse(data: any): string {
    if (data && data[0] && data[0].generated_text) {
      return data[0].generated_text;
    }
    return 'I apologize, but I could not generate a response at this time.';
  }
}

// Local Ollama Integration (Free, runs locally)
class OllamaLLM {
  private baseUrl = 'http://localhost:11434';

  async generateResponse(request: LLMRequest): Promise<LLMResponse> {
    try {
      const response = await fetch(`${this.baseUrl}/api/generate`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          model: 'llama2:7b', // Free local model
          prompt: this.buildPrompt(request),
          stream: false,
          options: {
            temperature: 0.7,
            num_predict: request.maxTokens || 150,
          }
        })
      });

      if (!response.ok) {
        throw new Error(`Ollama API error: ${response.status}`);
      }

      const data = await response.json();
      
      return {
        content: data.response,
        provider: 'Ollama',
        model: 'llama2:7b',
        timestamp: new Date().toISOString(),
        tokens: data.eval_count,
      };
    } catch (error) {
      console.error('Ollama LLM error:', error);
      throw error;
    }
  }

  private buildPrompt(request: LLMRequest): string {
    return `You are an agricultural assistant for Ghanaian farmers. Respond in ${request.language === 'en' ? 'English' : 'the local language'}. 

Context: ${request.context || 'General farming advice'}
Question: ${request.prompt}

Response:`;
  }
}

// Fallback LLM with predefined responses
class FallbackLLM {
  private agriculturalResponses = {
    en: {
      crop: "For crop cultivation in Ghana, consider the rainy season timing. Plant maize between April-June for best yield. Use organic fertilizers and practice crop rotation.",
      pest: "Common pests in Ghana include armyworms and aphids. Use neem oil or consult your local extension officer for safe pest control methods.",
      weather: "Monitor weather patterns closely. The harmattan season affects crop growth significantly. Water your crops regularly during dry spells.",
      market: "Check local market prices regularly. Tomatoes and cassava have good market demand. Consider selling during peak seasons for better prices.",
      default: "I'm here to help with your farming questions. Ask me about crops, pests, weather, or markets in Ghana. For specific advice, contact your local extension officer."
    },
    tw: {
      crop: "Kuayɛ ho nimdeɛ: Kuayɛ bere pa ne osutɔ bere. Aburow dua wɔ Kwapem kɔsi Kuotɔ. Fa organic fertilizer na yɛ crop rotation.",
      pest: "Mmoawa a ɛhaw aduan: Kwatɔ ne aphids. Fa neem ngo anaa kɔ extension officer hɔ ma safe pest control.",
      weather: "Ewiem tebea: Harmattan mmerɛ no ka aduan nyin kwan. Gua wo aduan nsuo regular sɛ ewiem yɛ hyew.",
      market: "Gua so: Ntoses ne bankye wɔ gua pa. Hwɛ sɛ woataa so wɔ peak seasons ma bo pa.",
      default: "Mewɔ ha sɛ meboa wo kuayɛ nsɛm ho. Bisa me aduan, mmoawa, ewiem anaa gua ho nsɛm."
    },
    ee: {
      crop: "Agblẽnɔnɔ ho: Agblẽnɔnɔ ƒe ɣeyiɣi ne tsidzadza ƒe ɣeyiɣi. Ɖe nukuwo le Afɔfi kple Masa me. Zã organic fertilizer eye nàwɔ crop rotation.",
      pest: "Nudzrala siwo le agblẽnɔnɔ me: Armyworms kple aphids. Zã neem ngo alo kɔ extension officer hɔ.",
      weather: "Yame ƒe nɔnɔme: Harmattan ƒe ɣeyiɣi ka nukuwo ƒe nyuie. Na nukuwo nɔ le ɣeyiɣi me.",
      market: "Asi Ƒe ga home: Ntoses kple cassava wɔ asi pa. Hwɛ sɛ woataa so le peak seasons me.",
      default: "Mewɔ ha sɛ meboa wo agblẽnɔnɔ ho. Bisa nuku, nudzrala, yame, alo asi ho nsɛm."
    },
    ga: {
      crop: "Kuayɛ ho nimdeɛ: Kuayɛ bere pa ne osutɔ bere. Aburow dua wɔ Kwapem kɔsi Kuotɔ. Fa organic fertilizer na yɛ crop rotation.",
      pest: "Mmoawa a ɛhaw aduan: Kwatɔ ne aphids. Fa neem ngo anaa kɔ extension officer hɔ ma safe pest control.",
      weather: "Ewiem tebea: Harmattan mmerɛ no ka aduan nyin kwan. Gua wo aduan nsuo regular sɛ ewiem yɛ hyew.",
      market: "Gua so: Ntoses ne bankye wɔ gua pa. Hwɛ sɛ woataa so wɔ peak seasons ma bo pa.",
      default: "Mewɔ ha sɛ meboa wo kuayɛ nsɛm ho. Bisa me aduan, mmoawa, ewiem anaa gua ho nsɛm."
    }
  };

  async generateResponse(request: LLMRequest): Promise<LLMResponse> {
    // Simple keyword matching for fallback responses
    const lowerMessage = request.prompt.toLowerCase();
    const langResponses = this.agriculturalResponses[request.language as keyof typeof this.agriculturalResponses] || this.agriculturalResponses.en;
    
    let response = langResponses.default;
    
    if (lowerMessage.includes('crop') || lowerMessage.includes('plant') || lowerMessage.includes('aduan')) {
      response = langResponses.crop;
    } else if (lowerMessage.includes('pest') || lowerMessage.includes('insect') || lowerMessage.includes('mmoawa')) {
      response = langResponses.pest;
    } else if (lowerMessage.includes('weather') || lowerMessage.includes('rain') || lowerMessage.includes('ewiem')) {
      response = langResponses.weather;
    } else if (lowerMessage.includes('market') || lowerMessage.includes('price') || lowerMessage.includes('gua')) {
      response = langResponses.market;
    }

    return {
      content: response,
      provider: 'Fallback',
      model: 'Agricultural Knowledge Base',
      timestamp: new Date().toISOString(),
    };
  }
}

// Main LLM Service
export class LLMService {
  private providers: {
    huggingface: HuggingFaceLLM;
    ollama: OllamaLLM;
    fallback: FallbackLLM;
  };

  constructor() {
    this.providers = {
      huggingface: new HuggingFaceLLM(),
      ollama: new OllamaLLM(),
      fallback: new FallbackLLM(),
    };
  }

  async generateResponse(request: LLMRequest): Promise<LLMResponse> {
    // Try providers in order of preference
    const providers = ['huggingface', 'ollama', 'fallback'] as const;
    
    for (const providerName of providers) {
      try {
        const provider = this.providers[providerName];
        const response = await provider.generateResponse(request);
        console.log(`Using ${providerName} LLM provider`);
        return response;
      } catch (error) {
        console.warn(`${providerName} LLM failed, trying next provider:`, error);
        continue;
      }
    }

    // If all providers fail, return fallback
    return this.providers.fallback.generateResponse(request);
  }

  // Check if Ollama is available locally
  async checkOllamaAvailability(): Promise<boolean> {
    try {
      const response = await fetch('http://localhost:11434/api/tags', {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' },
      });
      return response.ok;
    } catch {
      return false;
    }
  }

  // Get available models from Ollama
  async getOllamaModels(): Promise<string[]> {
    try {
      const response = await fetch('http://localhost:11434/api/tags');
      const data = await response.json();
      return data.models?.map((model: any) => model.name) || [];
    } catch {
      return [];
    }
  }
}

// Export singleton instance
export const llmService = new LLMService(); 
