import { useState, useEffect } from 'react';
import { Card } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { CloudRain, Sun, Cloud, Wind, Thermometer, Droplets } from 'lucide-react';
import { weatherService, WeatherResponse } from '@/services/api';

interface WeatherWidgetProps {
  language: string;
  location?: string;
}

export const WeatherWidget = ({ language, location = 'Accra' }: WeatherWidgetProps) => {
  const [weather, setWeather] = useState<WeatherResponse | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchWeather = async () => {
      try {
        setLoading(true);
        const data = await weatherService.getCurrentWeather(location);
        setWeather(data);
      } catch (error) {
        console.error('Failed to fetch weather:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchWeather();
    // Refresh weather every 30 minutes
    const interval = setInterval(fetchWeather, 30 * 60 * 1000);
    return () => clearInterval(interval);
  }, [location]);

  const getWeatherIcon = (condition: string) => {
    const lowerCondition = condition.toLowerCase();
    if (lowerCondition.includes('rain')) return <CloudRain className="h-6 w-6" />;
    if (lowerCondition.includes('sunny') || lowerCondition.includes('clear')) return <Sun className="h-6 w-6" />;
    if (lowerCondition.includes('cloud')) return <Cloud className="h-6 w-6" />;
    return <Sun className="h-6 w-6" />;
  };

  const getWeatherAdvice = () => {
    if (!weather) return '';
    
    const temp = weather.temperature;
    const condition = weather.condition.toLowerCase();
    
    if (temp > 30) {
      return language === 'en' ? 'High temperature - water crops more frequently' :
             language === 'tw' ? 'Ewiem yɛ hyew - gua aduan nsuo pii' :
             language === 'ee' ? 'Yame le dzo - na nukuwo nɔ' :
             language === 'ga' ? 'Ewiem yɛ hyew - gua aduan nsuo pii' : '';
    } else if (condition.includes('rain')) {
      return language === 'en' ? 'Rain expected - good for planting' :
             language === 'tw' ? 'Osutɔ reba - ɛyɛ pa sɛ woaburow' :
             language === 'ee' ? 'Tsidzadza le gbɔna - ɖe nukuwo' :
             language === 'ga' ? 'Osutɔ reba - ɛyɛ pa sɛ woaburow' : '';
    } else {
      return language === 'en' ? 'Good weather for farming activities' :
             language === 'tw' ? 'Ewiem yɛ pa ma kuayɛ adwuma' :
             language === 'ee' ? 'Yame le dɔwɔm na agblẽnɔnɔ' :
             language === 'ga' ? 'Ewiem yɛ pa ma kuayɛ adwuma' : '';
    }
  };

  if (loading) {
    return (
      <Card className="p-4 bg-gradient-primary border-2">
        <div className="flex items-center justify-center h-32">
          <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-primary-foreground"></div>
        </div>
      </Card>
    );
  }

  if (!weather) {
    return (
      <Card className="p-4 bg-gradient-primary border-2">
        <div className="text-center text-primary-foreground">
          <Cloud className="h-8 w-8 mx-auto mb-2" />
          <p className="text-sm">
            {language === 'en' ? 'Weather data unavailable' :
             language === 'tw' ? 'Ewiem nsɛm nni hɔ' :
             language === 'ee' ? 'Yame ƒe nyawo meli o' :
             language === 'ga' ? 'Ewiem nsɛm nni hɔ' : 'Weather data unavailable'}
          </p>
        </div>
      </Card>
    );
  }

  return (
    <Card className="p-4 bg-gradient-primary border-2">
      <div className="text-primary-foreground">
        {/* Header */}
        <div className="flex items-center justify-between mb-3">
          <h3 className="font-semibold text-lg">
            {language === 'en' ? 'Weather' :
             language === 'tw' ? 'Ewiem' :
             language === 'ee' ? 'Yame' :
             language === 'ga' ? 'Ewiem' : 'Weather'}
          </h3>
          <Badge variant="secondary" className="text-xs">
            {location}
          </Badge>
        </div>

        {/* Main Weather Info */}
        <div className="flex items-center justify-between mb-4">
          <div className="flex items-center gap-3">
            {getWeatherIcon(weather.condition)}
            <div>
              <div className="text-2xl font-bold">{weather.temperature}°C</div>
              <div className="text-sm opacity-90">{weather.condition}</div>
            </div>
          </div>
          <div className="text-right text-sm">
            <div className="flex items-center gap-1 mb-1">
              <Droplets className="h-4 w-4" />
              <span>{weather.humidity}%</span>
            </div>
            <div className="flex items-center gap-1">
              <Wind className="h-4 w-4" />
              <span>{weather.windSpeed} km/h</span>
            </div>
          </div>
        </div>

        {/* Weather Advice */}
        <div className="bg-primary-foreground/10 rounded-lg p-3">
          <p className="text-sm leading-relaxed">
            {getWeatherAdvice()}
          </p>
        </div>

        {/* Last Updated */}
        <div className="text-xs opacity-70 mt-3 text-center">
          {language === 'en' ? 'Last updated: ' :
           language === 'tw' ? 'Wɔayɛ no bio: ' :
           language === 'ee' ? 'Wɔwɔe bio: ' :
           language === 'ga' ? 'Wɔayɛ no bio: ' : 'Last updated: '}
          {new Date(weather.timestamp).toLocaleTimeString()}
        </div>
      </div>
    </Card>
  );
}; 