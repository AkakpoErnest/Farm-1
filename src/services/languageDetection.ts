// Language detection service for Ghanaian languages
export interface LanguageInfo {
  code: string;
  name: string;
  nativeName: string;
  confidence: number;
  region: string;
}

export interface LanguagePattern {
  words: string[];
  phrases: string[];
  grammar: string[];
  confidence: number;
}

// Language patterns for Ghanaian languages
const LANGUAGE_PATTERNS: Record<string, LanguagePattern> = {
  tw: {
    words: [
      'me', 'wo', 'yɛ', 'ne', 'wɔ', 'ɛyɛ', 'ɛno', 'ɛne', 'ɛwɔ', 'ma', 'ka', 'da',
      'kɔ', 'ba', 'firi', 'kwan', 'yie', 'pa', 'nye', 'ɛ', 'no', 'yi', 'bi', 'kakra',
      'kɛse', 'ketewa', 'pɛ', 'pɛɛ', 'pɛɛɛ', 'yɛɛ', 'yɛɛɛ', 'ɛɛ', 'ɛɛɛ', 'woo', 'woo',
      'ɛɛɛ', 'ɛɛɛɛ', 'ɛɛɛɛɛ', 'ɛɛɛɛɛɛ', 'ɛɛɛɛɛɛɛ', 'ɛɛɛɛɛɛɛɛ', 'ɛɛɛɛɛɛɛɛɛ'
    ],
    phrases: [
      'ɛyɛ yie', 'ɛyɛ pa', 'ɛyɛ nyɛ', 'ɛyɛ kakra', 'ɛyɛ kɛse', 'ɛyɛ ketewa',
      'me pɛ', 'wo pɛ', 'yɛ pɛ', 'ɛ pɛ', 'no pɛ', 'yi pɛ', 'bi pɛ', 'kakra pɛ',
      'me kɔ', 'wo kɔ', 'yɛ kɔ', 'ɛ kɔ', 'no kɔ', 'yi kɔ', 'bi kɔ', 'kakra kɔ',
      'me ba', 'wo ba', 'yɛ ba', 'ɛ ba', 'no ba', 'yi ba', 'bi ba', 'kakra ba'
    ],
    grammar: [
      'me + verb', 'wo + verb', 'yɛ + verb', 'ɛ + verb', 'no + verb',
      'verb + me', 'verb + wo', 'verb + yɛ', 'verb + ɛ', 'verb + no'
    ],
    confidence: 0.9
  },
  ee: {
    words: [
      'nye', 'woe', 'me', 'nye', 'wo', 'le', 'nye', 'woe', 'nye', 'woe', 'nye',
      'woe', 'nye', 'woe', 'nye', 'woe', 'nye', 'woe', 'nye', 'woe', 'nye', 'woe',
      'nye', 'woe', 'nye', 'woe', 'nye', 'woe', 'nye', 'woe', 'nye', 'woe', 'nye'
    ],
    phrases: [
      'nye le', 'woe le', 'me le', 'nye le', 'wo le', 'le nye', 'le woe', 'le me',
      'nye + verb', 'woe + verb', 'me + verb', 'nye + verb', 'wo + verb', 'le + verb'
    ],
    grammar: [
      'nye + verb', 'woe + verb', 'me + verb', 'nye + verb', 'wo + verb', 'le + verb'
    ],
    confidence: 0.85
  },
  ga: {
    words: [
      'mi', 'wo', 'le', 'nye', 'woe', 'mi', 'wo', 'le', 'nye', 'woe', 'mi', 'wo',
      'le', 'nye', 'woe', 'mi', 'wo', 'le', 'nye', 'woe', 'mi', 'wo', 'le', 'nye'
    ],
    phrases: [
      'mi le', 'wo le', 'le mi', 'le wo', 'mi + verb', 'wo + verb', 'le + verb',
      'nye + verb', 'woe + verb', 'mi + verb', 'wo + verb', 'le + verb', 'nye + verb'
    ],
    grammar: [
      'mi + verb', 'wo + verb', 'le + verb', 'nye + verb', 'woe + verb'
    ],
    confidence: 0.8
  },
  fa: {
    words: [
      'me', 'wo', 'yɛ', 'ne', 'wɔ', 'ɛyɛ', 'ɛno', 'ɛne', 'ɛwɔ', 'ma', 'ka', 'da',
      'kɔ', 'ba', 'firi', 'kwan', 'yie', 'pa', 'nye', 'ɛ', 'no', 'yi', 'bi', 'kakra',
      'kɛse', 'ketewa', 'pɛ', 'pɛɛ', 'pɛɛɛ', 'yɛɛ', 'yɛɛɛ', 'ɛɛ', 'ɛɛɛ', 'woo', 'woo',
      'ɛɛɛ', 'ɛɛɛɛ', 'ɛɛɛɛɛ', 'ɛɛɛɛɛɛ', 'ɛɛɛɛɛɛɛ', 'ɛɛɛɛɛɛɛɛ', 'ɛɛɛɛɛɛɛɛɛ'
    ],
    phrases: [
      'ɛyɛ yie', 'ɛyɛ pa', 'ɛyɛ nyɛ', 'ɛyɛ kakra', 'ɛyɛ kɛse', 'ɛyɛ ketewa',
      'me pɛ', 'wo pɛ', 'yɛ pɛ', 'ɛ pɛ', 'no pɛ', 'yi pɛ', 'bi pɛ', 'kakra pɛ',
      'me kɔ', 'wo kɔ', 'yɛ kɔ', 'ɛ kɔ', 'no kɔ', 'yi kɔ', 'bi kɔ', 'kakra kɔ',
      'me ba', 'wo ba', 'yɛ ba', 'ɛ ba', 'no ba', 'yi ba', 'bi ba', 'kakra ba'
    ],
    grammar: [
      'me + verb', 'wo + verb', 'yɛ + verb', 'ɛ + verb', 'no + verb',
      'verb + me', 'verb + wo', 'verb + yɛ', 'verb + ɛ', 'verb + no'
    ],
    confidence: 0.85
  },
  fr: {
    words: [
      'le', 'la', 'les', 'un', 'une', 'des', 'et', 'ou', 'mais', 'dans', 'sur', 'à',
      'est', 'sont', 'était', 'étaient', 'être', 'été', 'étant', 'avoir', 'a', 'avait',
      'faire', 'fait', 'faisait', 'vouloir', 'voulait', 'pouvoir', 'pouvait', 'devoir', 'devait'
    ],
    phrases: [
      'comment allez-vous', 'qu\'est-ce que c\'est', 'où êtes-vous', 'quand voulez-vous', 'pourquoi avez-vous',
      'pouvez-vous aider', 'merci', 'vous êtes bienvenu', 'excusez-moi', 'je suis désolé'
    ],
    grammar: [
      'sujet + verbe', 'verbe + objet', 'adjectif + nom', 'adverbe + verbe'
    ],
    confidence: 0.9
  },
  en: {
    words: [
      'the', 'and', 'or', 'but', 'in', 'on', 'at', 'to', 'for', 'of', 'with', 'by',
      'is', 'are', 'was', 'were', 'be', 'been', 'being', 'have', 'has', 'had', 'do',
      'does', 'did', 'will', 'would', 'could', 'should', 'may', 'might', 'can'
    ],
    phrases: [
      'how are you', 'what is this', 'where are you', 'when will you', 'why did you',
      'can you help', 'thank you', 'you are welcome', 'excuse me', 'i am sorry'
    ],
    grammar: [
      'subject + verb', 'verb + object', 'adjective + noun', 'adverb + verb'
    ],
    confidence: 0.95
  }
};

// Enhanced language detection using multiple methods
export class LanguageDetector {
  private static instance: LanguageDetector;
  
  static getInstance(): LanguageDetector {
    if (!LanguageDetector.instance) {
      LanguageDetector.instance = new LanguageDetector();
    }
    return LanguageDetector.instance;
  }

  // Detect language from text using pattern matching
  detectFromText(text: string): LanguageInfo {
    const lowerText = text.toLowerCase().trim();
    const words = lowerText.split(/\s+/);
    
    const scores: Record<string, number> = {};
    
    // Initialize scores
    Object.keys(LANGUAGE_PATTERNS).forEach(lang => {
      scores[lang] = 0;
    });
    
    // Score based on word matches
    words.forEach(word => {
      Object.entries(LANGUAGE_PATTERNS).forEach(([lang, pattern]) => {
        if (pattern.words.includes(word)) {
          scores[lang] += 1;
        }
      });
    });
    
    // Score based on phrase matches
    Object.entries(LANGUAGE_PATTERNS).forEach(([lang, pattern]) => {
      pattern.phrases.forEach(phrase => {
        if (lowerText.includes(phrase)) {
          scores[lang] += 2; // Phrases get higher weight
        }
      });
    });
    
    // Score based on grammar patterns
    Object.entries(LANGUAGE_PATTERNS).forEach(([lang, pattern]) => {
      pattern.grammar.forEach(grammar => {
        if (this.matchesGrammarPattern(lowerText, grammar)) {
          scores[lang] += 1.5;
        }
      });
    });
    
    // Normalize scores
    const maxScore = Math.max(...Object.values(scores));
    Object.keys(scores).forEach(lang => {
      scores[lang] = scores[lang] / maxScore;
    });
    
    // Find the language with highest score
    let detectedLang = 'en';
    let highestScore = scores.en;
    
    Object.entries(scores).forEach(([lang, score]) => {
      if (score > highestScore) {
        highestScore = score;
        detectedLang = lang;
      }
    });
    
    return this.getLanguageInfo(detectedLang, highestScore);
  }

  // Detect language from audio using Web Speech API
  async detectFromAudio(audioBlob: Blob): Promise<LanguageInfo> {
    // This would integrate with a speech-to-text service
    // For now, return a default detection
    return this.getLanguageInfo('en', 0.7);
  }

  // Detect language from speech recognition result
  detectFromSpeech(transcript: string, confidence: number = 0.8): LanguageInfo {
    const detected = this.detectFromText(transcript);
    detected.confidence = Math.min(detected.confidence * confidence, 1);
    return detected;
  }

  // Check if text matches a grammar pattern
  private matchesGrammarPattern(text: string, pattern: string): boolean {
    // Simple pattern matching - can be enhanced with proper NLP
    if (pattern.includes('+')) {
      const parts = pattern.split('+').map(p => p.trim());
      return parts.every(part => text.includes(part));
    }
    return text.includes(pattern);
  }

  // Get language information
  private getLanguageInfo(code: string, confidence: number): LanguageInfo {
    const languageNames: Record<string, { name: string; nativeName: string; region: string }> = {
      tw: { name: 'Twi', nativeName: 'Twi', region: 'Ashanti, Central Ghana' },
      ee: { name: 'Ewe', nativeName: 'Eʋegbe', region: 'Volta Region, Ghana' },
      ga: { name: 'Ga', nativeName: 'Ga', region: 'Greater Accra Region, Ghana' },
      fa: { name: 'Fante', nativeName: 'Fante', region: 'Central Region, Ghana' },
      fr: { name: 'French', nativeName: 'Français', region: 'International, West Africa' },
      en: { name: 'English', nativeName: 'English', region: 'Ghana (Official)' }
    };
    
    const info = languageNames[code] || languageNames.en;
    
    return {
      code,
      name: info.name,
      nativeName: info.nativeName,
      confidence: Math.max(0.1, Math.min(1, confidence)),
      region: info.region
    };
  }

  // Get supported languages
  getSupportedLanguages(): LanguageInfo[] {
    return Object.keys(LANGUAGE_PATTERNS).map(code => 
      this.getLanguageInfo(code, 1)
    );
  }

  // Validate if a language code is supported
  isLanguageSupported(code: string): boolean {
    return code in LANGUAGE_PATTERNS;
  }

  // Get language patterns for a specific language
  getLanguagePatterns(code: string): LanguagePattern | null {
    return LANGUAGE_PATTERNS[code] || null;
  }
}

// Export singleton instance
export const languageDetector = LanguageDetector.getInstance();
