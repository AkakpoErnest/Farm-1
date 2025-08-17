import { useState } from 'react';
import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { MapPin, ChevronDown } from 'lucide-react';

const locations = [
  { code: 'accra', name: 'Accra', region: 'Greater Accra' },
  { code: 'kumasi', name: 'Kumasi', region: 'Ashanti' },
  { code: 'tamale', name: 'Tamale', region: 'Northern' },
  { code: 'takoradi', name: 'Takoradi', region: 'Western' },
  { code: 'cape-coast', name: 'Cape Coast', region: 'Central' },
  { code: 'ho', name: 'Ho', region: 'Volta' },
  { code: 'sunyani', name: 'Sunyani', region: 'Bono' },
  { code: 'bolgatanga', name: 'Bolgatanga', region: 'Upper East' },
];

interface LocationSelectorProps {
  onLocationSelect: (location: string) => void;
  selectedLocation: string;
  language: string;
}

export const LocationSelector = ({ onLocationSelect, selectedLocation, language }: LocationSelectorProps) => {
  const [isOpen, setIsOpen] = useState(false);

  const getSelectedLocationName = () => {
    const location = locations.find(loc => loc.code === selectedLocation);
    return location ? location.name : 'Accra';
  };

  const getSelectedLocationRegion = () => {
    const location = locations.find(loc => loc.code === selectedLocation);
    return location ? location.region : 'Greater Accra';
  };

  return (
    <Card className="p-4 bg-gradient-earth border-2">
      <div className="flex items-center justify-between mb-4">
        <h3 className="text-lg font-semibold text-foreground">
          {language === 'en' ? 'Select Location' :
           language === 'tw' ? 'Paw Beaeɛ' :
           language === 'ee' ? 'Tia Nɔƒe' :
           language === 'ga' ? 'Paw Beaeɛ' :
           language === 'fa' ? 'Paw Beaeɛ' :
           language === 'fr' ? 'Sélectionner l\'Emplacement' : 'Select Location'}
        </h3>
        <Badge variant="outline" className="text-xs">
          {language === 'en' ? 'For accurate data' :
           language === 'tw' ? 'Ma nsɛm pa' :
           language === 'ee' ? 'Na nyawo pa' :
           language === 'ga' ? 'Ma nsɛm pa' :
           language === 'fa' ? 'Ma nsɛm pa' :
           language === 'fr' ? 'Pour des données précises' : 'For accurate data'}
        </Badge>
      </div>

      <div className="relative">
        <Button
          variant="outline"
          className="w-full justify-between"
          onClick={() => setIsOpen(!isOpen)}
        >
          <div className="flex items-center gap-2">
            <MapPin className="h-4 w-4" />
            <div className="text-left">
              <div className="font-medium">{getSelectedLocationName()}</div>
              <div className="text-xs text-muted-foreground">{getSelectedLocationRegion()}</div>
            </div>
          </div>
          <ChevronDown className={`h-4 w-4 transition-transform ${isOpen ? 'rotate-180' : ''}`} />
        </Button>

        {isOpen && (
          <div className="absolute top-full left-0 right-0 mt-1 bg-background border rounded-lg shadow-lg z-10 max-h-60 overflow-y-auto">
            {locations.map((location) => (
              <button
                key={location.code}
                className={`w-full p-3 text-left hover:bg-muted transition-colors ${
                  selectedLocation === location.code ? 'bg-muted' : ''
                }`}
                onClick={() => {
                  onLocationSelect(location.code);
                  setIsOpen(false);
                }}
              >
                <div className="font-medium">{location.name}</div>
                <div className="text-xs text-muted-foreground">{location.region}</div>
              </button>
            ))}
          </div>
        )}
      </div>

      <p className="text-xs text-muted-foreground mt-3">
        {language === 'en' ? 'Weather and market data will be updated for your selected location' :
         language === 'tw' ? 'Ewiem ne gua nsɛm bɛsesa ama wo beaeɛ a woapaw' :
         language === 'ee' ? 'Yame kple asi nyawo aɖe sesa na wò nɔƒe si wo tia' :
         language === 'ga' ? 'Ewiem ne gua nsɛm bɛsesa ama wo beaeɛ a woapaw' :
         language === 'fa' ? 'Ewiem ne gua nsɛm bɛsesa ama wo beaeɛ a woapaw' :
         language === 'fr' ? 'Les données météo et du marché seront mises à jour pour votre emplacement sélectionné' : 'Weather and market data will be updated for your selected location'}
      </p>
    </Card>
  );
}; 