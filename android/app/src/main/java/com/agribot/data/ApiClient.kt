package com.agribot.data

import com.agribot.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

object ApiClient {
    private val gson = GsonBuilder().setLenient().create()

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttp = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttp)
        .build()

    private val service = retrofit.create(ApiService::class.java)

    suspend fun currentWeather(location: String): String? =
        try { service.currentWeather(location).toString() } catch (e: Exception) { e.message }

    suspend fun weatherForecast(location: String, days: Int = 7): String? =
        try { service.weatherForecast(location, days).toString() } catch (e: Exception) { e.message }

    suspend fun weatherAlerts(location: String): String? =
        try { service.weatherAlerts(location).toString() } catch (e: Exception) { e.message }

    suspend fun marketPrices(location: String?, crop: String?): String? =
        try { service.marketPrices(location, crop).toString() } catch (e: Exception) { e.message }

    suspend fun marketHistory(crop: String, location: String, days: Int = 30): String? =
        try { service.marketHistory(crop, location, days).toString() } catch (e: Exception) { e.message }

    suspend fun marketTrends(location: String?): String? =
        try { service.marketTrends(location).toString() } catch (e: Exception) { e.message }

    suspend fun markets(location: String?): String? =
        try { service.markets(location).toString() } catch (e: Exception) { e.message }

    suspend fun experts(): String? =
        try { service.experts().toString() } catch (e: Exception) { e.message }

    suspend fun farmers(): String? =
        try { service.farmers().toString() } catch (e: Exception) { e.message }

    suspend fun registerFarmer(name: String, location: String, farmSize: String, crops: List<String>): String? =
        try { service.registerFarmer(RegisterFarmerRequest(name, location, farmSize, crops)).toString() } catch (e: Exception) { e.message }

    suspend fun subsidies(): String? =
        try { service.subsidies().toString() } catch (e: Exception) { e.message }

    suspend fun chat(message: String): String? =
        try { service.chat(ChatRequest(message)).toString() } catch (e: Exception) { e.message }
}

data class ChatRequest(val message: String, val language: String = "en")

data class RegisterFarmerRequest(
    val name: String,
    val location: String,
    val farmSize: String,
    val crops: List<String>
)

interface ApiService {
    @GET("weather/current")
    suspend fun currentWeather(@Query("location") location: String): Any

    @GET("weather/forecast")
    suspend fun weatherForecast(@Query("location") location: String, @Query("days") days: Int): Any

    @GET("weather/alerts")
    suspend fun weatherAlerts(@Query("location") location: String): Any

    @GET("market/prices")
    suspend fun marketPrices(
        @Query("location") location: String?,
        @Query("crop") crop: String?
    ): Any

    @GET("market/history")
    suspend fun marketHistory(
        @Query("crop") crop: String,
        @Query("location") location: String,
        @Query("days") days: Int
    ): Any

    @GET("market/trends")
    suspend fun marketTrends(@Query("location") location: String?): Any

    @GET("market/markets")
    suspend fun markets(@Query("location") location: String?): Any

    @GET("experts")
    suspend fun experts(): Any

    @GET("farmers")
    suspend fun farmers(): Any

    @POST("farmers")
    suspend fun registerFarmer(@Body body: RegisterFarmerRequest): Any

    @GET("subsidies")
    suspend fun subsidies(): Any

    @POST("chat")
    suspend fun chat(@Body body: ChatRequest): Any
}


