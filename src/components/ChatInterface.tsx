import React, { useState, useEffect, useRef } from 'react';
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Card, CardContent } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { ScrollArea } from "@/components/ui/scroll-area";
import { Separator } from "@/components/ui/separator";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { 
  Mic, 
  MicOff, 
  Send, 
  Bot, 
  User, 
  MessageSquare, 
  Phone, 
  Mail,
  Clock,
  Cloud,
  TrendingUp,
  Building2,
  CheckCircle,
  Loader2
} from 'lucide-react';
import { useToast } from "@/hooks/use-toast";
import { useLanguage } from '@/contexts/LanguageContext';

// Speech Recognition types
interface SpeechRecognitionEvent {
  results: SpeechRecognitionResultList;
}

interface SpeechRecognitionErrorEvent {
  error: string;
}

interface SpeechRecognitionResultList {
  [index: number]: SpeechRecognitionResult;
  length: number;
}

interface SpeechRecognitionResult {
  [index: number]: SpeechRecognitionAlternative;
  length: number;
}

interface SpeechRecognitionAlternative {
  transcript: string;
  confidence: number;
}

interface Message {
  id: string;
  content: string;
  sender: 'user' | 'bot' | 'expert';
  timestamp: Date;
  language?: string;
  type?: 'text' | 'weather' | 'market' | 'subsidy' | 'expert-request';
  data?: WeatherData | MarketData[] | SubsidyData[] | Record<string, unknown>;
  provider?: string;
  model?: string;
}

interface WeatherData {
  temperature: number;
  condition: string;
  humidity: number;
  windSpeed: number;
  forecast: string;
}

interface MarketData {
  crop: string;
  price: number;
  unit: string;
  location: string;
  trend: 'up' | 'down' | 'stable';
}

interface SubsidyData {
  program: string;
  description: string;
  eligibility: string;
  deadline: string;
  contact: string;
}

interface ChatInterfaceProps {
  language?: string;
}

export const ChatInterface = ({ language: propLanguage }: ChatInterfaceProps) => {
  const { language: contextLanguage } = useLanguage();
  const language = propLanguage || contextLanguage;
  const [messages, setMessages] = useState<Message[]>([]);
  const [inputMessage, setInputMessage] = useState('');
  const [isRecording, setIsRecording] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [recognition, setRecognition] = useState<unknown>(null);
  const [weatherData, setWeatherData] = useState<WeatherData | null>(null);
  const [marketData, setMarketData] = useState<MarketData[]>([]);
  const [subsidyData, setSubsidyData] = useState<SubsidyData[]>([]);
  const [expertMode, setExpertMode] = useState(false);
  const messagesEndRef = useRef<HTMLDivElement>(null);
  const { toast } = useToast();

  // Initialize speech recognition
  useEffect(() => {
    if ('webkitSpeechRecognition' in window || 'SpeechRecognition' in window) {
      const SpeechRecognition = (window as any).webkitSpeechRecognition || (window as any).SpeechRecognition;
      const recognitionInstance = new SpeechRecognition();
      
      recognitionInstance.continuous = false;
      recognitionInstance.interimResults = false;
      recognitionInstance.lang = getLanguageCode(language);
      
      recognitionInstance.onresult = (event: SpeechRecognitionEvent) => {
        const transcript = event.results[0][0].transcript;
        setInputMessage(transcript);
        setIsRecording(false);
      };
      
      recognitionInstance.onerror = (event: SpeechRecognitionErrorEvent) => {
        console.error('Speech recognition error:', event.error);
        setIsRecording(false);
        toast({
          title: "Speech Recognition Error",
          description: "Could not process your voice. Please try again.",
          variant: "destructive",
        });
      };
      
      recognitionInstance.onend = () => {
        setIsRecording(false);
      };
      
      setRecognition(recognitionInstance);
    }
  }, [language, toast]);

  // Auto-scroll to bottom
  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  }, [messages]);

  // Add welcome message
  useEffect(() => {
    const welcomeMessage = getWelcomeMessage(language);
    setMessages([{
      id: '1',
      content: welcomeMessage,
      sender: 'bot',
      timestamp: new Date()
    }]);
  }, [language]);

  const getLanguageCode = (lang: string) => {
    const codes = {
      'en': 'en-US',
      'tw': 'ak-GH', // Twi fallback to English
      'ee': 'ee-GH', // Ewe fallback to English  
      'ga': 'ga-GH', // Ga fallback to English
    };
    return codes[lang as keyof typeof codes] || 'en-US';
  };

  const getWelcomeMessage = (lang: string) => {
    const messages = {
      'en': 'Hello! I\'m Agribot, your agricultural assistant. How can I help you today?',
      'tw': 'Akwaba! Me yɛ Agribot, wo kuwadwuma boafo. Sɛn na me tumi bo wo?',
      'ee': 'Ndi! Nye Agribot, wo agblẽnɔnɔ boafo. Aleke nàdze nàdze wo?',
      'ga': 'Akwaba! Nye Agribot, wo kuwadwuma boafo. Sɛn na me tumi bo wo?'
    };
    return messages[lang as keyof typeof messages] || messages.en;
  };

  const getQuickQuestions = (language: string) => {
    const questions = {
      'en': [
        'Tell me about crops',
        'How to control pests?',
        'Weather forecast',
        'Market prices',
        'Government subsidies',
        'Connect with expert'
      ],
      'tw': [
        'Ka aduan ho nsɛm',
        'Sɛn na yɛɛ mmoawa?',
        'Ewiem forecast',
        'Gua bo',
        'Amanaman ntam subsidy',
        'Ka extension officer ho'
      ],
      'ee': [
        'Ka agblẽnɔnɔ ŋu nyawo',
        'Aleke nàdze nudzrala?',
        'Yame ƒe forecast',
        'Asi ƒe ga',
        'Dukplɔlawo ƒe subsidy',
        'Ka extension officer gbɔ'
      ],
      'ga': [
        'Ka aduan ho nsɛm',
        'Sɛn na yɛɛ mmoawa?',
        'Ewiem forecast',
        'Gua bo',
        'Amanaman ntam subsidy',
        'Ka extension officer ho'
      ]
    };
    return questions[language as keyof typeof questions] || questions.en;
  };

  // Simulate real-time weather data
  const fetchWeatherData = async (): Promise<WeatherData> => {
    await new Promise(resolve => setTimeout(resolve, 500));
    
    return {
      temperature: 28,
      condition: 'Partly Cloudy',
      humidity: 75,
      windSpeed: 12,
      forecast: 'Light rain expected in the next 24 hours. Good for planting maize and vegetables.'
    };
  };

  // Simulate real-time market data
  const fetchMarketData = async (): Promise<MarketData[]> => {
    await new Promise(resolve => setTimeout(resolve, 500));
    
    return [
      { crop: 'Tomatoes', price: 15.50, unit: 'kg', location: 'Kumasi Market', trend: 'up' },
      { crop: 'Cassava', price: 8.20, unit: 'kg', location: 'Accra Market', trend: 'stable' },
      { crop: 'Maize', price: 12.80, unit: 'kg', location: 'Tamale Market', trend: 'down' },
      { crop: 'Yam', price: 18.90, unit: 'kg', location: 'Kumasi Market', trend: 'up' }
    ];
  };

  // Simulate government subsidy data
  const fetchSubsidyData = async (): Promise<SubsidyData[]> => {
    await new Promise(resolve => setTimeout(resolve, 500));
    
    return [
      {
        program: 'Fertilizer Subsidy Program',
        description: '50% subsidy on NPK and Urea fertilizers for registered farmers',
        eligibility: 'Must be a registered farmer with valid ID',
        deadline: 'December 31, 2024',
        contact: 'Ministry of Food and Agriculture: 0302-665-000'
      },
      {
        program: 'Planting for Food and Jobs',
        description: 'Free seeds and technical support for maize, rice, and vegetable farmers',
        eligibility: 'Smallholder farmers with less than 5 acres',
        deadline: 'Ongoing',
        contact: 'MoFA Extension Services: 0302-665-001'
      }
    ];
  };

  // Helper function to generate weather response
  const getWeatherResponse = (language: string, weatherData: WeatherData): string => {
    const responses = {
      'en': `🌤️ Current Weather: ${weatherData.condition}, ${weatherData.temperature}°C\n💧 Humidity: ${weatherData.humidity}%\n💨 Wind: ${weatherData.windSpeed} km/h\n🌱 Farming Advice: ${weatherData.forecast}`,
      'tw': `🌤️ Ewiem tebea: ${weatherData.condition}, ${weatherData.temperature}°C\n💧 Humidity: ${weatherData.humidity}%\n💨 Mframa: ${weatherData.windSpeed} km/h\n🌱 Kuayɛ afotu: ${weatherData.forecast}`,
      'ee': `🌤️ Yame ƒe nɔnɔme: ${weatherData.condition}, ${weatherData.temperature}°C\n💧 Nɔ ƒe nɔnɔme: ${weatherData.humidity}%\n💨 Mframa: ${weatherData.windSpeed} km/h\n🌱 Agblẽnɔnɔ ɖoɖo: ${weatherData.forecast}`,
      'ga': `🌤️ Ewiem tebea: ${weatherData.condition}, ${weatherData.temperature}°C\n💧 Humidity: ${weatherData.humidity}%\n💨 Mframa: ${weatherData.windSpeed} km/h\n🌱 Kuayɛ afotu: ${weatherData.forecast}`
    };
    return responses[language as keyof typeof responses] || responses.en;
  };

  // Helper function to generate market response
  const getMarketResponse = (language: string, marketData: MarketData[]): string => {
    const marketList = marketData.map(item => 
      `${item.crop}: ${item.price} GHS/${item.unit} (${item.location}) ${item.trend === 'up' ? '📈' : item.trend === 'down' ? '📉' : '➡️'}`
    ).join('\n');
    
    const responses = {
      'en': `📊 Current Market Prices:\n${marketList}\n\n💡 Tip: Prices are updated daily. Check local markets for the most current rates.`,
      'tw': `📊 Gua bo a ɛrekɔ so:\n${marketList}\n\n💡 Afotu: Bo sesa daa. Kɔ wo amantɔ gua hɔ na wo nya bo pa.`,
      'ee': `📊 Asi ƒe ga home:\n${marketList}\n\n💡 ɖoɖo: Ga sesa daa. Kɔ wò nutɔwo ƒe asi hã eye nàxɔ ga nyuie.`,
      'ga': `📊 Gua bo a ɛrekɔ so:\n${marketList}\n\n💡 Afotu: Bo sesa daa. Kɔ wo amantɔ gua hɔ na wo nya bo pa.`
    };
    return responses[language as keyof typeof responses] || responses.en;
  };

  // Helper function to generate subsidy response
  const getSubsidyResponse = (language: string, subsidyData: SubsidyData[]): string => {
    const subsidyList = subsidyData.map(item => 
      `🏛️ ${item.program}\n📝 ${item.description}\n✅ Eligibility: ${item.eligibility}\n⏰ Deadline: ${item.deadline}\n📞 Contact: ${item.contact}`
    ).join('\n\n');
    
    const responses = {
      'en': `🏛️ Government Agricultural Programs:\n\n${subsidyList}\n\n💡 Apply early as programs have limited slots.`,
      'tw': `🏛️ Amanaman ntam kuayɛ program:\n\n${subsidyList}\n\n💡 Sɔ wo application ntɛm efisɛ program wɔ slot kakra.`,
      'ee': `🏛️ Dukplɔlawo ƒe agblẽnɔnɔ programwo:\n\n${subsidyList}\n\n💡 Sɔ wò application enumake efisɛ programwo le slot kakra.`,
      'ga': `🏛️ Amanaman ntam kuayɛ program:\n\n${subsidyList}\n\n💡 Sɔ wo application ntɛm efisɛ program wɔ slot kakra.`
    };
    return responses[language as keyof typeof responses] || responses.en;
  };

  const getExpertConnectionMessage = () => {
    const messages = {
      'en': 'I\'ve connected you with an agricultural extension officer. Here are the contact details:',
      'tw': 'Meka wo extension officer ho. Hena contact details:',
      'ee': 'Meka wò extension officer gbɔ. Ame contact details:',
      'ga': 'Meka wo extension officer ho. Hena contact details:'
    };
    return messages[language as keyof typeof messages] || messages.en;
  };

  const sendMessage = async () => {
    if (!inputMessage.trim()) return;

    const userMessage: Message = {
      id: Date.now().toString(),
      content: inputMessage,
      sender: 'user',
      timestamp: new Date(),
      language
    };

    setMessages(prev => [...prev, userMessage]);
    setInputMessage('');
    setIsLoading(true);

    try {
      // Check if this is an expert connection request
      const lowerMessage = inputMessage.toLowerCase();
      const isExpertRequest = lowerMessage.includes('expert') || 
                             lowerMessage.includes('extension') || 
                             lowerMessage.includes('officer') ||
                             lowerMessage.includes('connect') ||
                             lowerMessage.includes('ka extension officer ho') ||
                             lowerMessage.includes('ka extension officer gbɔ');

      if (isExpertRequest) {
        // Handle expert connection request
        const expertMessage: Message = {
          id: (Date.now() + 1).toString(),
          content: getExpertConnectionMessage(),
          sender: 'bot',
          timestamp: new Date(),
          language,
          type: 'expert-request',
          data: {
            expertName: 'Kwame Asante',
            expertPhone: '+233 24 123 4567',
            expertEmail: 'kwame.asante@agric.gov.gh',
            responseTime: '24 hours',
            requestId: `EXP-${Date.now()}`
          }
        };

        setMessages(prev => [...prev, expertMessage]);
      }
      // Check for weather-related queries
      else if (lowerMessage.includes('weather') || lowerMessage.includes('ewiem') || lowerMessage.includes('yame') || lowerMessage.includes('forecast')) {
        const weatherData = await fetchWeatherData();
        const weatherResponse = getWeatherResponse(language, weatherData);
        
        const botMessage: Message = {
          id: (Date.now() + 1).toString(),
          content: weatherResponse,
          sender: 'bot',
          timestamp: new Date(),
          language,
          type: 'weather',
          data: weatherData
        };

        setMessages(prev => [...prev, botMessage]);
      }
      // Check for market-related queries
      else if (lowerMessage.includes('market') || lowerMessage.includes('price') || lowerMessage.includes('gua') || lowerMessage.includes('asi')) {
        const marketData = await fetchMarketData();
        const marketResponse = getMarketResponse(language, marketData);
        
        const botMessage: Message = {
          id: (Date.now() + 1).toString(),
          content: marketResponse,
          sender: 'bot',
          timestamp: new Date(),
          language,
          type: 'market',
          data: marketData
        };

        setMessages(prev => [...prev, botMessage]);
      }
      // Check for subsidy-related queries
      else if (lowerMessage.includes('subsidy') || lowerMessage.includes('government') || lowerMessage.includes('amanaman')) {
        const subsidyData = await fetchSubsidyData();
        const subsidyResponse = getSubsidyResponse(language, subsidyData);
        
        const botMessage: Message = {
          id: (Date.now() + 1).toString(),
          content: subsidyResponse,
          sender: 'bot',
          timestamp: new Date(),
          language,
          type: 'subsidy',
          data: subsidyData
        };

        setMessages(prev => [...prev, botMessage]);
      }
      // Default response for other queries
      else {
        const responses = {
          'en': [
            'I can help you with farming advice, crop management, and agricultural best practices.',
            'For pest control, consider using integrated pest management techniques.',
            'Weather conditions are important for crop planning. Check local forecasts regularly.',
            'Market prices vary by season and location. I recommend checking local markets.',
            'Government subsidies are available for various agricultural programs.',
            'I can connect you with agricultural extension officers in your area.'
          ],
          'tw': [
            'Me tumi bo wo kuwadwuma advice, aduan management, ne agricultural best practices.',
            'Sɛn na yɛɛ mmoawa, hwɛ integrated pest management techniques.',
            'Ewiem conditions yɛ important ma aduan planning. Hwɛ local forecasts daa.',
            'Gua bo yɛ different by season ne location. Me recommend wo hwɛ local markets.',
            'Amanaman ntam subsidies wɔ ma various agricultural programs.',
            'Me tumi connect wo ne agricultural extension officers wo wo area.'
          ],
          'ee': [
            'Nye tumi bo wo agblẽnɔnɔ advice, aduan management, ne agricultural best practices.',
            'Aleke nàdze nudzrala, hwɛ integrated pest management techniques.',
            'Yame ƒe conditions yɛ important ma aduan planning. Hwɛ local forecasts daa.',
            'Asi ƒe ga yɛ different by season ne location. Nye recommend wo hwɛ local markets.',
            'Dukplɔlawo ƒe subsidies wɔ ma various agricultural programs.',
            'Nye tumi connect wo ne agricultural extension officers wo wo area.'
          ],
          'ga': [
            'Nye tumi bo wo kuwadwuma advice, aduan management, ne agricultural best practices.',
            'Sɛn na yɛɛ mmoawa, hwɛ integrated pest management techniques.',
            'Ewiem conditions yɛ important ma aduan planning. Hwɛ local forecasts daa.',
            'Gua bo yɛ different by season ne location. Me recommend wo hwɛ local markets.',
            'Amanaman ntam subsidies wɔ ma various agricultural programs.',
            'Me tumi connect wo ne agricultural extension officers wo wo area.'
          ]
        };
        
        const langResponses = responses[language as keyof typeof responses] || responses.en;
        const randomIndex = Math.floor(Math.random() * langResponses.length);
        const response = langResponses[randomIndex];
        
        const botMessage: Message = {
          id: (Date.now() + 1).toString(),
          content: response,
          sender: 'bot',
          timestamp: new Date(),
          language
        };

        setMessages(prev => [...prev, botMessage]);
      }
    } catch (error) {
      console.error('Error generating response:', error);
      toast({
        title: "Error",
        description: "Failed to get response. Please try again.",
        variant: "destructive",
      });
    } finally {
      setIsLoading(false);
    }
  };

  const handleKeyPress = (e: React.KeyboardEvent) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault();
      sendMessage();
    }
  };

  const handleQuickQuestion = (question: string) => {
    setInputMessage(question);
  };

  const toggleRecording = () => {
    if (!recognition) {
      toast({
        title: "Not Supported",
        description: "Speech recognition is not supported in this browser.",
        variant: "destructive",
      });
      return;
    }

    if (isRecording) {
      (recognition as { stop: () => void }).stop();
      setIsRecording(false);
    } else {
      (recognition as { start: () => void; lang: string }).lang = getLanguageCode(language);
      (recognition as { start: () => void }).start();
      setIsRecording(true);
    }
  };

  return (
    <div className="w-full max-w-4xl mx-auto">
      <Card className="shadow-lg">
        <CardContent className="p-6">
          {/* Chat Header */}
          <div className="flex items-center gap-3 mb-6">
            <Avatar className="h-10 w-10">
              <AvatarImage src="/agribot-logo.png" alt="Agribot" />
              <AvatarFallback className="bg-green-100 text-green-800">
                <Bot className="h-5 w-5" />
              </AvatarFallback>
            </Avatar>
            <div>
              <h3 className="text-lg font-semibold text-gray-900">
                {language === 'en' ? 'Chat with Agribot' : 
                 language === 'tw' ? 'Ka Agribot ho kasa' :
                 language === 'ee' ? 'Ka Agribot gbɔ kpɔ' :
                 'Ka Agribot ho kasa'}
              </h3>
              <p className="text-sm text-gray-600">
                {language === 'en' ? 'Your AI agricultural assistant' :
                 language === 'tw' ? 'Wo AI kuwadwuma boafo' :
                 language === 'ee' ? 'Wo AI agblẽnɔnɔ boafo' :
                 'Wo AI kuwadwuma boafo'}
              </p>
            </div>
          </div>

          {/* Messages */}
          <ScrollArea className="h-96 mb-4">
            <div className="space-y-4">
              {messages.map((message) => (
                <div
                  key={message.id}
                  className={`flex gap-3 ${
                    message.sender === 'user' ? 'justify-end' : 'justify-start'
                  }`}
                >
                  {message.sender === 'bot' && (
                    <Avatar className="h-8 w-8">
                      <AvatarImage src="/agribot-logo.png" alt="Agribot" />
                      <AvatarFallback className="bg-green-100 text-green-800">
                        <Bot className="h-4 w-4" />
                      </AvatarFallback>
                    </Avatar>
                  )}
                  <div
                    className={`max-w-xs lg:max-w-md px-4 py-2 rounded-lg ${
                      message.sender === 'user'
                        ? 'bg-blue-500 text-white'
                        : 'bg-gray-100 text-gray-900'
                    }`}
                  >
                    <p className="text-sm">{message.content}</p>
                    
                    {/* Weather Data Display */}
                    {message.type === 'weather' && message.data && 'temperature' in message.data && (
                      <div className="mt-2 p-3 bg-blue-50 rounded-lg">
                        <div className="flex items-center gap-2 text-blue-700">
                          <Cloud className="h-4 w-4" />
                          <span className="font-medium">Weather Data</span>
                        </div>
                        <div className="mt-1 text-sm text-blue-600">
                          Temperature: {(message.data as WeatherData).temperature}°C<br />
                          Condition: {(message.data as WeatherData).condition}<br />
                          Humidity: {(message.data as WeatherData).humidity}%<br />
                          Wind: {(message.data as WeatherData).windSpeed} km/h<br />
                          Forecast: {(message.data as WeatherData).forecast}
                        </div>
                      </div>
                    )}

                    {/* Market Data Display */}
                    {message.type === 'market' && message.data && Array.isArray(message.data) && (
                      <div className="mt-2 p-3 bg-green-50 rounded-lg">
                        <div className="flex items-center gap-2 text-green-700">
                          <TrendingUp className="h-4 w-4" />
                          <span className="font-medium">Market Prices</span>
                        </div>
                        <div className="mt-1 text-sm text-green-600">
                          {(message.data as MarketData[]).map((item, index) => (
                            <div key={index}>
                              {item.crop}: {item.price} GHS/{item.unit} ({item.location})
                            </div>
                          ))}
                        </div>
                      </div>
                    )}

                    {/* Subsidy Data Display */}
                    {message.type === 'subsidy' && message.data && Array.isArray(message.data) && (
                      <div className="mt-2 p-3 bg-purple-50 rounded-lg">
                        <div className="flex items-center gap-2 text-purple-700">
                          <Building2 className="h-4 w-4" />
                          <span className="font-medium">Government Programs</span>
                        </div>
                        <div className="mt-1 text-sm text-purple-600">
                          {(message.data as SubsidyData[]).map((item, index) => (
                            <div key={index} className="mb-2">
                              <strong>{item.program}</strong><br />
                              {item.description}<br />
                              Contact: {item.contact}
                            </div>
                          ))}
                        </div>
                      </div>
                    )}

                    {/* Expert Request Display */}
                    {message.type === 'expert-request' && message.data && (
                      <div className="mt-2 p-3 bg-orange-50 rounded-lg">
                        <div className="flex items-center gap-2 text-orange-700">
                          <CheckCircle className="h-4 w-4" />
                          <span className="font-medium">Expert Connection</span>
                        </div>
                        <div className="mt-1 text-sm text-orange-600">
                          Expert: {String((message.data as Record<string, unknown>).expertName)}<br />
                          Phone: {String((message.data as Record<string, unknown>).expertPhone)}<br />
                          Email: {String((message.data as Record<string, unknown>).expertEmail)}<br />
                          Response Time: {String((message.data as Record<string, unknown>).responseTime)}
                        </div>
                      </div>
                    )}

                    <p className="text-xs opacity-70 mt-1">
                      {message.timestamp.toLocaleTimeString()}
                    </p>
                  </div>
                  {message.sender === 'user' && (
                    <Avatar className="h-8 w-8">
                      <AvatarFallback className="bg-blue-100 text-blue-800">
                        <User className="h-4 w-4" />
                      </AvatarFallback>
                    </Avatar>
                  )}
                </div>
              ))}
              {isLoading && (
                <div className="flex gap-3 justify-start">
                  <Avatar className="h-8 w-8">
                    <AvatarImage src="/agribot-logo.png" alt="Agribot" />
                    <AvatarFallback className="bg-green-100 text-green-800">
                      <Bot className="h-4 w-4" />
                    </AvatarFallback>
                  </Avatar>
                  <div className="bg-gray-100 text-gray-900 px-4 py-2 rounded-lg">
                    <div className="flex items-center gap-2">
                      <Loader2 className="h-4 w-4 animate-spin" />
                      <span className="text-sm">
                        {language === 'en' ? 'Thinking...' :
                         language === 'tw' ? 'Yɛ adwene...' :
                         language === 'ee' ? 'Yɛ adwene...' :
                         'Yɛ adwene...'}
                      </span>
                    </div>
                  </div>
                </div>
              )}
              <div ref={messagesEndRef} />
            </div>
          </ScrollArea>

          <Separator className="my-4" />

          {/* Input Area */}
          <div className="flex gap-2">
            <Button
              variant={isRecording ? "destructive" : "outline"}
              size="sm"
              onClick={toggleRecording}
              className="shrink-0"
            >
              {isRecording ? (
                <MicOff className="h-4 w-4" />
              ) : (
                <Mic className="h-4 w-4" />
              )}
            </Button>
            <Input
              value={inputMessage}
              onChange={(e) => setInputMessage(e.target.value)}
              onKeyPress={handleKeyPress}
              placeholder={
                language === 'en' ? 'Type your message or use voice...' :
                language === 'tw' ? 'Kyerɛw wo nkra anaa fa nne...' :
                language === 'ee' ? 'Ŋlɔ wò nya alo zã gbe...' :
                language === 'ga' ? 'Ŋmɛ wo nyɛ kɛ zã gbe...' : 'Type your message...'
              }
              disabled={isLoading}
              className="flex-1"
            />
            <Button
              onClick={sendMessage}
              disabled={!inputMessage.trim() || isLoading}
              className="bg-green-600 hover:bg-green-700"
            >
              <Send className="h-4 w-4" />
            </Button>
          </div>

          {/* Quick Questions - Positioned below input for easy access */}
          <div className="mt-3">
            <p className="text-sm text-gray-600 mb-2">
              {language === 'en' ? 'Quick questions:' :
               language === 'tw' ? 'Quick questions:' :
               language === 'ee' ? 'Quick questions:' :
               'Quick questions:'}
            </p>
            <div className="flex flex-wrap gap-2">
              {getQuickQuestions(language).map((question, index) => (
                <Button
                  key={index}
                  variant="outline"
                  size="sm"
                  onClick={() => handleQuickQuestion(question)}
                  className="text-xs hover:bg-green-50 hover:border-green-300"
                >
                  {question}
                </Button>
              ))}
            </div>
          </div>
        </CardContent>
      </Card>
    </div>
  );
};
