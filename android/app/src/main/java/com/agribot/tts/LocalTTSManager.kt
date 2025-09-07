package com.agribot.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import java.util.*

class LocalTTSManager(private val context: Context) {
    private var tts: TextToSpeech? = null
    private var isInitialized by mutableStateOf(false)
    
    companion object {
        private const val TAG = "LocalTTSManager"
    }
    
    fun initialize(onReady: () -> Unit = {}) {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                isInitialized = true
                setupTTS()
                onReady()
                Log.d(TAG, "TTS initialization successful")
            } else {
                Log.e(TAG, "TTS initialization failed")
            }
        }
    }
    
    private fun setupTTS() {
        tts?.let { textToSpeech ->
            // Set speech rate and pitch for better pronunciation
            textToSpeech.setSpeechRate(0.8f) // Slower for better understanding
            textToSpeech.setPitch(1.0f)
        }
    }
    
    fun speak(text: String, language: String) {
        if (!isInitialized) {
            Log.w(TAG, "TTS not initialized yet")
            return
        }
        
        tts?.let { textToSpeech ->
            // Set language with fallback strategy
            val languageSet = setLanguageWithFallback(textToSpeech, language)
            
            if (languageSet) {
                // For Ghanaian languages, slow down speech for better pronunciation
                if (isGhanaianLanguage(language)) {
                    textToSpeech.setSpeechRate(0.7f)
                    textToSpeech.setPitch(1.1f)
                }
                
                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
                Log.d(TAG, "Speaking: $text in $language")
            } else {
                Log.w(TAG, "Could not set language for: $language")
            }
        }
    }
    
    private fun setLanguageWithFallback(tts: TextToSpeech, language: String): Boolean {
        val primaryLocale = getLocaleForLanguage(language)
        val result = tts.setLanguage(primaryLocale)
        
        if (result == TextToSpeech.LANG_AVAILABLE || result == TextToSpeech.LANG_COUNTRY_AVAILABLE) {
            Log.d(TAG, "Primary language set successfully: $primaryLocale")
            return true
        }
        
        // Try fallback locales for Ghanaian languages
        val fallbackLocales = getFallbackLocales(language)
        for (fallbackLocale in fallbackLocales) {
            val fallbackResult = tts.setLanguage(fallbackLocale)
            if (fallbackResult == TextToSpeech.LANG_AVAILABLE || fallbackResult == TextToSpeech.LANG_COUNTRY_AVAILABLE) {
                Log.d(TAG, "Fallback language set: $fallbackLocale")
                return true
            }
        }
        
        // Final fallback to English
        val englishResult = tts.setLanguage(Locale.US)
        if (englishResult == TextToSpeech.LANG_AVAILABLE || englishResult == TextToSpeech.LANG_COUNTRY_AVAILABLE) {
            Log.d(TAG, "Using English as final fallback")
            return true
        }
        
        return false
    }
    
    private fun getLocaleForLanguage(language: String): Locale {
        return when (language) {
            "English" -> Locale.US
            "Twi", "Akan" -> Locale("ak", "GH") // Akan (includes Twi)
            "Ewe" -> Locale("ee", "GH") // Ewe
            "Ga" -> Locale("gaa", "GH") // Ga
            "Dagbani" -> Locale("dag", "GH") // Dagbani
            "Fante" -> Locale("fat", "GH") // Fante
            "Hausa" -> Locale("ha", "NG") // Hausa (Nigeria has better support)
            else -> Locale.US
        }
    }
    
    private fun getFallbackLocales(language: String): List<Locale> {
        return when (language) {
            "Twi", "Fante", "Akan" -> listOf(
                Locale("ak", "GH"), // Akan
                Locale("tw", "GH"), // Twi
                Locale("en", "GH"), // English Ghana
                Locale.UK,
                Locale.US
            )
            "Ewe" -> listOf(
                Locale("ee", "GH"), // Ewe Ghana
                Locale("ee", "TG"), // Ewe Togo
                Locale("en", "GH"),
                Locale.UK,
                Locale.US
            )
            "Ga" -> listOf(
                Locale("gaa", "GH"), // Ga
                Locale("en", "GH"),
                Locale.UK,
                Locale.US
            )
            "Dagbani" -> listOf(
                Locale("dag", "GH"), // Dagbani
                Locale("en", "GH"),
                Locale.UK,
                Locale.US
            )
            "Hausa" -> listOf(
                Locale("ha", "NG"), // Hausa Nigeria
                Locale("ha", "NE"), // Hausa Niger
                Locale("ha", "GH"), // Hausa Ghana
                Locale("en", "NG"),
                Locale.US
            )
            else -> listOf(Locale.US)
        }
    }
    
    private fun isGhanaianLanguage(language: String): Boolean {
        return language in listOf("Twi", "Ewe", "Ga", "Dagbani", "Fante", "Hausa")
    }
    
    fun stop() {
        tts?.stop()
    }
    
    fun shutdown() {
        tts?.shutdown()
        isInitialized = false
    }
    
    fun isReady(): Boolean = isInitialized
    
    // Check available languages on the device
    fun getAvailableLanguages(): Set<Locale> {
        return tts?.availableLanguages ?: emptySet()
    }
    
    // Check if a specific language is available
    fun isLanguageAvailable(language: String): Boolean {
        tts?.let { textToSpeech ->
            val locale = getLocaleForLanguage(language)
            val result = textToSpeech.isLanguageAvailable(locale)
            return result == TextToSpeech.LANG_AVAILABLE || result == TextToSpeech.LANG_COUNTRY_AVAILABLE
        }
        return false
    }
    
    // Get language availability status
    fun getLanguageAvailabilityStatus(language: String): String {
        tts?.let { textToSpeech ->
            val locale = getLocaleForLanguage(language)
            return when (textToSpeech.isLanguageAvailable(locale)) {
                TextToSpeech.LANG_AVAILABLE -> "Fully Available"
                TextToSpeech.LANG_COUNTRY_AVAILABLE -> "Country Available"
                TextToSpeech.LANG_COUNTRY_VAR_AVAILABLE -> "Country Variant Available"
                TextToSpeech.LANG_MISSING_DATA -> "Data Missing"
                TextToSpeech.LANG_NOT_SUPPORTED -> "Not Supported"
                else -> "Unknown"
            }
        }
        return "TTS Not Initialized"
    }
}

