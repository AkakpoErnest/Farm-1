package com.agribot.ai

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ImageGenerationService(private val context: Context) {
    
    companion object {
        private const val TAG = "ImageGenerationService"
        private const val BASE_URL = "https://api.stability.ai/"
        private const val API_KEY = "sk-aU6B4e6wWiAxpARaW0f5zCZA24IkLRnRx3d1PquGknsNpr0g"
        private const val ENGINE_ID = "stable-diffusion-xl-1024-v1-0"
    }
    
    private val apiService: StabilityApiService by lazy {
        createApiService()
    }
    
    private fun createApiService(): StabilityApiService {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
        
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        
        return retrofit.create(StabilityApiService::class.java)
    }
    
    suspend fun generateImage(
        prompt: String,
        language: String,
        style: String = "realistic"
    ): Result<ImageGenerationResponse> = withContext(Dispatchers.IO) {
        try {
            // Translate prompt to English for better image generation
            val englishPrompt = translatePromptToEnglish(prompt, language)
            val fullPrompt = "$englishPrompt, agricultural, farming, Ghana, high quality, detailed"
            
            Log.d(TAG, "Generating image with prompt: $fullPrompt")
            
            val request = StabilityRequest(
                textPrompts = listOf(
                    StabilityTextPrompt(
                        text = fullPrompt,
                        weight = 1.0f
                    )
                ),
                cfgScale = 7.0f,
                height = 1024,
                width = 1024,
                samples = 1,
                steps = 30
            )
            
            val response = apiService.generateImage(API_KEY, request = request)
            
            if (response.isSuccessful) {
                val stabilityResponse = response.body()
                if (stabilityResponse != null && stabilityResponse.artifacts.isNotEmpty()) {
                    val imageData = stabilityResponse.artifacts.first()
                    val imageUrl = "data:image/png;base64,${imageData.base64}"
                    
                    Result.success(
                        ImageGenerationResponse(
                            imageUrl = imageUrl,
                            prompt = fullPrompt,
                            language = language
                        )
                    )
                } else {
                    Result.failure(Exception("Empty response from Stability AI"))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e(TAG, "Image API Error: ${response.code()} - ${response.message()}")
                Log.e(TAG, "Error Body: $errorBody")
                
                // Return failure instead of fallback for API errors
                Result.failure(Exception("API Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error generating image", e)
            
            // Only provide fallback for network/technical errors, not API errors
            if (e.message?.contains("API Error") == true) {
                Result.failure(e)
            } else {
                // Provide fallback image for technical errors
                val fallbackUrl = getFallbackImageUrl(prompt, language)
                Result.success(
                    ImageGenerationResponse(
                        imageUrl = fallbackUrl,
                        prompt = prompt,
                        language = language
                    )
                )
            }
        }
    }
    
    private fun translatePromptToEnglish(prompt: String, language: String): String {
        return when (language) {
            "English" -> prompt
            "Twi" -> when {
                prompt.contains("nhaban") -> "crops"
                prompt.contains("asaase") -> "soil"
                prompt.contains("nsu") -> "water"
                prompt.contains("wiem") -> "weather"
                else -> prompt
            }
            "Ewe" -> when {
                prompt.contains("agbɔgbɔ") -> "agriculture"
                prompt.contains("anyigba") -> "soil"
                prompt.contains("tsi") -> "water"
                prompt.contains("dzɔdzɔ") -> "weather"
                else -> prompt
            }
            "Ga" -> when {
                prompt.contains("kuayɛɛ") -> "farming"
                prompt.contains("asaase") -> "soil"
                prompt.contains("nsu") -> "water"
                prompt.contains("wiem") -> "weather"
                else -> prompt
            }
            "Dagbani" -> when {
                prompt.contains("kuayɛɛ") -> "farming"
                prompt.contains("tia") -> "soil"
                prompt.contains("kom") -> "water"
                prompt.contains("wiem") -> "weather"
                else -> prompt
            }
            "Fante" -> when {
                prompt.contains("kuayɛɛ") -> "farming"
                prompt.contains("asaase") -> "soil"
                prompt.contains("nsu") -> "water"
                prompt.contains("wiem") -> "weather"
                else -> prompt
            }
            "Hausa" -> when {
                prompt.contains("noma") -> "farming"
                prompt.contains("ƙasa") -> "soil"
                prompt.contains("ruwa") -> "water"
                prompt.contains("yanayi") -> "weather"
                else -> prompt
            }
            else -> prompt
        }
    }
    
    private fun getFallbackImageUrl(prompt: String, language: String): String {
        // Generate a themed placeholder image
        val theme = when {
            prompt.contains("crop") || prompt.contains("plant") -> "agricultural"
            prompt.contains("soil") -> "soil"
            prompt.contains("water") -> "irrigation"
            prompt.contains("weather") -> "weather"
            else -> "farming"
        }
        
        return "https://via.placeholder.com/1024x1024/16a34a/ffffff?text=${theme.replace(" ", "+")}+Image"
    }
}

// Stability AI API Models
data class StabilityRequest(
    val textPrompts: List<StabilityTextPrompt>,
    val cfgScale: Float,
    val height: Int,
    val width: Int,
    val samples: Int,
    val steps: Int
)

data class StabilityTextPrompt(
    val text: String,
    val weight: Float
)

data class StabilityResponse(
    val artifacts: List<StabilityArtifact>
)

data class StabilityArtifact(
    val base64: String,
    val seed: Long,
    val finishReason: String
)
