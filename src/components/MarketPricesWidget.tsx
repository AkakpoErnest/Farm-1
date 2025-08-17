import { useState, useEffect } from 'react';
import { Card } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { TrendingUp, TrendingDown, Minus, ShoppingBag, MapPin, Clock } from 'lucide-react';
import { marketService, MarketPriceResponse } from '@/services/api';

interface MarketPricesWidgetProps {
  language: string;
  location?: string;
}

export const MarketPricesWidget = ({ language, location }: MarketPricesWidgetProps) => {
  const [prices, setPrices] = useState<MarketPriceResponse[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchPrices = async () => {
      try {
        setLoading(true);
        const data = await marketService.getCurrentPrices(location);
        setPrices(data.slice(0, 4)); // Show top 4 prices
      } catch (error) {
        console.error('Failed to fetch market prices:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchPrices();
    // Refresh prices every 15 minutes
    const interval = setInterval(fetchPrices, 15 * 60 * 1000);
    return () => clearInterval(interval);
  }, [location]);

  const getTrendIcon = (trend: 'up' | 'down' | 'stable') => {
    switch (trend) {
      case 'up':
        return <TrendingUp className="h-4 w-4 text-green-500" />;
      case 'down':
        return <TrendingDown className="h-4 w-4 text-red-500" />;
      case 'stable':
        return <Minus className="h-4 w-4 text-gray-500" />;
      default:
        return <Minus className="h-4 w-4 text-gray-500" />;
    }
  };

  const getTrendText = (trend: 'up' | 'down' | 'stable') => {
    switch (trend) {
      case 'up':
        return language === 'en' ? 'Rising' :
               language === 'tw' ? 'Ɛrekɔ so' :
               language === 'ee' ? 'Le dzo' :
               language === 'ga' ? 'Ɛrekɔ so' : 'Rising';
      case 'down':
        return language === 'en' ? 'Falling' :
               language === 'tw' ? 'Ɛresan' :
               language === 'ee' ? 'Le gbɔna' :
               language === 'ga' ? 'Ɛresan' : 'Falling';
      case 'stable':
        return language === 'en' ? 'Stable' :
               language === 'tw' ? 'Ɛyɛ adwuma' :
               language === 'ee' ? 'Le dɔwɔm' :
               language === 'ga' ? 'Ɛyɛ adwuma' : 'Stable';
      default:
        return '';
    }
  };

  if (loading) {
    return (
      <Card className="p-4 bg-gradient-harvest border-2">
        <div className="flex items-center justify-center h-32">
          <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-secondary-foreground"></div>
        </div>
      </Card>
    );
  }

  if (!prices || prices.length === 0) {
    return (
      <Card className="p-4 bg-gradient-harvest border-2">
        <div className="text-center text-secondary-foreground">
          <ShoppingBag className="h-8 w-8 mx-auto mb-2" />
          <p className="text-sm">
            {language === 'en' ? 'Market data unavailable' :
             language === 'tw' ? 'Gua nsɛm nni hɔ' :
             language === 'ee' ? 'Asi ƒe nyawo meli o' :
             language === 'ga' ? 'Gua nsɛm nni hɔ' : 'Market data unavailable'}
          </p>
        </div>
      </Card>
    );
  }

  return (
    <Card className="p-4 bg-gradient-harvest border-2">
      <div className="text-secondary-foreground">
        {/* Header */}
        <div className="flex items-center justify-between mb-4">
          <h3 className="font-semibold text-lg">
            {language === 'en' ? 'Market Prices' :
             language === 'tw' ? 'Gua Bo' :
             language === 'ee' ? 'Asi ƒe Ga Home' :
             language === 'ga' ? 'Gua Bo' : 'Market Prices'}
          </h3>
          <Badge variant="secondary" className="text-xs">
            {language === 'en' ? 'Live' :
             language === 'tw' ? 'Ɛrekɔ so' :
             language === 'ee' ? 'Le dɔwɔm' :
             language === 'ga' ? 'Ɛrekɔ so' : 'Live'}
          </Badge>
        </div>

        {/* Price List */}
        <div className="space-y-3">
          {prices.map((price, index) => (
            <div key={index} className="flex items-center justify-between p-2 bg-secondary-foreground/10 rounded-lg">
              <div className="flex-1">
                <div className="flex items-center gap-2 mb-1">
                  <span className="font-medium text-sm">{price.crop}</span>
                  {getTrendIcon(price.trend)}
                </div>
                <div className="flex items-center gap-2 text-xs opacity-70">
                  <MapPin className="h-3 w-3" />
                  <span>{price.market}</span>
                </div>
              </div>
              <div className="text-right">
                <div className="font-bold text-sm">
                  GH₵ {price.price.toFixed(2)}/{price.unit}
                </div>
                <div className="text-xs opacity-70">
                  {getTrendText(price.trend)}
                </div>
              </div>
            </div>
          ))}
        </div>

        {/* Last Updated */}
        <div className="text-xs opacity-70 mt-4 text-center flex items-center justify-center gap-1">
          <Clock className="h-3 w-3" />
          <span>
            {language === 'en' ? 'Updated: ' :
             language === 'tw' ? 'Wɔayɛ no bio: ' :
             language === 'ee' ? 'Wɔwɔe bio: ' :
             language === 'ga' ? 'Wɔayɛ no bio: ' : 'Updated: '}
            {prices[0] ? new Date(prices[0].lastUpdated).toLocaleTimeString() : ''}
          </span>
        </div>
      </div>
    </Card>
  );
}; 