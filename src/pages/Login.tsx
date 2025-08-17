import { LoginForm } from "@/components/auth/LoginForm";
import { Button } from "@/components/ui/button";
import { ArrowLeft, Bot, Globe, Mic, Shield } from 'lucide-react';
import { Link, useSearchParams } from 'react-router-dom';
import agribotLogo from "/agribot-logo.png";

const Login = () => {
  const [searchParams] = useSearchParams();
  const selectedLanguage = searchParams.get('lang') || 'en';

  // Multilingual text content
  const getText = (key: string) => {
    const texts = {
      welcomeToAgribot: {
        en: 'Welcome to Agribot',
        tw: 'Akwaaba Agribot',
        ee: 'Woezɔ Agribot',
        ga: 'Akwaaba Agribot'
      },
      tagline: {
        en: 'Your AI-powered agricultural assistant for Ghana',
        tw: 'Wo AI-powered agricultural assistant ma Ghana',
        ee: 'Wò AI-powered agricultural assistant na Ghana',
        ga: 'Wo AI-powered agricultural assistant ma Ghana'
      },
      aiAssistant: {
        en: 'AI Agricultural Assistant',
        tw: 'AI Agricultural Assistant',
        ee: 'AI Agricultural Assistant',
        ga: 'AI Agricultural Assistant'
      },
      aiAssistantDesc: {
        en: 'Get instant answers about crops, pests, and farming techniques',
        tw: 'Fa answers ntɛmɛmɛ wɔ crops, pests, ne farming techniques ho',
        ee: 'Kpɔ answers kpɔkpɔ wɔ crops, pests, kple farming techniques gbɔ',
        ga: 'Fa answers ntɛmɛmɛ wɔ crops, pests, ne farming techniques ho'
      },
      voiceCommunication: {
        en: 'Voice Communication',
        tw: 'Voice Communication',
        ee: 'Voice Communication',
        ga: 'Voice Communication'
      },
      voiceCommunicationDesc: {
        en: 'Speak in your local language and get responses',
        tw: 'Kasa wo local language mu na fa responses',
        ee: 'Dɔ gbe wò local language me eye wòa nya responses',
        ga: 'Kasa wo local language mu na fa responses'
      },
      multiLanguageSupport: {
        en: 'Multi-language Support',
        tw: 'Multi-language Support',
        ee: 'Multi-language Support',
        ga: 'Multi-language Support'
      },
      multiLanguageSupportDesc: {
        en: 'Available in Twi, Ewe, Ga, and English',
        tw: 'Wɔ Twi, Ewe, Ga, ne English mu',
        ee: 'Le Twi, Ewe, Ga, kple English me',
        ga: 'Wɔ Twi, Ewe, Ga, ne English mu'
      },
      securePrivate: {
        en: 'Secure & Private',
        tw: 'Secure & Private',
        ee: 'Secure & Private',
        ga: 'Secure & Private'
      },
      securePrivateDesc: {
        en: 'Your data is protected with industry-standard security',
        tw: 'Wo data yɛ protected wɔ industry-standard security ho',
        ee: 'Wò data le protected wɔ industry-standard security gbɔ',
        ga: 'Wo data yɛ protected wɔ industry-standard security ho'
      },
      joinCommunity: {
        en: 'Join Our Community',
        tw: 'Ka Yen Community Ho',
        ee: 'Ka Mía Community Gbɔ',
        ga: 'Ka Yen Community Ho'
      },
      joinCommunityDesc: {
        en: 'Connect with farmers, customers, and agricultural experts across Ghana',
        tw: 'Ka farmers, customers, ne agricultural experts ho wɔ Ghana nyinaa',
        ee: 'Ka farmers, customers, kple agricultural experts gbɔ wɔ Ghana katã',
        ga: 'Ka farmers, customers, ne agricultural experts ho wɔ Ghana nyinaa'
      },
      backToHome: {
        en: 'Back to Home',
        tw: 'San Kɔ Fie',
        ee: 'Trɔ Kɔ Fie',
        ga: 'San Kɔ Fie'
      },
      termsPrivacy: {
        en: 'By signing in, you agree to our Terms of Service and Privacy Policy',
        tw: 'Sɛ wo sign in a, wo pene yen Terms of Service ne Privacy Policy',
        ee: 'Ne wò sign in la, wò pene mía Terms of Service kple Privacy Policy',
        ga: 'Sɛ wo sign in a, wo pene yen Terms of Service ne Privacy Policy'
      },
      madeForGhana: {
        en: 'Made for Ghana',
        tw: 'Yɛ Ma Ghana',
        ee: 'Wɔ Na Ghana',
        ga: 'Yɛ Ma Ghana'
      },
      localSupport: {
        en: 'Local Support',
        tw: 'Local Support',
        ee: 'Local Support',
        ga: 'Local Support'
      },
      aiPowered: {
        en: 'AI Powered',
        tw: 'AI Powered',
        ee: 'AI Powered',
        ga: 'AI Powered'
      }
    };
    
    const textGroup = texts[key as keyof typeof texts];
    return textGroup?.[selectedLanguage as keyof typeof textGroup] || textGroup?.en || key;
  };

  return (
    <div className="min-h-screen bg-gradient-earth flex">
      {/* Left Side - Features */}
      <div className="hidden lg:flex lg:w-1/2 bg-gradient-primary p-8 text-primary-foreground">
        <div className="max-w-md mx-auto space-y-8">
          <div className="space-y-4">
            <div className="flex justify-center mb-4">
              <img src={agribotLogo} alt="Agribot Logo" className="h-16 w-auto bg-white/10 rounded-lg p-2" />
            </div>
            <h1 className="text-4xl font-bold">{getText('welcomeToAgribot')}</h1>
            <p className="text-lg text-primary-foreground/90">
              {getText('tagline')}
            </p>
          </div>

          <div className="space-y-6">
            <div className="flex items-start gap-4">
              <div className="p-3 bg-primary-foreground/20 rounded-lg">
                <Bot className="h-6 w-6" />
              </div>
              <div>
                <h3 className="font-semibold mb-2">{getText('aiAssistant')}</h3>
                <p className="text-sm text-primary-foreground/80">
                  {getText('aiAssistantDesc')}
                </p>
              </div>
            </div>

            <div className="flex items-start gap-4">
              <div className="p-3 bg-primary-foreground/20 rounded-lg">
                <Mic className="h-6 w-6" />
              </div>
              <div>
                <h3 className="font-semibold mb-2">{getText('voiceCommunication')}</h3>
                <p className="text-sm text-primary-foreground/80">
                  {getText('voiceCommunicationDesc')}
                </p>
              </div>
            </div>

            <div className="flex items-start gap-4">
              <div className="p-3 bg-primary-foreground/20 rounded-lg">
                <Globe className="h-6 w-6" />
              </div>
              <div>
                <h3 className="font-semibold mb-2">{getText('multiLanguageSupport')}</h3>
                <p className="text-sm text-primary-foreground/80">
                  {getText('multiLanguageSupportDesc')}
                </p>
              </div>
            </div>

            <div className="flex items-start gap-4">
              <div className="p-3 bg-primary-foreground/20 rounded-lg">
                <Shield className="h-6 w-6" />
              </div>
              <div>
                <h3 className="font-semibold mb-2">{getText('securePrivate')}</h3>
                <p className="text-sm text-primary-foreground/80">
                  {getText('securePrivateDesc')}
                </p>
              </div>
            </div>
          </div>

          <div className="pt-8">
            <div className="bg-primary-foreground/10 rounded-lg p-4">
              <h4 className="font-semibold mb-2">{getText('joinCommunity')}</h4>
              <p className="text-sm text-primary-foreground/80">
                {getText('joinCommunityDesc')}
              </p>
            </div>
          </div>
        </div>
      </div>

      {/* Right Side - Login Form */}
      <div className="flex-1 flex items-center justify-center p-8">
        <div className="w-full max-w-md space-y-6">
          {/* Back to Home */}
          <div className="flex items-center justify-between">
            <Link to={`/?lang=${selectedLanguage}`}>
              <Button variant="ghost" size="sm" className="gap-2">
                <ArrowLeft className="h-4 w-4" />
                {getText('backToHome')}
              </Button>
            </Link>
          </div>

          {/* Login Form */}
          <LoginForm onSwitchToRegister={() => {}} language={selectedLanguage} />

          {/* Footer */}
          <div className="text-center space-y-4">
            <p className="text-sm text-muted-foreground">
              {getText('termsPrivacy')}
            </p>
            
            <div className="flex items-center justify-center gap-4 text-xs text-muted-foreground">
              <span>🌾 {getText('madeForGhana')}</span>
              <span>•</span>
              <span>🇬🇭 {getText('localSupport')}</span>
              <span>•</span>
              <span>🤖 {getText('aiPowered')}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login; 