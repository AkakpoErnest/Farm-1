import { Card } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { useLanguage } from '@/contexts/LanguageContext';
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
  MessageSquare
} from 'lucide-react';

interface FeatureShowcaseProps {
  language?: string;
}

export const FeatureShowcase = ({ language: propLanguage }: FeatureShowcaseProps) => {
  const { language: contextLanguage } = useLanguage();
  const language = propLanguage || contextLanguage;
  const features = [
    {
      icon: Brain,
      title: {
        en: "AI Agricultural Assistant",
        tw: "AI Kuayɛ Boafoɔ",
        ee: "AI Agblẽnuto Kpeɖeŋutɔ",
        ga: "AI Kuayɛ Boafoɔ"
      },
      description: {
        en: "Get instant answers about crops, pests, and farming techniques",
        tw: "Nya mmuae ntɛm fa aduan, mmoawa, ne kuayɛ akwan ho",
        ee: "Xɔ ŋuɖoɖo enumake tso nuku, nudzrala, kple agblẽnɔnɔ ŋu",
        ga: "Nya mmuae ntɛm fa aduan, mmoawa, ne kuayɛ akwan ho"
      },
      status: "active"
    },
    {
      icon: Mic,
      title: {
        en: "Voice Communication",
        tw: "Nne Nkitaho",
        ee: "Gbeɖoɖo",
        ga: "Nne Nkitaho"
      },
      description: {
        en: "Speak in your local language and get responses",
        tw: "Kasa wo amantɔ kasa mu na gye mmuae",
        ee: "Ƒo nu le wò nutɔwo ƒe gbe me eye nàxɔ ŋuɖoɖo",
        ga: "Kasa wo amantɔ kasa mu na gye mmuae"
      },
      status: "active"
    },
    {
      icon: Languages,
      title: {
        en: "Multi-language Support",
        tw: "Kasa Ahodoɔ",
        ee: "Gbe Vovovowo Dzi Kpekpeɖeŋu",
        ga: "Kasa Ahodoɔ"
      },
      description: {
        en: "Available in Twi, Ewe, Ga, and English",
        tw: "Ɛwɔ Twi, Ewe, Ga, ne Borɔfo kasa mu",
        ee: "Li le Twi, Ewe, Ga, kple Yevugbe me",
        ga: "Ɛwɔ Twi, Ewe, Ga, ne Borɔfo kasa mu"
      },
      status: "active"
    },
    {
      icon: Camera,
      title: {
        en: "Crop Disease Detection",
        tw: "Aduan Yadeɛ Nhunmu",
        ee: "Nuku Ƒe Dɔléle Didi",
        ga: "Aduan Yadeɛ Nhunmu"
      },
      description: {
        en: "Take photos to identify plant diseases instantly",
        tw: "Gye mfoni na hu aduan yadeɛ ntɛm",
        ee: "Ɖe foto be nàdze nuku ƒe dɔléle enumake",
        ga: "Gye mfoni na hu aduan yadeɛ ntɛm"
      },
      status: "coming-soon"
    },
    {
      icon: CloudRain,
      title: {
        en: "Weather Integration",
        tw: "Ewiem Nsɛm",
        ee: "Yame Ƒe Nɔnɔme",
        ga: "Ewiem Nsɛm"
      },
      description: {
        en: "Get weather-based farming recommendations",
        tw: "Nya ewiem nsɛm a ɛfa kuayɛ ho",
        ee: "Xɔ agblẽnɔnɔ ɖoɖo si ɖo yame ƒe nɔnɔme ŋu",
        ga: "Nya ewiem nsɛm a ɛfa kuayɛ ho"
      },
      status: "coming-soon"
    },
    {
      icon: TrendingUp,
      title: {
        en: "Market Prices",
        tw: "Gua Bo",
        ee: "Asi Ƒe Ga Home",
        ga: "Gua Bo"
      },
      description: {
        en: "Real-time crop prices and market trends",
        tw: "Aduan bo a ɛrekɔ so ne gua mu nsɛm",
        ee: "Nuku Ƒe ga home kple asi me nɔnɔme",
        ga: "Aduan bo a ɛrekɔ so ne gua mu nsɛm"
      },
      status: "coming-soon"
    },
    {
      icon: Users,
      title: {
        en: "Farmer Community",
        tw: "Akuafo Fekuo",
        ee: "Agblẽnɔla Ƒe Hame",
        ga: "Akuafo Fekuo"
      },
      description: {
        en: "Connect with other farmers in your region",
        tw: "Di nkitaho akuafo foforɔ wɔ wo mpɔtam",
        ee: "Do takpekpe kple agblẽnɔla bubuwo le wò nutɔwo me",
        ga: "Di nkitaho akuafo foforɔ wɔ wo mpɔtam"
      },
      status: "coming-soon"
    },
    {
      icon: MapPin,
      title: {
        en: "Location Services",
        tw: "Mmeae Dwumadie",
        ee: "Teƒe Ƒe Dɔwɔwɔ",
        ga: "Mmeae Dwumadie"
      },
      description: {
        en: "Get location-specific agricultural advice",
        tw: "Nya kuayɛ afotu a ɛfa wo beaeɛ ho",
        ee: "Xɔ agblẽnɔnɔ ɖoɖo si ku ɖe wò nɔƒe ŋu",
        ga: "Nya kuayɛ afotu a ɛfa wo beaeɛ ho"
      },
      status: "coming-soon"
    },
    {
      icon: Wifi,
      title: {
        en: "Offline Support",
        tw: "Intanɛt Nkyerɛe",
        ee: "Internet Manɔmee Kpekpeɖeŋu",
        ga: "Intanɛt Nkyerɛe"
      },
      description: {
        en: "Core features work without internet connection",
        tw: "Ntitiriw nneɛma yɛ adwuma sɛ intanɛt nni hɔ mpo",
        ee: "Nu vevitɔwo wɔa dɔ togbɔ be internet meli o hã",
        ga: "Ntitiriw nneɛma yɛ adwuma sɛ intanɛt nni hɔ mpo"
      },
      status: "coming-soon"
    }
  ];

  const getTitle = (feature: any) => feature.title[language as keyof typeof feature.title] || feature.title.en;
  const getDescription = (feature: any) => feature.description[language as keyof typeof feature.description] || feature.description.en;

  return (
    <section className="py-16 container mx-auto px-4">
      <div className="text-center mb-12">
        <h2 className="text-3xl md:text-4xl font-bold text-foreground mb-4">
          {language === 'en' ? 'AI-Powered Agricultural Features' :
           language === 'tw' ? 'AI Kuayɛ Nneɛma' :
           language === 'ee' ? 'AI Ŋusẽ Agblẽnɔnɔ Nɔɔ̃wo' :
           language === 'ga' ? 'AI Kuayɛ Nneɛma' : 'AI-Powered Agricultural Features'}
        </h2>
        <p className="text-lg text-muted-foreground max-w-2xl mx-auto">
          {language === 'en' ? 'Cutting-edge technology to revolutionize farming in Ghana' :
           language === 'tw' ? 'Mfidie foforɔ a ɛbɛsesa kuayɛ wɔ Ghana' :
           language === 'ee' ? 'Mɔnu yeye siwo atrɔ̃ agblẽnɔnɔ le Ghana' :
           language === 'ga' ? 'Mfidie foforɔ a ɛbɛsesa kuayɛ wɔ Ghana' : 'Cutting-edge technology to revolutionize farming in Ghana'}
        </p>
      </div>

      <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
        {features.map((feature, index) => {
          const Icon = feature.icon;
          return (
            <Card key={index} className="p-6 hover:shadow-medium transition-all duration-300 border-2">
              <div className="flex items-start gap-4">
                <div className={`p-3 rounded-lg ${
                  feature.status === 'active' 
                    ? 'bg-gradient-primary' 
                    : 'bg-muted'
                }`}>
                  <Icon className={`h-6 w-6 ${
                    feature.status === 'active' 
                      ? 'text-primary-foreground' 
                      : 'text-muted-foreground'
                  }`} />
                </div>
                <div className="flex-1 space-y-2">
                  <div className="flex items-center gap-2">
                    <h3 className="font-semibold text-foreground">
                      {getTitle(feature)}
                    </h3>
                    <Badge variant={feature.status === 'active' ? 'default' : 'secondary'} className="text-xs">
                      {feature.status === 'active' 
                        ? (language === 'en' ? 'Active' : 
                           language === 'tw' ? 'Yɛ Adwuma' :
                           language === 'ee' ? 'Le Dɔwɔm' :
                           language === 'ga' ? 'Yɛ Adwuma' : 'Active')
                        : (language === 'en' ? 'Coming Soon' :
                           language === 'tw' ? 'Ɛreba' :
                           language === 'ee' ? 'Egbɔna' :
                           language === 'ga' ? 'Ɛreba' : 'Coming Soon')
                      }
                    </Badge>
                  </div>
                  <p className="text-sm text-muted-foreground">
                    {getDescription(feature)}
                  </p>
                  {feature.status === 'active' && (
                    <Button variant="outline" size="sm" className="mt-2">
                      <MessageSquare className="h-3 w-3 mr-1" />
                      {language === 'en' ? 'Try Now' :
                       language === 'tw' ? 'Sɔ Hwɛ' :
                       language === 'ee' ? 'Te Kpɔ' :
                       language === 'ga' ? 'Sɔ Hwɛ' : 'Try Now'}
                    </Button>
                  )}
                </div>
              </div>
            </Card>
          );
        })}
      </div>


    </section>
  );
};