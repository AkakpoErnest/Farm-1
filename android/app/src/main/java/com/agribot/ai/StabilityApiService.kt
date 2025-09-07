package com.agribot.ai

import retrofit2.Response
import retrofit2.http.*

interface StabilityApiService {
    
    @POST("v1/generation/{engine_id}/text-to-image")
    suspend fun generateImage(
        @Header("Authorization") apiKey: String,
        @Path("engine_id") engineId: String = "stable-diffusion-xl-1024-v1-0",
        @Body request: StabilityRequest
    ): Response<StabilityResponse>
}

