package com.agribot.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Image
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import com.agribot.tts.LocalTTSManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import androidx.compose.ui.draw.alpha
import androidx.compose.animation.core.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import kotlin.math.sin
import com.agribot.R
import com.agribot.ui.screens.LanguageTranslations.getLocalizedText
import com.agribot.data.User
import com.agribot.ui.auth.AuthViewModel
import com.agribot.ui.auth.AuthEvent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.style.TextAlign
import com.agribot.data.UserRole
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import androidx.compose.foundation.BorderStroke
import com.agribot.ai.ChatViewModel
import com.agribot.ai.ChatMessage as AIChatMessage
import java.util.*

// Data class for chat messages (keeping for backward compatibility)
data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

// Language code mapping for speech recognition and TTS
fun getLanguageCode(language: String): String {
    return when (language) {
        "English" -> "en-US"
        "Twi" -> "en-GH" // Use English with Ghana locale for Twi (fallback)
        "Ewe" -> "en-GH" // Use English with Ghana locale for Ewe (fallback)
        "Ga" -> "en-GH" // Use English with Ghana locale for Ga (fallback)
        "Dagbani" -> "en-GH" // Use English with Ghana locale for Dagbani (fallback)
        "Fante" -> "en-GH" // Use English with Ghana locale for Fante (fallback)
        "Hausa" -> "ha-NG" // Hausa is supported in Nigeria
        else -> "en-US"
    }
}

// Function to detect if user is asking for an image
fun isImageRequest(message: String): Boolean {
    val imageKeywords = listOf(
        "image", "picture", "photo", "draw", "show me", "generate", "create",
        "img", "pic", "visual", "illustration", "diagram", "chart"
    )
    
    val lowerMessage = message.lowercase()
    return imageKeywords.any { keyword -> lowerMessage.contains(keyword) }
}

@Composable
fun ChatScreen(selectedLanguage: String = "English") {
    var messageText by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf(listOf<ChatMessage>()) }
    var isLoading by remember { mutableStateOf(false) }
    var isListening by remember { mutableStateOf(false) }
    
    val context = LocalContext.current
    
    // AI Chat ViewModel
    val chatViewModel: ChatViewModel = viewModel()
    val chatState by chatViewModel.chatState.collectAsState()
    val aiIsLoading by chatViewModel.isLoading.collectAsState()
    
    // Enhanced TTS with LocalTTSManager
    var localTTSManager by remember { mutableStateOf<LocalTTSManager?>(null) }
    
    // Initialize Enhanced TTS
    LaunchedEffect(Unit) {
        localTTSManager = LocalTTSManager(context).apply {
            initialize()
        }
    }
    
    // Cleanup TTS
    DisposableEffect(Unit) {
        onDispose {
            localTTSManager?.stop()
            localTTSManager?.shutdown()
        }
    }
    
    // Speech recognition launcher
    val speechLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            val spokenText = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0) ?: ""
            messageText = spokenText
            isListening = false
        } else {
            isListening = false
        }
    }
    
    // Add welcome message and sync with AI chat state
    LaunchedEffect(Unit) {
        if (chatState.messages.isEmpty()) {
            // Initialize with welcome message
            chatViewModel.sendMessage("", selectedLanguage) // This will trigger welcome message
        }
    }
    
    // Sync local messages with AI chat state
    LaunchedEffect(chatState.messages) {
        messages = chatState.messages.map { aiMessage ->
            ChatMessage(
                text = aiMessage.content,
                isUser = aiMessage.isUser,
                timestamp = aiMessage.timestamp
            )
        }
    }
    
    // Update loading state
    LaunchedEffect(aiIsLoading) {
        isLoading = aiIsLoading
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
    ) {
        // Header
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            shadowElevation = 4.dp
        ) {
            Text(
                text = getLocalizedText("chat_title", selectedLanguage),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        // Messages
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages) { message ->
                                                            ChatMessageItem(
                        message = message, 
                        selectedLanguage = selectedLanguage,
                        onPlayAudio = {
                            // Play AI response audio with enhanced local language support
                            localTTSManager?.speak(message.text, selectedLanguage)
                        }
                    )
                    
                    // Show generated image if available
                    chatState.lastGeneratedImage?.let { imageResponse ->
                        if (imageResponse.prompt.contains(message.text, ignoreCase = true)) {
                            Card(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White
                                ),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp)
                                ) {
                                    Text(
                                        text = "Generated Image:",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = Color.Gray,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )
                                    
                                    // Display the generated image
                                    AsyncImage(
                                        model = imageResponse.imageUrl,
                                        contentDescription = "Generated image for: ${imageResponse.prompt}",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(200.dp)
                                            .clip(RoundedCornerShape(8.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                    
                                    Text(
                                        text = imageResponse.prompt,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray,
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                }
                            }
                        }
                    }
            }
            
            if (isLoading) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Card(
                            modifier = Modifier.padding(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFE3F2FD)
                            )
                        ) {
                            Text(
                                text = getLocalizedText("chat_typing", selectedLanguage),
                                modifier = Modifier.padding(12.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF1976D2)
                            )
                        }
                    }
                }
            }
        }

        // Input field
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    modifier = Modifier.weight(1f),
                    placeholder = {
                        Text(getLocalizedText("chat_placeholder", selectedLanguage))
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = Color.Gray,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                isListening = true
                                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                                    putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                                    putExtra(RecognizerIntent.EXTRA_LANGUAGE, getLanguageCode(selectedLanguage))
                                    putExtra(RecognizerIntent.EXTRA_PROMPT, getLocalizedText("chat_voice_prompt", selectedLanguage))
                                }
                                try {
                                    speechLauncher.launch(intent)
                                } catch (e: Exception) {
                                    isListening = false
                                    // Handle error - could show a toast or alert
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (isListening) Icons.Filled.MicOff else Icons.Filled.Mic,
                                contentDescription = if (isListening) "Stop listening" else "Start voice input",
                                tint = if (isListening) Color.Red else MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    singleLine = true
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Button(
                    onClick = {
                        if (messageText.isNotBlank()) {
                            val userText = messageText
                            messageText = ""
                            
                            // Send message to AI service
                            chatViewModel.sendMessage(userText, selectedLanguage)
                            
                            // Check if user is asking for an image and automatically generate one
                            if (isImageRequest(userText)) {
                                chatViewModel.generateImage(userText, selectedLanguage)
                            }
                        }
                    },
                    enabled = messageText.isNotBlank()
                ) {
                    Text(getLocalizedText("chat_send", selectedLanguage))
                }
            }
        }
    }
}

@Composable
fun ChatMessageItem(message: ChatMessage, selectedLanguage: String, onPlayAudio: (() -> Unit)? = null) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .widthIn(max = 280.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (message.isUser) 
                    MaterialTheme.colorScheme.primary 
                else 
                    Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column {
                Text(
                    text = message.text,
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (message.isUser) Color.White else Color.Black
                )
                
                // Add play button for AI responses
                if (!message.isUser && onPlayAudio != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(
                            onClick = onPlayAudio,
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.PlayArrow,
                                contentDescription = "Play audio",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

// AI response generation function
fun generateAIResponse(userMessage: String, selectedLanguage: String): String {
    val lowerMessage = userMessage.lowercase()
    
    return when {
        lowerMessage.contains("subsidy") || lowerMessage.contains("subsidies") -> {
            getLocalizedText("chat_subsidies_response", selectedLanguage)
        }
        lowerMessage.contains("weather") -> {
            getLocalizedText("chat_weather_response", selectedLanguage)
        }
        lowerMessage.contains("market") || lowerMessage.contains("price") -> {
            getLocalizedText("chat_market_response", selectedLanguage)
        }
        lowerMessage.contains("fertilizer") || lowerMessage.contains("fertiliser") -> {
            getLocalizedText("chat_fertilizer_response", selectedLanguage)
        }
        lowerMessage.contains("pest") || lowerMessage.contains("disease") -> {
            getLocalizedText("chat_pest_response", selectedLanguage)
        }
        lowerMessage.contains("irrigation") || lowerMessage.contains("water") -> {
            getLocalizedText("chat_irrigation_response", selectedLanguage)
        }
        lowerMessage.contains("seed") || lowerMessage.contains("planting") -> {
            getLocalizedText("chat_seed_response", selectedLanguage)
        }
        lowerMessage.contains("harvest") || lowerMessage.contains("yield") -> {
            getLocalizedText("chat_harvest_response", selectedLanguage)
        }
        lowerMessage.contains("government") -> {
            getLocalizedText("chat_government_response", selectedLanguage)
        }
        else -> {
            getLocalizedText("chat_default_response", selectedLanguage)
        }
    }
}

@Composable
fun WeatherScreen(selectedLanguage: String = "English") {
    // Animation states
    var animationTriggered by remember { mutableStateOf(false) }
    
    // Floating animation for weather elements
    val infiniteTransition = rememberInfiniteTransition(label = "weather_floating")
    val floatingOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 20f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "floating_offset"
    )
    
    // Background scale animation
    val backgroundScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "background_scale"
    )
    
    // Fade in animation
    val fadeInAlpha by animateFloatAsState(
        targetValue = if (animationTriggered) 1f else 0f,
        animationSpec = tween(1000),
        label = "fade_in"
    )
    
    // Scale animation for content
    val contentScale by animateFloatAsState(
        targetValue = if (animationTriggered) 1f else 0.8f,
        animationSpec = tween(800, easing = EaseOutBack),
        label = "content_scale"
    )
    
    LaunchedEffect(Unit) {
        animationTriggered = true
    }
    
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Animated Background Image
        Image(
            painter = painterResource(id = R.drawable.weather_background),
            contentDescription = "Weather Background",
            modifier = Modifier
                .fillMaxSize()
                .scale(backgroundScale)
                .alpha(0.3f),
            contentScale = ContentScale.Crop
        )
        
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .alpha(fadeInAlpha)
                .scale(contentScale)
        ) {
        // Header
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
                shadowElevation = 4.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = getLocalizedText("weather_title", selectedLanguage),
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Accra, Ghana",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }
        }

        // Current Weather Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .graphicsLayer {
                        translationY = floatingOffset
                    },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Temperature with pulsing animation
                    val temperatureScale by infiniteTransition.animateFloat(
                        initialValue = 1f,
                        targetValue = 1.1f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(2000, easing = EaseInOutSine),
                            repeatMode = RepeatMode.Reverse
                        ),
                        label = "temperature_pulse"
                    )
                    
                    Text(
                        text = "28°C",
                        style = MaterialTheme.typography.displayLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.scale(temperatureScale)
                    )
                    Text(
                        text = getLocalizedText("weather_partly_cloudy", selectedLanguage),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        WeatherDetail(
                            label = getLocalizedText("weather_humidity", selectedLanguage),
                            value = "65%",
                            selectedLanguage = selectedLanguage
                        )
                        WeatherDetail(
                            label = getLocalizedText("weather_wind", selectedLanguage),
                            value = "12 km/h",
                            selectedLanguage = selectedLanguage
                        )
                        WeatherDetail(
                            label = getLocalizedText("weather_visibility", selectedLanguage),
                            value = "10 km",
                            selectedLanguage = selectedLanguage
                        )
                    }
                }
            }
        }

        // 7-Day Forecast
        item {
            Text(
                text = getLocalizedText("weather_7day_forecast", selectedLanguage),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        items(7) { day ->
            // Staggered slide-in animation for each forecast item
            val slideOffset by animateFloatAsState(
                targetValue = if (animationTriggered) 0f else 300f,
                animationSpec = tween(
                    durationMillis = 600,
                    delayMillis = day * 100,
                    easing = EaseOutCubic
                ),
                label = "forecast_slide_$day"
            )
            
            DayForecast(
                day = when (day) {
                    0 -> "Today"
                    1 -> "Tomorrow"
                    else -> "Day ${day + 1}"
                },
                temperature = "28°C",
                condition = getLocalizedText("weather_partly_cloudy", selectedLanguage),
                selectedLanguage = selectedLanguage,
                modifier = Modifier
                    .graphicsLayer {
                        translationX = slideOffset
                    }
            )
        }

        // Weather Alerts
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFF3E0)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = getLocalizedText("weather_alerts", selectedLanguage),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE65100)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "No severe weather alerts at this time.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFE65100)
                    )
                }
            }
        }

        // Agricultural Advice
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE8F5E8)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = getLocalizedText("weather_agricultural_advice", selectedLanguage),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E7D32)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Current weather conditions are ideal for planting maize and vegetables. Soil moisture is adequate. Consider applying fertilizer before the next rainfall.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF2E7D32)
                    )
                }
            }
        }
        }
    }
}

@Composable
fun WeatherDetail(
    label: String,
    value: String,
    selectedLanguage: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}

@Composable
fun DayForecast(
    day: String,
    temperature: String,
    condition: String,
    selectedLanguage: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = day,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = condition,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Text(
                text = temperature,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun MarketScreen(selectedLanguage: String = "English") {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
    ) {
        // Header
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
                shadowElevation = 4.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = getLocalizedText("market_title", selectedLanguage),
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Live market data and analysis",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }
        }

        // Market Overview
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = getLocalizedText("market_overview", selectedLanguage),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        MarketStat(
                            label = "Trend",
                            value = "↗️ Up",
                            color = Color(0xFF4CAF50)
                        )
                        MarketStat(
                            label = "Volume",
                            value = "High",
                            color = Color(0xFF2196F3)
                        )
                        MarketStat(
                            label = "Volatility",
                            value = "Low",
                            color = Color(0xFFFF9800)
                        )
                    }
                }
            }
        }

        // Current Prices Section
        item {
            Text(
                text = getLocalizedText("market_current_prices", selectedLanguage),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        // Price Items
        items(getPriceItems()) { priceItem ->
            PriceItemCard(priceItem = priceItem, selectedLanguage = selectedLanguage)
        }

        // Market Analysis
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE3F2FD)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = getLocalizedText("market_analysis", selectedLanguage),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1976D2)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Market shows upward trend for grains. Maize prices increased by 8% this week. Rice prices stable. Vegetable prices fluctuating due to weather conditions. Good time to sell stored grains.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF1976D2)
                    )
                }
            }
        }

        // Trading Recommendations
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE8F5E8)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Trading Recommendations",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E7D32)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    TradingRecommendation(
                        action = getLocalizedText("market_buy", selectedLanguage),
                        crop = "Maize",
                        reason = "Prices expected to rise further",
                        color = Color(0xFF4CAF50)
                    )
                    
                    TradingRecommendation(
                        action = getLocalizedText("market_sell", selectedLanguage),
                        crop = "Stored Rice",
                        reason = "Current prices are favorable",
                        color = Color(0xFFF44336)
                    )
                    
                    TradingRecommendation(
                        action = getLocalizedText("market_wait", selectedLanguage),
                        crop = "Tomatoes",
                        reason = "Prices too volatile",
                        color = Color(0xFFFF9800)
                    )
                }
            }
        }
    }
}

@Composable
fun MarketStat(
    label: String,
    value: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}

@Composable
fun TradingRecommendation(
    action: String,
    crop: String,
    reason: String,
    color: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = action,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = color,
            modifier = Modifier.width(60.dp)
        )
        Text(
            text = crop,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.width(80.dp)
        )
        Text(
            text = reason,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            modifier = Modifier.weight(1f)
        )
    }
}

// Data classes and helper functions
data class PriceItem(
    val crop: String,
    val currentPrice: String,
    val previousPrice: String,
    val change: String,
    val changePercent: String,
    val trend: String
)

fun getPriceItems(): List<PriceItem> {
    return listOf(
        PriceItem("Maize", "GH₵ 2.50", "GH₵ 2.30", "+0.20", "+8.7%", "↗️"),
        PriceItem("Rice", "GH₵ 4.20", "GH₵ 4.20", "0.00", "0.0%", "→"),
        PriceItem("Tomatoes", "GH₵ 3.80", "GH₵ 4.10", "-0.30", "-7.3%", "↘️"),
        PriceItem("Cassava", "GH₵ 1.20", "GH₵ 1.15", "+0.05", "+4.3%", "↗️"),
        PriceItem("Yam", "GH₵ 2.80", "GH₵ 2.70", "+0.10", "+3.7%", "↗️"),
        PriceItem("Plantain", "GH₵ 1.50", "GH₵ 1.60", "-0.10", "-6.3%", "↘️")
    )
}

@Composable
fun PriceItemCard(priceItem: PriceItem, selectedLanguage: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = priceItem.crop,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Previous: ${priceItem.previousPrice}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
            
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = priceItem.currentPrice,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = priceItem.change,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (priceItem.change.startsWith("+")) Color(0xFF4CAF50) else Color(0xFFF44336)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = priceItem.trend,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileScreen(selectedLanguage: String = "English", currentUser: User? = null, authViewModel: AuthViewModel? = null) {
    val localAuthViewModel = authViewModel ?: viewModel<AuthViewModel>()
    var showEditDialog by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }
    var showHelpDialog by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
    ) {
        // Enhanced Header with Profile Info
        item {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Background with gradient overlay
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp)
                    ) {
                        // Background pattern
                        androidx.compose.foundation.Canvas(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            val width = size.width
                            val height = size.height
                            
                            // Draw subtle pattern
                            for (i in 0..10) {
                                drawCircle(
                                    color = Color.White.copy(alpha = 0.05f),
                                    radius = 50f + i * 20f,
                                    center = androidx.compose.ui.geometry.Offset(
                                        width * (0.1f + i * 0.08f),
                                        height * (0.2f + i * 0.06f)
                                    )
                                )
                            }
                        }
                        
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Profile Picture with enhanced design
                            Card(
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(vertical = 16.dp),
                                shape = RoundedCornerShape(50.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = currentUser?.role?.icon ?: "👨‍🌾",
                                        style = MaterialTheme.typography.displayMedium
                                    )
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // User Name with animation
                            Text(
                                text = currentUser?.name ?: "User",
                                style = MaterialTheme.typography.headlineMedium,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            
                            // Role badge
                            Card(
                                modifier = Modifier.padding(vertical = 8.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White.copy(alpha = 0.2f)
                                ),
                                shape = RoundedCornerShape(20.dp)
                            ) {
                                Text(
                                    text = currentUser?.role?.displayName ?: "User",
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            
                            // Location with icon
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "📍",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = currentUser?.location ?: "Location not set",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White.copy(alpha = 0.9f)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Role-specific Statistics Section
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = when (currentUser?.role) {
                                UserRole.FARMER -> "🌾"
                                UserRole.CUSTOMER -> "🛒"
                                UserRole.EXPERT -> "👨‍🌾"
                                else -> "📊"
                            },
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = when (currentUser?.role) {
                                UserRole.FARMER -> "Farm Analytics"
                                UserRole.CUSTOMER -> "Purchase History"
                                UserRole.EXPERT -> "Expertise Stats"
                                else -> "Profile Analytics"
                            },
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    // Role-specific stats
                    when (currentUser?.role) {
                        UserRole.FARMER -> {
                            // Farmer stats
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                ProfileStat(
                                    label = "Total Land",
                                    value = currentUser.farmSize?.let { "$it acres" } ?: "Not set",
                                    color = Color(0xFF4CAF50),
                                    modifier = Modifier.weight(1f)
                                )
                                ProfileStat(
                                    label = "Active Crops",
                                    value = currentUser.crops.size.toString(),
                                    color = Color(0xFF2196F3),
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                ProfileStat(
                                    label = "This Season",
                                    value = "GH₵ 45K",
                                    color = Color(0xFFFF9800),
                                    modifier = Modifier.weight(1f)
                                )
                                ProfileStat(
                                    label = "Experience",
                                    value = currentUser.experience?.let { "$it years" } ?: "Not set",
                                    color = Color(0xFF9C27B0),
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                        
                        UserRole.CUSTOMER -> {
                            // Customer stats
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                ProfileStat(
                                    label = "Total Orders",
                                    value = "12",
                                    color = Color(0xFF4CAF50),
                                    modifier = Modifier.weight(1f)
                                )
                                ProfileStat(
                                    label = "This Month",
                                    value = "GH₵ 2.5K",
                                    color = Color(0xFF2196F3),
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                ProfileStat(
                                    label = "Favorite Crops",
                                    value = "5",
                                    color = Color(0xFFFF9800),
                                    modifier = Modifier.weight(1f)
                                )
                                ProfileStat(
                                    label = "Member Since",
                                    value = "2023",
                                    color = Color(0xFF9C27B0),
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                        
                        UserRole.EXPERT -> {
                            // Expert stats
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                ProfileStat(
                                    label = "Experience",
                                    value = currentUser.experience?.let { "$it years" } ?: "Not set",
                                    color = Color(0xFF4CAF50),
                                    modifier = Modifier.weight(1f)
                                )
                                ProfileStat(
                                    label = "Consultations",
                                    value = "47",
                                    color = Color(0xFF2196F3),
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                ProfileStat(
                                    label = "Success Rate",
                                    value = "94%",
                                    color = Color(0xFFFF9800),
                                    modifier = Modifier.weight(1f)
                                )
                                ProfileStat(
                                    label = "Specializations",
                                    value = "3",
                                    color = Color(0xFF9C27B0),
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                        
                        else -> {
                            // Default stats
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                ProfileStat(
                                    label = "Profile",
                                    value = "Complete",
                                    color = Color(0xFF4CAF50),
                                    modifier = Modifier.weight(1f)
                                )
                                ProfileStat(
                                    label = "Status",
                                    value = "Active",
                                    color = Color(0xFF2196F3),
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Profile Details
        item {
            Text(
                text = getLocalizedText("profile_details", selectedLanguage),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

                        items(getProfileDetails(currentUser)) { detail ->
            ProfileDetailItem(detail = detail)
        }

        // Quick Actions
        item {
            Text(
                text = getLocalizedText("profile_quick_actions", selectedLanguage),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                QuickActionButton(
                    text = getLocalizedText("profile_edit_profile", selectedLanguage),
                    icon = "✏️",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        showEditDialog = true
                    }
                )
                QuickActionButton(
                    text = getLocalizedText("profile_settings", selectedLanguage),
                    icon = "⚙️",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        showSettingsDialog = true
                    }
                )
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                QuickActionButton(
                    text = getLocalizedText("profile_help_support", selectedLanguage),
                    icon = "❓",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        showHelpDialog = true
                    }
                )
                QuickActionButton(
                    text = getLocalizedText("profile_logout", selectedLanguage),
                    icon = "🚪",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        // Show logout confirmation dialog
                        showLogoutDialog = true
                    }
                )
            }
        }
    }
    
    // Edit Profile Dialog
    if (showEditDialog) {
        var editName by remember { mutableStateOf(currentUser?.name ?: "") }
        var editPhone by remember { mutableStateOf(currentUser?.phone ?: "") }
        var editLocation by remember { mutableStateOf(currentUser?.location ?: "") }
        var editFarmSize by remember { mutableStateOf(currentUser?.farmSize ?: "") }
        var editExperience by remember { mutableStateOf(currentUser?.experience ?: "") }
        var editCrops by remember { mutableStateOf(currentUser?.crops?.joinToString(", ") ?: "") }
        var isSaving by remember { mutableStateOf(false) }
        
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = { 
                Text(
                    "Edit Profile", 
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                ) 
            },
            text = { 
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Profile Picture Section
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Surface(
                                modifier = Modifier.size(80.dp),
                                shape = RoundedCornerShape(40.dp),
                                color = MaterialTheme.colorScheme.primary
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "📷",
                                        style = MaterialTheme.typography.displaySmall
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Tap to change photo",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    
                    // Basic Information
                    Text(
                        "Basic Information",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    OutlinedTextField(
                        value = editName,
                        onValueChange = { editName = it },
                        label = { Text("Full Name") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Gray,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            unfocusedLabelColor = Color.Gray
                        )
                    )
                    
                    OutlinedTextField(
                        value = editPhone,
                        onValueChange = { editPhone = it },
                        label = { Text("Phone Number") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Gray,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            unfocusedLabelColor = Color.Gray
                        )
                    )
                    
                    OutlinedTextField(
                        value = editLocation,
                        onValueChange = { editLocation = it },
                        label = { Text("Location") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Gray,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            unfocusedLabelColor = Color.Gray
                        )
                    )
                    
                    // Role-specific fields
                    if (currentUser?.role == UserRole.FARMER) {
                        Text(
                            "Farm Information",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        
                        OutlinedTextField(
                            value = editFarmSize,
                            onValueChange = { editFarmSize = it },
                            label = { Text("Farm Size (acres)") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = Color.Gray,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedLabelColor = MaterialTheme.colorScheme.primary,
                                unfocusedLabelColor = Color.Gray
                            )
                        )
                        
                        OutlinedTextField(
                            value = editCrops,
                            onValueChange = { editCrops = it },
                            label = { Text("Main Crops (separate with commas)") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = Color.Gray,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedLabelColor = MaterialTheme.colorScheme.primary,
                                unfocusedLabelColor = Color.Gray
                            )
                        )
                    }
                    
                    if (currentUser?.role == UserRole.EXPERT) {
                        Text(
                            "Expertise Information",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        
                        OutlinedTextField(
                            value = editExperience,
                            onValueChange = { editExperience = it },
                            label = { Text("Years of Experience") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = Color.Gray,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedLabelColor = MaterialTheme.colorScheme.primary,
                                unfocusedLabelColor = Color.Gray
                            )
                        )
                    }
                }
            },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextButton(
                        onClick = { showEditDialog = false },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = {
                            // Real profile update logic
                            isSaving = true
                            
                            // Create updated user object
                            val updatedUser = currentUser?.copy(
                                name = editName,
                                phone = editPhone,
                                location = editLocation,
                                farmSize = editFarmSize,
                                experience = editExperience,
                                crops = editCrops.split(",").map { it.trim() }.filter { it.isNotBlank() },
                                updatedAt = System.currentTimeMillis()
                            )
                            
                            if (updatedUser != null) {
                                // Update profile using AuthViewModel
                                localAuthViewModel.updateUserProfile(updatedUser)
                                
                                // Close dialog and show success
                                isSaving = false
                                showEditDialog = false
                                
                                // Show success message (you can add a toast here)
                            } else {
                                isSaving = false
                            }
                        },
                        modifier = Modifier.weight(1f),
                        enabled = editName.isNotBlank() && !isSaving
                    ) {
                        if (isSaving) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp,
                                color = Color.White
                            )
                        } else {
                            Text("Save Changes")
                        }
                    }
                }
            },
            modifier = Modifier.widthIn(max = 400.dp)
        )
    }
    
    // Settings Dialog
    if (showSettingsDialog) {
        AlertDialog(
            onDismissRequest = { showSettingsDialog = false },
            title = { Text("Settings") },
            text = { Text("Settings functionality coming soon! This will include notification preferences, privacy settings, account security, and app preferences.") },
            confirmButton = {
                TextButton(onClick = { showSettingsDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
    
    // Help & Support Dialog
    if (showHelpDialog) {
        AlertDialog(
            onDismissRequest = { showHelpDialog = false },
            title = { Text("Help & Support") },
            text = { 
                Column {
                    Text("Need help? Contact us:")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("📧 Email: support@agribot.com")
                    Text("📞 Phone: +233 20 123 4567")
                    Text("🌐 Website: www.agribot.com")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("We're here to help with any questions about farming, the app, or technical issues!")
                }
            },
            confirmButton = {
                TextButton(onClick = { showHelpDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
    
    // Logout Confirmation Dialog
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { 
                Text(
                    "Confirm Logout", 
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                ) 
            },
            text = { 
                Text("Are you sure you want to logout? You'll need to sign in again to access your account.")
            },
            confirmButton = {
                TextButton(
                    onClick = { 
                        showLogoutDialog = false
                        // Perform logout
                        localAuthViewModel.handleEvent(AuthEvent.Logout)
                    }
                ) {
                    Text("Cancel")
                }
            },
            dismissButton = {
                Button(
                    onClick = { 
                        showLogoutDialog = false
                        // Perform logout
                        localAuthViewModel.handleEvent(AuthEvent.Logout)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF44336)
                    )
                ) {
                    Text("Logout")
                }
            }
        )
    }
}

@Composable
fun ProfileStat(
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Animated value with color
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = color
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Label with icon
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = when {
                        label.contains("Land") -> "🌾"
                        label.contains("Crops") -> "🌱"
                        label.contains("Season") -> "💰"
                        label.contains("Experience") -> "⏰"
                        else -> "📊"
                    },
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun ProfileDetailItem(detail: ProfileDetail) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = detail.icon,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.width(32.dp)
            )
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = detail.label,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = detail.value,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun QuickActionButton(
    text: String,
    icon: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, Color(0xFFE0E0E0))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onClick() }
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Enhanced Icon with better background
                Surface(
                    modifier = Modifier.size(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = icon,
                            style = MaterialTheme.typography.headlineLarge,
                            fontSize = 28.sp
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Enhanced Text with better visibility
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF2C3E50),
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
            }
        }
    }
}

// Data classes
data class ProfileDetail(
    val label: String,
    val value: String,
    val icon: String
)

fun getProfileDetails(currentUser: User?): List<ProfileDetail> {
    return when (currentUser?.role) {
        UserRole.FARMER -> listOf(
            ProfileDetail("Phone", currentUser.phone ?: "Not set", "📱"),
            ProfileDetail("Email", currentUser.email ?: "Not set", "📧"),
            ProfileDetail("Farm Location", currentUser.location ?: "Not set", "📍"),
            ProfileDetail("Farm Size", currentUser.farmSize?.let { "$it acres" } ?: "Not set", "🌾"),
            ProfileDetail("Main Crops", currentUser.crops.joinToString(", ").ifEmpty { "Not set" }, "🌱"),
            ProfileDetail("Experience", currentUser.experience?.let { "$it years" } ?: "Not set", "⏰")
        )
        
        UserRole.CUSTOMER -> listOf(
            ProfileDetail("Phone", currentUser.phone ?: "Not set", "📱"),
            ProfileDetail("Email", currentUser.email ?: "Not set", "📧"),
            ProfileDetail("Location", currentUser.location ?: "Not set", "📍"),
            ProfileDetail("Preferred Crops", "Maize, Rice, Vegetables", "🛒"),
            ProfileDetail("Payment Method", "Mobile Money", "💳"),
            ProfileDetail("Member Since", "2023", "📅")
        )
        
        UserRole.EXPERT -> listOf(
            ProfileDetail("Phone", currentUser.phone ?: "Not set", "📱"),
            ProfileDetail("Email", currentUser.email ?: "Not set", "📧"),
            ProfileDetail("Service Area", currentUser.location ?: "Not set", "📍"),
            ProfileDetail("Experience", currentUser.experience?.let { "$it years" } ?: "Not set", "⏰"),
            ProfileDetail("Specializations", "Crop Management, Pest Control", "🎯"),
            ProfileDetail("Certifications", "Agricultural Science, PhD", "🎓")
        )
        
        else -> listOf(
            ProfileDetail("Phone", currentUser?.phone ?: "Not set", "📱"),
            ProfileDetail("Email", currentUser?.email ?: "Not set", "📧"),
            ProfileDetail("Status", "Active", "✅"),
            ProfileDetail("Member Since", "2024", "📅")
        )
    }
}
