import { useState, useRef, useEffect } from 'react';
import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { Mic, MicOff, Send, Volume2, Languages } from 'lucide-react';
import { toast } from 'sonner';
import { languageDetector, type LanguageInfo } from '@/services/languageDetection';

interface VoiceRecorderProps {
  language: string;
  onMessageReceived: (message: string, detectedLanguage: string) => void;
}



export const VoiceRecorder = ({ language, onMessageReceived }: VoiceRecorderProps) => {
  const [isRecording, setIsRecording] = useState(false);
  const [hasRecording, setHasRecording] = useState(false);
  const [isProcessing, setIsProcessing] = useState(false);
  const [transcribedText, setTranscribedText] = useState('');
  const [detectedLanguage, setDetectedLanguage] = useState<LanguageInfo | null>(null);
  const [audioBlob, setAudioBlob] = useState<Blob | null>(null);
  
  const mediaRecorderRef = useRef<MediaRecorder | null>(null);
  const audioChunksRef = useRef<Blob[]>([]);

  // Check if browser supports speech recognition
  const isSpeechRecognitionSupported = 'webkitSpeechRecognition' in window || 'SpeechRecognition' in window;

  // Initialize speech recognition
  const SpeechRecognition = (window as any).SpeechRecognition || (window as any).webkitSpeechRecognition;
  const recognition = isSpeechRecognitionSupported ? new SpeechRecognition() : null;

  useEffect(() => {
    if (recognition) {
      recognition.continuous = false;
      recognition.interimResults = false;
      recognition.maxAlternatives = 1;
      
      // Set language based on current selection
      const languageMap: { [key: string]: string } = {
        'en': 'en-US',
        'tw': 'en-US', // Twi - use English recognition for now
        'ee': 'en-US', // Ewe - use English recognition for now
        'ga': 'en-US', // Ga - use English recognition for now
      };
      
      recognition.lang = languageMap[language] || 'en-US';
      
      recognition.onresult = (event: any) => {
        const transcript = event.results[0][0].transcript;
        setTranscribedText(transcript);
        
        // Use enhanced language detection service
        const detected = languageDetector.detectFromSpeech(transcript);
        setDetectedLanguage(detected);
        
        setIsProcessing(false);
        setHasRecording(true);
      };
      
      recognition.onerror = (event: any) => {
        console.error('Speech recognition error:', event.error);
        toast.error('Speech recognition failed. Please try again.');
        setIsProcessing(false);
      };
      
      recognition.onend = () => {
        setIsRecording(false);
      };
    }
  }, [language, recognition]);



  const startRecording = async () => {
    try {
      const stream = await navigator.mediaDevices.getUserMedia({ audio: true });
      
      if (recognition) {
        // Use speech recognition
        recognition.start();
        setIsRecording(true);
        setIsProcessing(true);
      } else {
        // Fallback to MediaRecorder
        const mediaRecorder = new MediaRecorder(stream);
        mediaRecorderRef.current = mediaRecorder;
        audioChunksRef.current = [];
        
        mediaRecorder.ondataavailable = (event) => {
          audioChunksRef.current.push(event.data);
        };
        
        mediaRecorder.onstop = () => {
          const audioBlob = new Blob(audioChunksRef.current, { type: 'audio/wav' });
          setAudioBlob(audioBlob);
          setHasRecording(true);
          setIsRecording(false);
          
          // For MediaRecorder fallback, we'll need to send the audio to a service
          // that can transcribe and detect language
          toast.info('Audio recorded. Language detection requires internet connection.');
        };
        
        mediaRecorder.start();
        setIsRecording(true);
      }
      
      stream.getTracks().forEach(track => {
        track.onended = () => {
          if (mediaRecorderRef.current && mediaRecorderRef.current.state === 'recording') {
            mediaRecorderRef.current.stop();
          }
        };
      });
      
    } catch (error) {
      console.error('Error accessing microphone:', error);
      toast.error('Unable to access microphone. Please check permissions.');
    }
  };

  const stopRecording = () => {
    if (recognition && isRecording) {
      recognition.stop();
    } else if (mediaRecorderRef.current && mediaRecorderRef.current.state === 'recording') {
      mediaRecorderRef.current.stop();
    }
    setIsRecording(false);
  };

  const toggleRecording = () => {
    if (isRecording) {
      stopRecording();
    } else {
      startRecording();
    }
  };

  const sendMessage = () => {
    if (transcribedText.trim()) {
      onMessageReceived(transcribedText, detectedLanguage?.code || language);
      setTranscribedText('');
      setDetectedLanguage(null);
      setHasRecording(false);
      setAudioBlob(null);
    }
  };

  const playRecording = () => {
    if (audioBlob) {
      const audioUrl = URL.createObjectURL(audioBlob);
      const audio = new Audio(audioUrl);
      audio.play();
    }
  };

  const getLanguageDisplay = () => {
    if (detectedLanguage) {
      return `${detectedLanguage.nativeName} (${Math.round(detectedLanguage.confidence * 100)}% confidence) - ${detectedLanguage.region}`;
    }
    return 'Language detected';
  };

  return (
    <Card className="p-6 bg-gradient-earth border-2">
      <div className="text-center space-y-4">
        <h3 className="text-lg font-semibold text-foreground">
          {language === 'en' ? 'Voice Message' : 
           language === 'tw' ? 'Wo nne nkra' :
           language === 'ee' ? 'Wò nya' :
           language === 'ga' ? 'Wo nyɛ' :
           language === 'fa' ? 'Wo nne nkra' :
           language === 'fr' ? 'Message Vocal' : 'Voice Message'}
        </h3>
        
        <div className="flex flex-col items-center gap-4">
          <Button
            variant={isRecording ? "destructive" : "harvest"}
            size="lg"
            className={`h-20 w-20 rounded-full transition-all duration-300 ${
              isRecording ? 'animate-pulse' : ''
            }`}
            onClick={toggleRecording}
            disabled={!isSpeechRecognitionSupported && !navigator.mediaDevices}
          >
            {isRecording ? (
              <MicOff className="h-8 w-8" />
            ) : (
              <Mic className="h-8 w-8" />
            )}
          </Button>
          
          <p className="text-sm text-muted-foreground max-w-xs">
            {isRecording 
              ? (language === 'en' ? 'Listening... Tap to stop' :
                 language === 'tw' ? 'Tie... Tow na egyae' :
                 language === 'ee' ? 'Dzidzi... Tɔ be nàtso' :
                 language === 'ga' ? 'Tie... Tɔ nà bɔ' :
                 language === 'fa' ? 'Tie... Tow na egyae' :
                 language === 'fr' ? 'Écoute... Appuyez pour arrêter' : 'Listening... Tap to stop')
              : (language === 'en' ? 'Tap to start voice recognition' :
                 language === 'tw' ? 'Tow na fi ase tie wo nne' :
                 language === 'ee' ? 'Tɔ nàdze gɔmedzedze' :
                 language === 'ga' ? 'Tɔ nà bɔ gɔme' :
                 language === 'fa' ? 'Tow na fi ase tie wo nne' :
                 language === 'fr' ? 'Appuyez pour démarrer la reconnaissance vocale' : 'Tap to start voice recognition')
            }
          </p>
          
          {isProcessing && (
            <div className="flex items-center gap-2 text-sm text-muted-foreground">
              <div className="animate-spin rounded-full h-4 w-4 border-b-2 border-primary"></div>
              Processing speech...
            </div>
          )}
          
          {detectedLanguage && (
            <div className="flex items-center gap-2 text-sm bg-primary/10 px-3 py-2 rounded-lg">
              <Languages className="h-4 w-4" />
              {getLanguageDisplay()}
            </div>
          )}
          
          {transcribedText && (
            <div className="w-full max-w-md">
              <p className="text-sm font-medium mb-2">Transcribed Text:</p>
              <div className="bg-background/50 p-3 rounded-lg text-sm text-left">
                {transcribedText}
              </div>
            </div>
          )}
          
          {hasRecording && (
            <div className="flex gap-2">
              {audioBlob && (
                <Button variant="outline" onClick={playRecording} size="sm">
                  <Volume2 className="h-4 w-4 mr-2" />
                  Play
                </Button>
              )}
              <Button variant="ghana" onClick={sendMessage} className="gap-2">
                <Send className="h-4 w-4" />
                {language === 'en' ? 'Send Message' :
                 language === 'tw' ? 'Soma nkra' :
                 language === 'ee' ? 'Dɔ nya' :
                 language === 'ga' ? 'Soma' :
                 language === 'fa' ? 'Soma nkra' :
                 language === 'fr' ? 'Envoyer le Message' : 'Send Message'}
              </Button>
            </div>
          )}
        </div>
        
        {!isSpeechRecognitionSupported && (
          <p className="text-xs text-muted-foreground">
            Speech recognition not supported. Using audio recording instead.
          </p>
        )}
      </div>
    </Card>
  );
};