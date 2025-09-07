package com.agribot.ai

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel(application: Application) : AndroidViewModel(application) {
    
    private val claudeService = ClaudeService(application)
    private val imageService = ImageGenerationService(application)
    
    private val _chatState = MutableStateFlow(ChatState())
    val chatState: StateFlow<ChatState> = _chatState.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    fun sendMessage(message: String, language: String) {
        if (message.isBlank()) {
            // Send a welcome message to get AI suggestions
            sendWelcomeMessage(language)
            return
        }
        
        // Add user message to chat
        val userMessage = ChatMessage(
            content = message,
            isUser = true,
            language = language
        )
        
        _chatState.value = _chatState.value.copy(
            messages = _chatState.value.messages + userMessage
        )
        
        // Show loading state
        _isLoading.value = true
        
        viewModelScope.launch {
            try {
                // Convert chat history to Claude format
                val conversationHistory = _chatState.value.messages.map { chatMsg ->
                    ClaudeMessage(
                        role = if (chatMsg.isUser) "user" else "assistant",
                        content = listOf(
                            ClaudeContent(
                                type = "text",
                                text = chatMsg.content
                            )
                        )
                    )
                }
                
                val result = claudeService.sendMessage(message, language, conversationHistory = conversationHistory)
                
                result.fold(
                    onSuccess = { response ->
                        val aiMessage = ChatMessage(
                            content = response.message,
                            isUser = false,
                            language = language
                        )
                        
                        _chatState.value = _chatState.value.copy(
                            messages = _chatState.value.messages + aiMessage,
                            suggestions = response.suggestions
                        )
                    },
                    onFailure = { exception ->
                        // Add error message
                        val errorMessage = ChatMessage(
                            content = getLocalizedErrorMessage(exception.message ?: "Unknown error", language),
                            isUser = false,
                            language = language
                        )
                        
                        _chatState.value = _chatState.value.copy(
                            messages = _chatState.value.messages + errorMessage
                        )
                    }
                )
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun generateImage(prompt: String, language: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                
                val result = imageService.generateImage(prompt, language)
                
                result.fold(
                    onSuccess = { response ->
                        _chatState.value = _chatState.value.copy(
                            lastGeneratedImage = response
                        )
                    },
                    onFailure = { exception ->
                        // Handle image generation error
                        val errorMessage = ChatMessage(
                            content = getLocalizedErrorMessage("Failed to generate image: ${exception.message}", language),
                            isUser = false,
                            language = language
                        )
                        
                        _chatState.value = _chatState.value.copy(
                            messages = _chatState.value.messages + errorMessage
                        )
                    }
                )
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun clearChat() {
        _chatState.value = ChatState()
    }
    
    fun addQuickQuestion(question: String, language: String) {
        sendMessage(question, language)
    }
    
    private fun sendWelcomeMessage(language: String) {
        _isLoading.value = true
        
        viewModelScope.launch {
            try {
                val welcomePrompt = when (language) {
                    "English" -> "Hello! I'm Agribot, your agricultural assistant. How can I help you with farming today?"
                    "Twi" -> "Akaaba! Me yɛ Agribot, wo kuayɛɛ ho kyekyefo. Ɛdeɛn na metumi aboa wo wɔ kuayɛɛ ho nnɛ?"
                    "Ewe" -> "Hello! Menye Agribot, wo agbɔgbɔ ƒe kpekpe. Aleke makahe wu le agbɔgbɔ me ega?"
                    "Ga" -> "Hello! Me yɛ Agribot, wo kuayɛɛ ho kyekyefo. Ɛdeɛn na metumi aboa wo wɔ kuayɛɛ ho nnɛ?"
                    "Dagbani" -> "Hello! Me yɛ Agribot, wo kuayɛɛ ho kyekyefo. Ɛdeɛn na metumi aboa wo wɔ kuayɛɛ ho nnɛ?"
                    "Fante" -> "Hello! Me yɛ Agribot, wo kuayɛɛ ho kyekyefo. Ɛdeɛn na metumi aboa wo wɔ kuayɛɛ ho nnɛ?"
                    "Hausa" -> "Sannu! Ni Agribot ne, mai taimako da noma. Yaya zan iya taimake ku da noma yau?"
                    else -> "Hello! I'm Agribot, your agricultural assistant. How can I help you with farming today?"
                }
                
                val result = claudeService.sendMessage(welcomePrompt, language)
                
                result.fold(
                    onSuccess = { response ->
                        val aiMessage = ChatMessage(
                            content = response.message,
                            isUser = false,
                            language = language
                        )
                        
                        _chatState.value = _chatState.value.copy(
                            messages = listOf(aiMessage),
                            suggestions = response.suggestions
                        )
                    },
                    onFailure = { exception ->
                        // Add default welcome message if API fails
                        val welcomeMessage = ChatMessage(
                            content = welcomePrompt,
                            isUser = false,
                            language = language
                        )
                        
                        _chatState.value = _chatState.value.copy(
                            messages = listOf(welcomeMessage),
                            suggestions = generateDefaultSuggestions(language)
                        )
                    }
                )
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    private fun generateDefaultSuggestions(language: String): List<String> {
        return when (language) {
            "English" -> listOf(
                "Tell me about crops",
                "How to control pests?",
                "Market prices",
                "Weather advice",
                "Soil health tips"
            )
            "Twi" -> listOf(
                "Ka akyerɛ me nea ɛfa nhaban ho",
                "Sɛnea yɛbɛtumi akyerɛ nkaa?",
                "Tiaa bo",
                "Sɛnkyerɛnne akwankyerɛ",
                "Asaase ho akwankyerɛ"
            )
            else -> listOf(
                "Tell me about crops",
                "How to control pests?",
                "Market prices",
                "Weather advice",
                "Soil health tips"
            )
        }
    }
    
    private fun getLocalizedErrorMessage(error: String, language: String): String {
        return when (language) {
            "English" -> "Sorry, I encountered an error: $error"
            "Twi" -> "Yɛ, me nyaɛ error: $error"
            "Ewe" -> "Yɛ, me nyaɛ error: $error"
            "Ga" -> "Yɛ, me nyaɛ error: $error"
            "Dagbani" -> "Yɛ, me nyaɛ error: $error"
            "Fante" -> "Yɛ, me nyaɛ error: $error"
            "Hausa" -> "Yɛ, me nyaɛ error: $error"
            else -> "Sorry, I encountered an error: $error"
        }
    }
}

data class ChatState(
    val messages: List<ChatMessage> = emptyList(),
    val suggestions: List<String> = emptyList(),
    val lastGeneratedImage: ImageGenerationResponse? = null
)
