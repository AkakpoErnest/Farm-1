package com.agribot.ai

import retrofit2.Response
import retrofit2.http.*

interface ClaudeApiService {
    
    @POST("v1/messages")
    suspend fun sendMessage(
        @Header("x-api-key") apiKey: String,
        @Header("anthropic-version") version: String = "2023-06-01",
        @Header("content-type") contentType: String = "application/json",
        @Body request: ClaudeRequest
    ): Response<ClaudeResponse>
    
    @POST("v1/messages")
    suspend fun generateImage(
        @Header("x-api-key") apiKey: String,
        @Header("anthropic-version") version: String = "2023-06-01",
        @Header("content-type") contentType: String = "application/json",
        @Body request: ClaudeRequest
    ): Response<ClaudeResponse>
}
