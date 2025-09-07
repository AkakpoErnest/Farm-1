package com.agribot.ai

import com.google.gson.annotations.SerializedName

// Claude API Request Models
data class ClaudeRequest(
    val model: String = "claude-3-haiku-20240307",
    @SerializedName("max_tokens") val maxTokens: Int = 1000,
    val messages: List<ClaudeMessage>,
    val system: String? = null
)

data class ClaudeMessage(
    val role: String,
    val content: List<ClaudeContent>
)

data class ClaudeContent(
    val type: String,
    val text: String? = null,
    val source: ClaudeImageSource? = null
)

data class ClaudeImageSource(
    val type: String = "base64",
    val mediaType: String,
    val data: String
)

// Claude API Response Models
data class ClaudeResponse(
    val id: String,
    val type: String,
    val role: String,
    val content: List<ClaudeResponseContent>,
    val model: String,
    @SerializedName("stop_reason") val stopReason: String?,
    @SerializedName("stop_sequence") val stopSequence: String?,
    val usage: ClaudeUsage
)

data class ClaudeResponseContent(
    val type: String,
    val text: String? = null
)

data class ClaudeUsage(
    @SerializedName("input_tokens") val inputTokens: Int,
    @SerializedName("output_tokens") val outputTokens: Int
)

// Chat Models for App
data class ChatMessage(
    val id: String = System.currentTimeMillis().toString(),
    val content: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis(),
    val language: String = "English"
)

data class ChatRequest(
    val message: String,
    val language: String,
    val context: String? = null
)

data class ChatResponse(
    val message: String,
    val language: String,
    val suggestions: List<String> = emptyList()
)

// Image Generation Models
data class ImageGenerationRequest(
    val prompt: String,
    val language: String,
    val style: String = "realistic",
    val size: String = "1024x1024"
)

data class ImageGenerationResponse(
    val imageUrl: String,
    val prompt: String,
    val language: String
)
