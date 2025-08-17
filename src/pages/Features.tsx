import { useState } from 'react';
import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { 
  Brain, 
  Mic, 
  Languages, 
  Camera, 
  CloudRain, 
  TrendingUp,
  Users,
  MapPin,
  Wifi,
  MessageSquare,
  ArrowLeft,
  CheckCircle,
  Clock,
  Star,
  Zap,
  Shield,
  Globe,
  Smartphone
} from 'lucide-react';
import { Link } from 'react-router-dom';

const Features = () => {
  const [selectedLanguage, setSelectedLanguage] = useState('en');

  const features = [
    {
      id: 'ai-assistant',
      icon: Brain,
      title: {
        en: "AI Agricultural Assistant",
        tw: "AI Kuayɛ Boafoɔ",
        ee: "AI Agblẽnuto Kpeɖeŋutɔ",
        ga: "AI Kuayɛ Boafoɔ"
      },
      description: {
        en: "Get instant answers about crops, pests, and farming techniques with our advanced AI system",
        tw: "Nya mmuae ntɛm fa aduan, mmoawa, ne kuayɛ akwan yɛ wo AI system",
        ee: "Xɔ ŋuɖoɖo enumake tso nuku, nudzrala, kple agblẽnɔnɔ ŋu yɛ mía AI system",
        ga: "Nya mmuae ntɛm fa aduan, mmoawa, ne kuayɛ akwan yɛ wo AI system"
      },
      details: {
        en: [
          "24/7 instant responses to farming questions",
          "Personalized advice based on your location and crops",
          "Seasonal recommendations for optimal farming",
          "Integration with local agricultural knowledge"
        ],
        tw: [
          "24/7 mmuae ntɛm ma kuayɛ nsɛm",
          "Afotu a ɛfa wo beaeɛ ne aduan ho",
          "Nsɛm a ɛfa bere ho ma kuayɛ pa",
          "Nkitaho yɛ wo amantɔ kuayɛ nimdeɛ"
        ],
        ee: [
          "24/7 ŋuɖoɖo enumake na agblẽnɔnɔ ƒe nyawo",
          "ɖoɖo si ku ɖe wò nɔƒe kple agblẽnɔnɔ ŋu",
          "ɖoɖo si ɖe ɣeyiɣi ŋu ma agblẽnɔnɔ nyuie",
          "takpekpe kple nutɔ agblẽnɔnɔ ƒe nimdeɛ"
        ],
        ga: [
          "24/7 mmuae ntɛm ma kuayɛ nsɛm",
          "Afotu a ɛfa wo beaeɛ ne aduan ho",
          "Nsɛm a ɛfa bere ho ma kuayɛ pa",
          "Nkitaho yɛ wo amantɔ kuayɛ nimdeɛ"
        ]
      },
      status: "active",
      priority: "high"
    },
    {
      id: 'voice-communication',
      icon: Mic,
      title: {
        en: "Voice Communication",
        tw: "Nne Nkitaho",
        ee: "Gbeɖoɖo",
        ga: "Nne Nkitaho"
      },
      description: {
        en: "Speak in your local language and get responses instantly",
        tw: "Kasa wo amantɔ kasa mu na gye mmuae ntɛm",
        ee: "Ƒo nu le wò nutɔwo ƒe gbe me eye nàxɔ ŋuɖoɖo enumake",
        ga: "Kasa wo amantɔ kasa mu na gye mmuae ntɛm"
      },
      details: {
        en: [
          "Voice-to-text in multiple Ghanaian languages",
          "Real-time speech recognition",
          "Voice commands for hands-free operation",
          "Accent and dialect support"
        ],
        tw: [
          "Nne kɔ text wɔ Ghana kasa ahodoɔ mu",
          "Nne hunu ntɛm",
          "Nne command ma nsa nni hɔ",
          "Accent ne dialect support"
        ],
        ee: [
          "Gbe kɔ text le Ghana gbe vovovowo me",
          "Gbe didi enumake",
          "Gbe command ma asi mele",
          "Accent kple dialect kpekpeɖeŋu"
        ],
        ga: [
          "Nne kɔ text wɔ Ghana kasa ahodoɔ mu",
          "Nne hunu ntɛm",
          "Nne command ma nsa nni hɔ",
          "Accent ne dialect support"
        ]
      },
      status: "active",
      priority: "high"
    },
    {
      id: 'multilingual',
      icon: Languages,
      title: {
        en: "Multi-language Support",
        tw: "Kasa Ahodoɔ",
        ee: "Gbe Vovovowo Dzi Kpekpeɖeŋu",
        ga: "Kasa Ahodoɔ"
      },
      description: {
        en: "Available in Twi, Ewe, Ga, and English for maximum accessibility",
        tw: "Ɛwɔ Twi, Ewe, Ga, ne Borɔfo kasa mu ma accessibility",
        ee: "Li le Twi, Ewe, Ga, kple Yevugbe me na accessibility",
        ga: "Ɛwɔ Twi, Ewe, Ga, ne Borɔfo kasa mu ma accessibility"
      },
      details: {
        en: [
          "Full interface in 4 major Ghanaian languages",
          "Automatic language detection",
          "Cultural context awareness",
          "Local agricultural terminology"
        ],
        tw: [
          "Interface nyinaa wɔ Ghana kasa 4 mu",
          "Kasa hunu automatic",
          "Cultural context nimdeɛ",
          "Amanantɔ kuayɛ nsɛm"
        ],
        ee: [
          "Interface katã le Ghana gbe 4 me",
          "Gbe didi automatic",
          "Cultural context ƒe nimdeɛ",
          "Nutɔ agblẽnɔnɔ ƒe nyawo"
        ],
        ga: [
          "Interface nyinaa wɔ Ghana kasa 4 mu",
          "Kasa hunu automatic",
          "Cultural context nimdeɛ",
          "Amanantɔ kuayɛ nsɛm"
        ]
      },
      status: "active",
      priority: "high"
    },
    {
      id: 'weather-integration',
      icon: CloudRain,
      title: {
        en: "Weather Integration",
        tw: "Ewiem Nsɛm",
        ee: "Yame ƒe Nɔnɔme",
        ga: "Ewiem Nsɛm"
      },
      description: {
        en: "Real-time weather data and agricultural recommendations",
        tw: "Ewiem nsɛm ntɛm ne kuayɛ afotu",
        ee: "Yame ƒe nyawo enumake kple agblẽnɔnɔ ɖoɖo",
        ga: "Ewiem nsɛm ntɛm ne kuayɛ afotu"
      },
      details: {
        en: [
          "GMet weather data integration",
          "Agricultural weather alerts",
          "Planting season recommendations",
          "Climate-smart farming advice"
        ],
        tw: [
          "GMet ewiem nsɛm nkitaho",
          "Kuayɛ ewiem alert",
          "Dua bere afotu",
          "Climate-smart kuayɛ afotu"
        ],
        ee: [
          "GMet yame ƒe nyawo takpekpe",
          "Agblẽnɔnɔ yame alert",
          "Ɖe ɣeyiɣi ƒe ɖoɖo",
          "Climate-smart agblẽnɔnɔ ɖoɖo"
        ],
        ga: [
          "GMet ewiem nsɛm nkitaho",
          "Kuayɛ ewiem alert",
          "Dua bere afotu",
          "Climate-smart kuayɛ afotu"
        ]
      },
      status: "active",
      priority: "medium"
    },
    {
      id: 'market-prices',
      icon: TrendingUp,
      title: {
        en: "Market Price Integration",
        tw: "Gua Bo Nkitaho",
        ee: "Asi Ƒe Ga Home Takpekpe",
        ga: "Gua Bo Nkitaho"
      },
      description: {
        en: "Real-time market prices and trend analysis for better selling decisions",
        tw: "Gua bo ntɛm ne trend analysis ma gua pa",
        ee: "Asi Ƒe ga home enumake kple trend analysis ma asi nyuie",
        ga: "Gua bo ntɛm ne trend analysis ma gua pa"
      },
      details: {
        en: [
          "Esoko market data integration",
          "Price trend analysis",
          "Market location information",
          "Optimal selling time recommendations"
        ],
        tw: [
          "Esoko gua nsɛm nkitaho",
          "Bo trend analysis",
          "Gua beaeɛ nsɛm",
          "Gua bere pa afotu"
        ],
        ee: [
          "Esoko asi ƒe nyawo takpekpe",
          "Ga trend analysis",
          "Asi nɔƒe ƒe nyawo",
          "Asi ɣeyiɣi nyuie ƒe ɖoɖo"
        ],
        ga: [
          "Esoko gua nsɛm nkitaho",
          "Bo trend analysis",
          "Gua beaeɛ nsɛm",
          "Gua bere pa afotu"
        ]
      },
      status: "active",
      priority: "medium"
    },
    {
      id: 'expert-support',
      icon: Users,
      title: {
        en: "Expert Support System",
        tw: "Expert Support System",
        ee: "Expert Support System",
        ga: "Expert Support System"
      },
      description: {
        en: "Connect with agricultural extension officers for specialized advice",
        tw: "Ka extension officer ho ma specialized afotu",
        ee: "Ka extension officer gbɔ ma specialized ɖoɖo",
        ga: "Ka extension officer ho ma specialized afotu"
      },
      details: {
        en: [
          "24-hour expert response guarantee",
          "Direct connection to extension officers",
          "Specialized agricultural advice",
          "Follow-up support and monitoring"
        ],
        tw: [
          "24-hour expert response guarantee",
          "Extension officer nkitaho direct",
          "Specialized kuayɛ afotu",
          "Follow-up support ne monitoring"
        ],
        ee: [
          "24-hour expert response guarantee",
          "Extension officer takpekpe direct",
          "Specialized agblẽnɔnɔ ɖoɖo",
          "Follow-up support kple monitoring"
        ],
        ga: [
          "24-hour expert response guarantee",
          "Extension officer nkitaho direct",
          "Specialized kuayɛ afotu",
          "Follow-up support ne monitoring"
        ]
      },
      status: "active",
      priority: "high"
    },
    {
      id: 'crop-disease',
      icon: Camera,
      title: {
        en: "Crop Disease Detection",
        tw: "Aduan Yadeɛ Nhunmu",
        ee: "Nuku Ƒe Dɔléle Didi",
        ga: "Aduan Yadeɛ Nhunmu"
      },
      description: {
        en: "AI-powered crop disease identification through photo analysis",
        tw: "AI-powered aduan yadeɛ hunu yɛ foto analysis",
        ee: "AI-powered nuku ƒe dɔléle didi yɛ foto analysis",
        ga: "AI-powered aduan yadeɛ hunu yɛ foto analysis"
      },
      details: {
        en: [
          "Photo-based disease identification",
          "Treatment recommendations",
          "Prevention strategies",
          "Local treatment options"
        ],
        tw: [
          "Foto-based yadeɛ hunu",
          "Treatment afotu",
          "Prevention strategies",
          "Amanantɔ treatment options"
        ],
        ee: [
          "Foto-based dɔléle didi",
          "Treatment ƒe ɖoɖo",
          "Prevention strategies",
          "Nutɔ treatment options"
        ],
        ga: [
          "Foto-based yadeɛ hunu",
          "Treatment afotu",
          "Prevention strategies",
          "Amanantɔ treatment options"
        ]
      },
      status: "coming-soon",
      priority: "medium"
    },
    {
      id: 'offline-support',
      icon: Wifi,
      title: {
        en: "Offline Support",
        tw: "Intanɛt Nkyerɛe",
        ee: "Internet Manɔmee Kpekpeɖeŋu",
        ga: "Intanɛt Nkyerɛe"
      },
      description: {
        en: "Core features work without internet connection for rural areas",
        tw: "Ntitiriw nneɛma yɛ adwuma sɛ intanɛt nni hɔ mpo",
        ee: "Nu vevitɔwo wɔa dɔ togbɔ be internet meli o hã",
        ga: "Ntitiriw nneɛma yɛ adwuma sɛ intanɛt nni hɔ mpo"
      },
      details: {
        en: [
          "Offline chat history access",
          "Cached agricultural information",
          "Basic farming advice offline",
          "Sync when connection restored"
        ],
        tw: [
          "Offline chat history access",
          "Cached kuayɛ nsɛm",
          "Basic kuayɛ afotu offline",
          "Sync sɛ connection ba bio"
        ],
        ee: [
          "Offline chat history access",
          "Cached agblẽnɔnɔ ƒe nyawo",
          "Basic agblẽnɔnɔ ɖoɖo offline",
          "Sync ne connection va bio"
        ],
        ga: [
          "Offline chat history access",
          "Cached kuayɛ nsɛm",
          "Basic kuayɛ afotu offline",
          "Sync sɛ connection ba bio"
        ]
      },
      status: "coming-soon",
      priority: "medium"
    }
  ];

  const getTitle = (feature: any) => feature.title[selectedLanguage as keyof typeof feature.title] || feature.title.en;
  const getDescription = (feature: any) => feature.description[selectedLanguage as keyof typeof feature.description] || feature.description.en;
  const getDetails = (feature: any) => feature.details[selectedLanguage as keyof typeof feature.details] || feature.details.en;

  const activeFeatures = features.filter(f => f.status === 'active');
  const comingSoonFeatures = features.filter(f => f.status === 'coming-soon');

  return (
    <div className="min-h-screen bg-gradient-earth">
      {/* Header */}
      <div className="bg-primary text-primary-foreground py-6">
        <div className="container mx-auto px-4">
          <div className="flex items-center gap-4">
            <Link to="/">
              <Button variant="ghost" size="sm" className="text-primary-foreground">
                <ArrowLeft className="h-4 w-4 mr-2" />
                {selectedLanguage === 'en' ? 'Back to Home' :
                 selectedLanguage === 'tw' ? 'San kɔ Fie' :
                 selectedLanguage === 'ee' ? 'Trɔ ɖe Aƒe' :
                 selectedLanguage === 'ga' ? 'San kɔ Fie' : 'Back to Home'}
              </Button>
            </Link>
            <div>
              <h1 className="text-3xl font-bold">
                {selectedLanguage === 'en' ? 'Agribot Features' :
                 selectedLanguage === 'tw' ? 'Agribot Nneɛma' :
                 selectedLanguage === 'ee' ? 'Agribot Nɔɔ̃wo' :
                 selectedLanguage === 'ga' ? 'Agribot Nneɛma' : 'Agribot Features'}
              </h1>
              <p className="text-primary-foreground/80">
                {selectedLanguage === 'en' ? 'Comprehensive agricultural assistance powered by AI' :
                 selectedLanguage === 'tw' ? 'AI-powered kuayɛ boafoɔ nneɛma' :
                 selectedLanguage === 'ee' ? 'AI-powered agblẽnɔnɔ kpeɖeŋutɔ nɔɔ̃wo' :
                 selectedLanguage === 'ga' ? 'AI-powered kuayɛ boafoɔ nneɛma' : 'Comprehensive agricultural assistance'}
              </p>
            </div>
          </div>
        </div>
      </div>

      {/* Features Content */}
      <div className="container mx-auto px-4 py-12">
        <Tabs defaultValue="active" className="w-full">
          <TabsList className="grid w-full grid-cols-2 mb-8">
            <TabsTrigger value="active" className="flex items-center gap-2">
              <CheckCircle className="h-4 w-4" />
              {selectedLanguage === 'en' ? 'Active Features' :
               selectedLanguage === 'tw' ? 'Nneɛma a Yɛ Adwuma' :
               selectedLanguage === 'ee' ? 'Nɔɔ̃wo Siwo Le Dɔwɔm' :
               selectedLanguage === 'ga' ? 'Nneɛma a Yɛ Adwuma' : 'Active Features'}
            </TabsTrigger>
            <TabsTrigger value="coming-soon" className="flex items-center gap-2">
              <Clock className="h-4 w-4" />
              {selectedLanguage === 'en' ? 'Coming Soon' :
               selectedLanguage === 'tw' ? 'Ɛreba' :
               selectedLanguage === 'ee' ? 'Egbɔna' :
               selectedLanguage === 'ga' ? 'Ɛreba' : 'Coming Soon'}
            </TabsTrigger>
          </TabsList>

          <TabsContent value="active" className="space-y-8">
            <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
              {activeFeatures.map((feature) => {
                const Icon = feature.icon;
                return (
                  <Card key={feature.id} className="p-6 hover:shadow-lg transition-all duration-300 border-2">
                    <div className="flex items-start gap-4">
                      <div className="p-3 rounded-lg bg-gradient-primary">
                        <Icon className="h-6 w-6 text-primary-foreground" />
                      </div>
                      <div className="flex-1 space-y-3">
                        <div className="flex items-center gap-2">
                          <h3 className="font-semibold text-foreground">
                            {getTitle(feature)}
                          </h3>
                          <Badge variant="default" className="text-xs">
                            {feature.priority === 'high' ? (
                              <Star className="h-3 w-3 mr-1" />
                            ) : (
                              <Zap className="h-3 w-3 mr-1" />
                            )}
                            {feature.priority === 'high' ? 
                              (selectedLanguage === 'en' ? 'High Priority' :
                               selectedLanguage === 'tw' ? 'Priority Kɛseɛ' :
                               selectedLanguage === 'ee' ? 'Priority Kɛse' :
                               selectedLanguage === 'ga' ? 'Priority Kɛseɛ' : 'High Priority') :
                              (selectedLanguage === 'en' ? 'Medium Priority' :
                               selectedLanguage === 'tw' ? 'Priority Mfinimfini' :
                               selectedLanguage === 'ee' ? 'Priority Mfinimfini' :
                               selectedLanguage === 'ga' ? 'Priority Mfinimfini' : 'Medium Priority')
                            }
                          </Badge>
                        </div>
                        <p className="text-sm text-muted-foreground">
                          {getDescription(feature)}
                        </p>
                        <div className="space-y-2">
                          {getDetails(feature).map((detail, index) => (
                            <div key={index} className="flex items-center gap-2 text-xs text-muted-foreground">
                              <CheckCircle className="h-3 w-3 text-green-500" />
                              {detail}
                            </div>
                          ))}
                        </div>
                        <Button variant="outline" size="sm" className="w-full mt-4">
                          <MessageSquare className="h-3 w-3 mr-1" />
                          {selectedLanguage === 'en' ? 'Try Now' :
                           selectedLanguage === 'tw' ? 'Sɔ Hwɛ' :
                           selectedLanguage === 'ee' ? 'Te Kpɔ' :
                           selectedLanguage === 'ga' ? 'Sɔ Hwɛ' : 'Try Now'}
                        </Button>
                      </div>
                    </div>
                  </Card>
                );
              })}
            </div>
          </TabsContent>

          <TabsContent value="coming-soon" className="space-y-8">
            <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
              {comingSoonFeatures.map((feature) => {
                const Icon = feature.icon;
                return (
                  <Card key={feature.id} className="p-6 hover:shadow-lg transition-all duration-300 border-2 opacity-75">
                    <div className="flex items-start gap-4">
                      <div className="p-3 rounded-lg bg-muted">
                        <Icon className="h-6 w-6 text-muted-foreground" />
                      </div>
                      <div className="flex-1 space-y-3">
                        <div className="flex items-center gap-2">
                          <h3 className="font-semibold text-foreground">
                            {getTitle(feature)}
                          </h3>
                          <Badge variant="secondary" className="text-xs">
                            <Clock className="h-3 w-3 mr-1" />
                            {selectedLanguage === 'en' ? 'Coming Soon' :
                             selectedLanguage === 'tw' ? 'Ɛreba' :
                             selectedLanguage === 'ee' ? 'Egbɔna' :
                             selectedLanguage === 'ga' ? 'Ɛreba' : 'Coming Soon'}
                          </Badge>
                        </div>
                        <p className="text-sm text-muted-foreground">
                          {getDescription(feature)}
                        </p>
                        <div className="space-y-2">
                          {getDetails(feature).map((detail, index) => (
                            <div key={index} className="flex items-center gap-2 text-xs text-muted-foreground">
                              <Shield className="h-3 w-3 text-blue-500" />
                              {detail}
                            </div>
                          ))}
                        </div>
                        <Button variant="outline" size="sm" className="w-full mt-4" disabled>
                          <Globe className="h-3 w-3 mr-1" />
                          {selectedLanguage === 'en' ? 'In Development' :
                           selectedLanguage === 'tw' ? 'Wɔ Development' :
                           selectedLanguage === 'ee' ? 'Le Development' :
                           selectedLanguage === 'ga' ? 'Wɔ Development' : 'In Development'}
                        </Button>
                      </div>
                    </div>
                  </Card>
                );
              })}
            </div>
          </TabsContent>
        </Tabs>

        {/* Call to Action */}
        <div className="mt-16 text-center">
          <Card className="p-8 bg-gradient-primary text-primary-foreground">
            <div className="max-w-2xl mx-auto space-y-4">
              <h2 className="text-2xl font-bold">
                {selectedLanguage === 'en' ? 'Ready to Transform Your Farming?' :
                 selectedLanguage === 'tw' ? 'Wo Ready Sɛ Wo Sesɛ Wo Kuayɛ?' :
                 selectedLanguage === 'ee' ? 'Wò Ready Be Nàsesɛ Wò Agblẽnɔnɔ?' :
                 selectedLanguage === 'ga' ? 'Wo Ready Sɛ Wo Sesɛ Wo Kuayɛ?' : 'Ready to Transform Your Farming?'}
              </h2>
              <p className="text-primary-foreground/80">
                {selectedLanguage === 'en' ? 'Start using Agribot today and experience the future of agricultural assistance' :
                 selectedLanguage === 'tw' ? 'Fi Agribot ase nnɛ na sɔ kuayɛ boafoɔ future hwɛ' :
                 selectedLanguage === 'ee' ? 'Dze Agribot gɔme egbe eye nàsɔ agblẽnɔnɔ kpeɖeŋutɔ ƒe future kpɔ' :
                 selectedLanguage === 'ga' ? 'Fi Agribot ase nnɛ na sɔ kuayɛ boafoɔ future hwɛ' : 'Start using Agribot today'}
              </p>
              <div className="flex flex-wrap justify-center gap-4">
                <Link to="/">
                  <Button variant="secondary" size="lg" className="gap-2">
                    <Smartphone className="h-4 w-4" />
                    {selectedLanguage === 'en' ? 'Start Chatting' :
                     selectedLanguage === 'tw' ? 'Fi Nkɔmmɔ Ase' :
                     selectedLanguage === 'ee' ? 'Dze Nubiabia Gɔme' :
                     selectedLanguage === 'ga' ? 'Fi Nkɔmmɔ Ase' : 'Start Chatting'}
                  </Button>
                </Link>
                <Button variant="outline" size="lg" className="gap-2 text-primary-foreground border-primary-foreground">
                  <Users className="h-4 w-4" />
                  {selectedLanguage === 'en' ? 'Learn More' :
                   selectedLanguage === 'tw' ? 'Kyerɛɛ Wo Ho' :
                   selectedLanguage === 'ee' ? 'Kpɔ Nu Vovovowo' :
                   selectedLanguage === 'ga' ? 'Kyerɛɛ Wo Ho' : 'Learn More'}
                </Button>
              </div>
            </div>
          </Card>
        </div>
      </div>
    </div>
  );
};

export default Features; 