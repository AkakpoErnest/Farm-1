import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { useLanguage } from "@/contexts/LanguageContext";

const languages = [
  { code: 'en', name: 'English', flag: '🇬🇧' },
  { code: 'tw', name: 'Twi', flag: '🇬🇭' },
  { code: 'ee', name: 'Ewe', flag: '🇬🇭' },
  { code: 'ga', name: 'Ga', flag: '🇬🇭' },
  { code: 'fa', name: 'Fante', flag: '🇬🇭' },
  { code: 'fr', name: 'French', flag: '🇫🇷' },
];

export const LanguageSelector = () => {
  const { language, setLanguage, t } = useLanguage();

  return (
    <Card className="p-6 bg-gradient-earth border-2">
      <h3 className="text-lg font-semibold mb-4 text-foreground">
        {t('language.title')}
      </h3>
      <div className="grid grid-cols-3 gap-3">
        {languages.map((lang) => (
          <Button
            key={lang.code}
            variant={language === lang.code ? "ghana" : "earth"}
            className="h-auto p-4 flex flex-col items-center gap-2 text-center"
            onClick={() => setLanguage(lang.code as any)}
          >
            <span className="text-2xl">{lang.flag}</span>
            <span className="font-medium">{lang.name}</span>
          </Button>
        ))}
      </div>
    </Card>
  );
};