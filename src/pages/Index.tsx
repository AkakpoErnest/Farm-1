import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { LanguageSelector } from "@/components/LanguageSelector";
import { VoiceRecorder } from "@/components/VoiceRecorder";
import { ChatInterface } from "@/components/ChatInterface";
import { FeatureShowcase } from "@/components/FeatureShowcase";
import { TeamSection } from "@/components/TeamSection";
import { Bot, Zap, Languages, User, LogOut, Settings } from 'lucide-react';
import { Link } from 'react-router-dom';
import { useAuth } from '@/contexts/AuthContext';
import { useLanguage } from '@/contexts/LanguageContext';
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Badge } from "@/components/ui/badge";
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuTrigger } from "@/components/ui/dropdown-menu";
import heroImage from "@/assets/agribot-hero.jpg";
import agribotLogo from "/agribot-logo.png";

const Index = () => {
  const { user, isAuthenticated, logout } = useAuth();
  const { t } = useLanguage();

  const getRoleInfo = (role: string) => {
    const roleInfo = {
      farmer: { icon: '🌾', title: 'Farmer', color: 'bg-green-100 text-green-800' },
      customer: { icon: '🛒', title: 'Customer', color: 'bg-blue-100 text-blue-800' },
      expert: { icon: '👨‍🌾', title: 'Expert', color: 'bg-purple-100 text-purple-800' }
    };
    return roleInfo[role as keyof typeof roleInfo] || roleInfo.farmer;
  };

  // Multilingual text content
  const getText = (key: string) => {
    const texts = {
      signIn: {
        en: 'Sign In',
        tw: 'Sign In',
        ee: 'Sign In',
        ga: 'Sign In'
      },
      myProfile: {
        en: 'My Profile',
        tw: 'Me Profile',
        ee: 'Nye Profile',
        ga: 'Me Profile'
      },
      settings: {
        en: 'Settings',
        tw: 'Settings',
        ee: 'Settings',
        ga: 'Settings'
      },
      logout: {
        en: 'Logout',
        tw: 'Logout',
        ee: 'Logout',
        ga: 'Logout'
      },
      heroTitle: {
        en: 'Agribot',
        tw: 'Agribot',
        ee: 'Agribot',
        ga: 'Agribot'
      },
      heroSubtitle: {
        en: "Bridging Communication in Ghana's Agriculture",
        tw: 'Ka Communication Ho Wɔ Ghana Kuayɛ Mu',
        ee: 'Ka Communication Gbɔ Wɔ Ghana Agblẽnɔnɔ Me',
        ga: 'Ka Communication Ho Wɔ Ghana Kuayɛ Mu'
      },
      heroDescription: {
        en: 'Connect farmers, extension officers, and customers through multilingual voice communication in Twi, Ewe, Ga, and English',
        tw: 'Ka farmers, extension officers, ne customers ho wɔ multilingual voice communication mu wɔ Twi, Ewe, Ga, ne English',
        ee: 'Ka farmers, extension officers, kple customers gbɔ wɔ multilingual voice communication me wɔ Twi, Ewe, Ga, kple English',
        ga: 'Ka farmers, extension officers, ne customers ho wɔ multilingual voice communication mu wɔ Twi, Ewe, Ga, ne English'
      },
      welcomeBack: {
        en: 'Welcome back, {name}! Ready to continue your agricultural journey?',
        tw: 'Akwaaba bio, {name}! Wo ready na wo continue wo agricultural journey?',
        ee: 'Woezɔ bio, {name}! Wò ready na wò continue wò agricultural journey?',
        ga: 'Akwaaba bio, {name}! Wo ready na wo continue wo agricultural journey?'
      },
      startAiChat: {
        en: 'Start AI Chat',
        tw: 'Fi AI Nkɔmmɔ Ase',
        ee: 'Dze AI Nubiabia Gɔme',
        ga: 'Fi AI Nkɔmmɔ Ase'
      },
      chooseLanguage: {
        en: 'Choose Your Language',
        tw: 'Paw Wo Kasa',
        ee: 'Tia Wò Gbe',
        ga: 'Paw Wo Kasa'
      },
      chooseLanguageDesc: {
        en: 'Select your preferred language to enhance your experience',
        tw: 'Paw wo kasa a wo pɛ na ma wo adwuma yɛ wo',
        ee: 'Tia wò gbe si wòlɔ̃ eye nàwɔ wò dɔwɔwɔ',
        ga: 'Paw wo kasa a wo pɛ na ma wo adwuma yɛ wo'
      },
      chatWithAgribot: {
        en: 'Chat with Agribot',
        tw: 'Di Nkɔmmɔ Yɛ Agribot',
        ee: 'Dze Nubiabia Yɛ Agribot',
        ga: 'Di Nkɔmmɔ Yɛ Agribot'
      },
      chatWithAgribotDesc: {
        en: 'Start chatting with our AI agricultural assistant',
        tw: 'Fi nkɔmmɔ ase yɛ yɛn AI kuayɛ boafoɔ',
        ee: 'Dze nubiabia gɔme yɛ mía AI agblẽnɔnɔ kpeɖeŋutɔ',
        ga: 'Fi nkɔmmɔ ase yɛ yɛn AI kuayɛ boafoɔ'
      },
      features: {
        en: 'Features',
        tw: 'Features',
        ee: 'Features',
        ga: 'Features'
      },
      featuresDesc: {
        en: 'Discover the powerful features of Agribot',
        tw: 'Hu Agribot features a ɛyɛ den',
        ee: 'Kpɔ Agribot features siwo le dzen',
        ga: 'Hu Agribot features a ɛyɛ den'
      },
      newsUpdates: {
        en: 'News & Updates',
        tw: 'News & Updates',
        ee: 'News & Updates',
        ga: 'News & Updates'
      },
      newsUpdatesDesc: {
        en: 'Stay updated with the latest agricultural news and market information',
        tw: 'Rest wo updated wɔ agricultural news ne market information a ɛfata',
        ee: 'Dze wò updated wɔ agricultural news kple market information siwo le gbɔgbɔ',
        ga: 'Rest wo updated wɔ agricultural news ne market information a ɛfata'
      },
      team: {
        en: 'Our Team',
        tw: 'Yen Team',
        ee: 'Mía Team',
        ga: 'Yen Team'
      },
      teamDesc: {
        en: 'Meet the developers and designers behind Agribot',
        tw: 'Hu developers ne designers a ɛwɔ Agribot akyi',
        ee: 'Kpɔ developers kple designers siwo le Agribot megbe',
        ga: 'Hu developers ne designers a ɛwɔ Agribot akyi'
      },
      statistics: {
        en: 'Statistics',
        tw: 'Statistics',
        ee: 'Statistics',
        ga: 'Statistics'
      },
      statisticsDesc: {
        en: 'Agribot by the numbers',
        tw: 'Agribot wɔ numbers mu',
        ee: 'Agribot wɔ numbers me',
        ga: 'Agribot wɔ numbers mu'
      },
      footer: {
        en: '© 2024 Agribot. Made with ❤️ for Ghana\'s agricultural community.',
        tw: '© 2024 Agribot. Yɛ wɔ ❤️ ho ma Ghana kuayɛ community.',
        ee: '© 2024 Agribot. Wɔ wɔ ❤️ gbɔ na Ghana agblẽnɔnɔ community.',
        ga: '© 2024 Agribot. Yɛ wɔ ❤️ ho ma Ghana kuayɛ community.'
      }
    };
    
    const textGroup = texts[key as keyof typeof texts];
    let text = textGroup?.en || key;
    
    // Replace placeholders
    if (key === 'welcomeBack' && user?.name) {
      text = text.replace('{name}', user.name);
    }
    
    return text;
  };

  return (
    <div className="min-h-screen bg-gradient-earth">
      {/* Navigation Header */}
      <header className="bg-gradient-primary text-primary-foreground py-4">
        <div className="container mx-auto px-4">
          <div className="flex items-center justify-between">
            <div className="flex items-center gap-4">
              <img src={agribotLogo} alt="Agribot Logo" className="h-10 w-auto bg-white/10 rounded-lg p-1" />
              <h1 className="text-xl font-bold">Agribot</h1>
            </div>

            <div className="flex items-center gap-4">
              {isAuthenticated ? (
                <div className="flex items-center gap-3">
                  <Badge className={getRoleInfo(user?.role).color}>
                    {getRoleInfo(user?.role).icon} {getRoleInfo(user?.role).title}
                  </Badge>
                  
                  <DropdownMenu>
                    <DropdownMenuTrigger asChild>
                      <Button variant="ghost" className="gap-2 text-primary-foreground hover:bg-primary-foreground/20">
                        <Avatar className="h-6 w-6">
                          <AvatarImage src={user?.avatar} alt={user?.name} />
                          <AvatarFallback className="text-xs">
                            {user?.name.split(' ').map(n => n[0]).join('')}
                          </AvatarFallback>
                        </Avatar>
                        <span className="hidden md:inline">{user?.name}</span>
                      </Button>
                    </DropdownMenuTrigger>
                    <DropdownMenuContent align="end">
                      <DropdownMenuItem asChild>
                        <Link to="/profile" className="flex items-center gap-2">
                          <User className="h-4 w-4" />
                          {getText('myProfile')}
                        </Link>
                      </DropdownMenuItem>
                      <DropdownMenuItem asChild>
                        <Link to="/settings" className="flex items-center gap-2">
                          <Settings className="h-4 w-4" />
                          {getText('settings')}
                        </Link>
                      </DropdownMenuItem>
                      <DropdownMenuItem onClick={logout} className="flex items-center gap-2 text-red-600">
                        <LogOut className="h-4 w-4" />
                        {getText('logout')}
                      </DropdownMenuItem>
                    </DropdownMenuContent>
                  </DropdownMenu>
                </div>
              ) : (
                <div className="flex items-center gap-2">
                  <Link to="/login">
                    <Button variant="harvest" size="sm">
                      {getText('signIn')}
                    </Button>
                  </Link>
                </div>
              )}
            </div>
          </div>
        </div>
      </header>

      {/* Hero Section */}
      <section className="relative overflow-hidden">
        <div 
          className="absolute inset-0 bg-cover bg-center bg-no-repeat"
          style={{ backgroundImage: `url(${heroImage})` }}
        >
          <div className="absolute inset-0 bg-primary/80"></div>
        </div>
        
        <div className="relative container mx-auto px-4 py-20 text-center">
          <div className="max-w-4xl mx-auto space-y-6">
            <div className="flex justify-center mb-6">
              <img src={agribotLogo} alt="Agribot Logo" className="h-24 w-auto drop-shadow-lg bg-white/20 rounded-xl p-2" />
            </div>
            <h1 className="text-5xl md:text-6xl font-bold text-primary-foreground">
              {getText('heroTitle')}
            </h1>
            <p className="text-xl md:text-2xl text-primary-foreground/90 max-w-2xl mx-auto">
              {getText('heroSubtitle')}
            </p>
            <p className="text-lg text-primary-foreground/80 max-w-3xl mx-auto">
              {getText('heroDescription')}
            </p>
            
            {isAuthenticated && (
              <div className="bg-primary-foreground/10 rounded-lg p-4 max-w-md mx-auto">
                <p className="text-primary-foreground/90">
                  {getText('welcomeBack')}
                </p>
              </div>
            )}
            
            <div className="flex flex-wrap justify-center gap-4 pt-6">
              <Button 
                variant="harvest" 
                size="lg"
                onClick={() => document.getElementById('chat-section')?.scrollIntoView({ behavior: 'smooth' })}
                className="gap-2"
              >
                <Bot className="h-5 w-5" />
                {getText('startAiChat')}
              </Button>
            </div>
          </div>
        </div>
      </section>

      {/* Language Selection Section - Now comes first */}
      <section className="py-16 bg-gradient-to-b from-background to-muted/20">
        <div className="container mx-auto px-4">
          <div className="text-center mb-12">
            <div className="inline-flex items-center gap-3 mb-4">
              <Languages className="h-8 w-8 text-primary" />
              <h2 className="text-3xl md:text-4xl font-bold text-foreground">
                {getText('chooseLanguage')}
              </h2>
            </div>
            <p className="text-lg text-muted-foreground max-w-2xl mx-auto">
              {getText('chooseLanguageDesc')}
            </p>
          </div>

          {/* Language Selector */}
          <div className="max-w-2xl mx-auto mb-8">
                        <LanguageSelector />
          </div>
        </div>
      </section>

      {/* AI Bot Interface - Now comes after language selection */}
      <section id="chat-section" className="py-16 bg-gradient-to-b from-muted/20 to-background">
        <div className="container mx-auto px-4">
          <div className="text-center mb-12">
            <div className="inline-flex items-center gap-3 mb-4">
              <Bot className="h-8 w-8 text-primary" />
              <h2 className="text-3xl md:text-4xl font-bold text-foreground">
                {getText('chatWithAgribot')}
              </h2>
            </div>
            <p className="text-lg text-muted-foreground max-w-2xl mx-auto">
              {getText('chatWithAgribotDesc')}
            </p>
          </div>

          {/* Chat Interface */}
          <div className="max-w-4xl mx-auto">
            <ChatInterface />
          </div>
        </div>
      </section>

      {/* Feature Showcase */}
      <section className="py-16 bg-gradient-to-b from-background to-muted/20">
        <div className="container mx-auto px-4">
          <div className="text-center mb-12">
            <div className="inline-flex items-center gap-3 mb-4">
              <Zap className="h-8 w-8 text-primary" />
              <h2 className="text-3xl md:text-4xl font-bold text-foreground">
                {getText('features')}
              </h2>
            </div>
            <p className="text-lg text-muted-foreground max-w-2xl mx-auto">
              {getText('featuresDesc')}
            </p>
          </div>

          <FeatureShowcase />
        </div>
      </section>

      {/* Team Section */}
              <TeamSection />

      {/* Statistics Section */}
      <section className="py-16 bg-primary text-primary-foreground">
        <div className="container mx-auto px-4">
          <div className="text-center mb-12">
            <h2 className="text-3xl md:text-4xl font-bold mb-4">
              {getText('statistics')}
            </h2>
            <p className="text-lg text-primary-foreground/80 max-w-2xl mx-auto">
              {getText('statisticsDesc')}
            </p>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
            <div className="text-center">
              <div className="text-4xl font-bold mb-2">4</div>
              <div className="text-primary-foreground/80">Languages Supported</div>
            </div>
            <div className="text-center">
              <div className="text-4xl font-bold mb-2">24/7</div>
              <div className="text-primary-foreground/80">AI Assistant Available</div>
            </div>
            <div className="text-center">
              <div className="text-4xl font-bold mb-2">100%</div>
              <div className="text-primary-foreground/80">Voice Recognition</div>
            </div>
            <div className="text-center">
              <div className="text-4xl font-bold mb-2">∞</div>
              <div className="text-primary-foreground/80">Agricultural Knowledge</div>
            </div>
          </div>
        </div>
      </section>

      {/* Footer */}
      <footer className="bg-gradient-primary text-primary-foreground py-8">
        <div className="container mx-auto px-4">
          <div className="flex flex-col md:flex-row items-center justify-between gap-4">
            <div className="flex items-center gap-4">
              <img src={agribotLogo} alt="Agribot Logo" className="h-8 w-auto bg-white/10 rounded-lg p-1" />
              <span className="font-semibold">Agribot</span>
            </div>
            <p className="text-sm text-primary-foreground/80 text-center md:text-right">
              {getText('footer')}
            </p>
          </div>
        </div>
      </footer>
    </div>
  );
};

export default Index;
