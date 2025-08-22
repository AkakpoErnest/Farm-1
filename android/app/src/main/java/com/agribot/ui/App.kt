package com.agribot.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.zIndex
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.agribot.ui.screens.ChatScreen
import com.agribot.ui.screens.MarketScreen
import com.agribot.ui.screens.ProfileScreen
import com.agribot.ui.screens.WeatherScreen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PriceCheck
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.CalendarToday
import com.agribot.ui.screens.ExpertsScreen
import com.agribot.ui.screens.FarmersScreen
import com.agribot.ui.screens.RegisterFarmerScreen
import com.agribot.ui.screens.SubsidiesScreen
import com.agribot.ui.screens.FarmToolsScreen
import com.agribot.ui.screens.ExpertsDetailedScreen
import com.agribot.ui.screens.FarmersDetailedScreen
import com.agribot.ui.screens.RegisterFarmerDetailedScreen
import com.agribot.ui.screens.SubsidiesDetailedScreen
import com.agribot.ui.screens.LanguageTranslations
import com.agribot.ui.theme.PrimaryGreen
import com.agribot.data.User
import com.agribot.ui.theme.SecondaryGreen
import com.agribot.ui.theme.LightGreen
import com.agribot.ui.theme.AccentGold
import com.agribot.ui.theme.WarmBrown
import com.agribot.ui.theme.Cream
import com.agribot.ui.theme.OffWhite
import com.agribot.ui.theme.DarkText
import com.agribot.ui.theme.MediumText
import com.agribot.ui.theme.LightText
import com.agribot.ui.theme.SurfaceLight
import com.agribot.ui.theme.SurfaceDark
import com.agribot.ui.theme.BorderColor
import com.agribot.ui.theme.SuccessGreen
import com.agribot.ui.theme.WarningOrange
import com.agribot.ui.theme.ErrorRed
import com.agribot.ui.theme.InfoBlue
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.AlertDialog
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.IconButton
import androidx.compose.foundation.layout.Box
import com.agribot.R

sealed class Dest(val route: String, val label: String) {
    data object Home : Dest("home", "Home")
    data object Chat : Dest("chat", "Chat")
    data object Weather : Dest("weather", "Weather")
    data object Market : Dest("market", "Market")
    data object FarmTools : Dest("farm_tools", "Tools")
    data object Profile : Dest("profile", "Profile")

    data object Experts : Dest("experts", "Experts")
    data object Farmers : Dest("farmers", "Farmers")
    data object RegisterFarmer : Dest("register_farmer", "Register Farmer")
    data object Subsidies : Dest("subsidies", "Subsidies")
    
    // Detailed screens for quick actions
    data object ExpertsDetailed : Dest("experts_detailed", "Agricultural Experts")
    data object FarmersDetailed : Dest("farmers_detailed", "Farmer Community")
    data object RegisterFarmerDetailed : Dest("register_farmer_detailed", "Farmer Registration")
    data object SubsidiesDetailed : Dest("subsidies_detailed", "Government Subsidies")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgribotApp(currentUser: User? = null, authViewModel: com.agribot.ui.auth.AuthViewModel? = null) {
    val navController = rememberNavController()
    val items = listOf(Dest.Home, Dest.Chat, Dest.Weather, Dest.Market, Dest.FarmTools, Dest.Profile)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    // Global language state
    var selectedLanguage by remember { mutableStateOf("English") }
    var showLanguageDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            val title = when (currentRoute) {
                Dest.Home.route -> "Agribot"
                Dest.Chat.route -> Dest.Chat.label
                Dest.Weather.route -> Dest.Weather.label
                Dest.Market.route -> Dest.Market.label
                Dest.FarmTools.route -> Dest.FarmTools.label
                Dest.Profile.route -> Dest.Profile.label
                Dest.Experts.route -> Dest.Experts.label
                Dest.Farmers.route -> Dest.Farmers.label
                Dest.RegisterFarmer.route -> Dest.RegisterFarmer.label
                Dest.Subsidies.route -> Dest.Subsidies.label
                else -> "Agribot"
            }
            
            CenterAlignedTopAppBar(
                title = { 
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.agribot_logo_option2_removebg_preview),
                            contentDescription = "Agribot Logo",
                            modifier = Modifier.size(32.dp),
                            contentScale = ContentScale.Fit
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = getLocalizedTitle(title, selectedLanguage),
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                },
                actions = {
                    // Language Selector
                    IconButton(
                        onClick = { showLanguageDialog = true }
                    ) {
                        Icon(
                            Icons.Filled.Language,
                            contentDescription = "Select Language",
                            tint = PrimaryGreen
                        )
                    }
                }
            )
        },
        bottomBar = { BottomBar(navController, items) }
    ) { padding ->
        androidx.compose.foundation.layout.Box(Modifier.fillMaxSize().padding(padding)) {
            NavHost(navController = navController, startDestination = Dest.Home.route) {
                composable(Dest.Home.route) {
                    HomeLanding(
                        navController = navController,
                        selectedLanguage = selectedLanguage,
                        currentUser = currentUser,
                        onExperts = { navController.navigate(Dest.ExpertsDetailed.route) },
                        onFarmers = { navController.navigate(Dest.FarmersDetailed.route) },
                        onRegisterFarmer = { navController.navigate(Dest.RegisterFarmerDetailed.route) },
                        onSubsidies = { navController.navigate(Dest.SubsidiesDetailed.route) }
                    )
                }
                composable(Dest.Chat.route) { ChatScreen(selectedLanguage = selectedLanguage) }
                composable(Dest.Weather.route) { WeatherScreen(selectedLanguage = selectedLanguage) }
                composable(Dest.Market.route) { MarketScreen(selectedLanguage = selectedLanguage) }
                composable(Dest.FarmTools.route) { FarmToolsScreen(selectedLanguage = selectedLanguage) }
                composable(Dest.Profile.route) { ProfileScreen(selectedLanguage = selectedLanguage, currentUser = currentUser, authViewModel = authViewModel) }

                composable(Dest.Experts.route) { ExpertsScreen(selectedLanguage = selectedLanguage) }
                composable(Dest.Farmers.route) { FarmersScreen(selectedLanguage = selectedLanguage) }
                composable(Dest.RegisterFarmer.route) { RegisterFarmerScreen(selectedLanguage = selectedLanguage) }
                composable(Dest.Subsidies.route) { SubsidiesScreen(selectedLanguage = selectedLanguage) }
                
                // Detailed screens for quick actions
                composable(Dest.ExpertsDetailed.route) { 
                    ExpertsDetailedScreen(
                        onBack = { 
                            navController.navigate(Dest.Home.route) {
                                popUpTo(Dest.Home.route) { inclusive = true }
                            }
                        },
                        selectedLanguage = selectedLanguage
                    ) 
                }
                composable(Dest.FarmersDetailed.route) { 
                    FarmersDetailedScreen(
                        onBack = { 
                            navController.navigate(Dest.Home.route) {
                                popUpTo(Dest.Home.route) { inclusive = true }
                            }
                        },
                        selectedLanguage = selectedLanguage
                    ) 
                }
                composable(Dest.RegisterFarmerDetailed.route) { 
                    RegisterFarmerDetailedScreen(
                        onBack = { 
                            navController.navigate(Dest.Home.route) {
                                popUpTo(Dest.Home.route) { inclusive = true }
                            }
                        },
                        selectedLanguage = selectedLanguage
                    ) 
                }
                composable(Dest.SubsidiesDetailed.route) { 
                    SubsidiesDetailedScreen(
                        onBack = { 
                            navController.navigate(Dest.Home.route) {
                                popUpTo(Dest.Home.route) { inclusive = true }
                            }
                        },
                        selectedLanguage = selectedLanguage
                    ) 
                }
                composable(Dest.ExpertsDetailed.route) { 
                    ExpertsDetailedScreen(
                        onBack = { navController.navigate(Dest.Home.route) },
                        selectedLanguage = selectedLanguage
                    ) 
                }
                composable(Dest.FarmersDetailed.route) { 
                    FarmersDetailedScreen(
                        onBack = { navController.navigate(Dest.Home.route) },
                        selectedLanguage = selectedLanguage
                    ) 
                }
                composable(Dest.RegisterFarmerDetailed.route) { 
                    RegisterFarmerDetailedScreen(
                        onBack = { navController.navigate(Dest.Home.route) },
                        selectedLanguage = selectedLanguage
                    ) 
                }
                composable(Dest.SubsidiesDetailed.route) { 
                    SubsidiesDetailedScreen(
                        onBack = { navController.navigate(Dest.Home.route) },
                        selectedLanguage = selectedLanguage
                    ) 
                }
            }
        }
        
        // Global Language Selection Dialog
        if (showLanguageDialog) {
            AlertDialog(
                onDismissRequest = { showLanguageDialog = false },
                title = {
                    Text(
                        "Select Language / Họrọ Asụsụ / Yɛ Asɛm / Yɛ Ga",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                text = {
                    Column {
                        listOf(
                            "English" to "English",
                            "Twi" to "Twi",
                            "Ga" to "Ga", 
                            "Ewe" to "Eʋegbe",
                            "Dagbani" to "Dagbanli",
                            "Fante" to "Fante",
                            "Hausa" to "Hausa"
                        ).forEach { (code, displayName) ->
                            Button(
                                onClick = {
                                    selectedLanguage = code
                                    showLanguageDialog = false
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (selectedLanguage == code) PrimaryGreen else SurfaceLight
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    displayName,
                                    color = if (selectedLanguage == code) Color.White else DarkText,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = if (selectedLanguage == code) FontWeight.Bold else FontWeight.Normal
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                },
                confirmButton = {},
                dismissButton = {},
                containerColor = Color.White,
                shape = RoundedCornerShape(20.dp)
            )
        }
    }
}

// Function to get localized titles
private fun getLocalizedTitle(title: String, language: String): String {
    return when (language) {
        "Twi" -> when (title) {
            "Agribot" -> "Agribot"
            "Home" -> "Fie"
            "Chat" -> "Kasa"
            "Weather" -> "Sɛnkyerɛnne"
            "Market" -> "Tiaa"
            "Tools" -> "Mfaso"
            "Profile" -> "Nkaabɔ"
            else -> title
        }
        "Ga" -> when (title) {
            "Agribot" -> "Agribot"
            "Home" -> "Shi"
            "Chat" -> "Kɛ"
            "Weather" -> "Sɛnkyerɛnne"
            "Market" -> "Tiaa"
            "Tools" -> "Mfaso"
            "Profile" -> "Nkaabɔ"
            else -> title
        }
        "Ewe" -> when (title) {
            "Agribot" -> "Agribot"
            "Home" -> "Aƒe"
            "Chat" -> "Gbe"
            "Weather" -> "Sɛnkyerɛnne"
            "Market" -> "Tiaa"
            "Tools" -> "Mfaso"
            "Profile" -> "Nkaabɔ"
            else -> title
        }
        "Dagbani" -> when (title) {
            "Agribot" -> "Agribot"
            "Home" -> "Yili"
            "Chat" -> "Yɛli"
            "Weather" -> "Sɛnkyerɛnne"
            "Market" -> "Tiaa"
            "Tools" -> "Mfaso"
            "Profile" -> "Nkaabɔ"
            else -> title
        }
        "Fante" -> when (title) {
            "Agribot" -> "Agribot"
            "Home" -> "Fie"
            "Chat" -> "Kasa"
            "Weather" -> "Sɛnkyerɛnne"
            "Market" -> "Tiaa"
            "Tools" -> "Mfaso"
            "Profile" -> "Nkaabɔ"
            else -> title
        }
        "Hausa" -> when (title) {
            "Agribot" -> "Agribot"
            "Home" -> "Gida"
            "Chat" -> "Tattaunawa"
            "Weather" -> "Sɛnkyerɛnne"
            "Market" -> "Tiaa"
            "Tools" -> "Mfaso"
            "Profile" -> "Nkaabɔ"
            else -> title
        }
        else -> title
    }
}

// Function to get localized text for common UI elements
fun getLocalizedText(key: String, language: String): String {
    return when (language) {
        "Twi" -> when (key) {
            "Chat with Agribot" -> "Kasa ne Agribot"
            "Your AI agricultural assistant" -> "Wo AI kuayɛɛ ho kyekyefo"
            "Quick questions" -> "Mfasoɛ a ɛyɛ mmerɛw"
            "Tell me about crops" -> "Ka akyerɛ me nea ɛfa nhaban ho"
            "How to control pests?" -> "Sɛnea yɛbɛtumi akyerɛ nkaa?"
            "Market prices" -> "Tiaa bo"
            "Government subsidies" -> "Amanaman ntam akwankyerɛ"
            "Connect with expert" -> "Ka kyerɛ wo ho ne adwumayɛfo"
            "Type your message or use voice..." -> "Twerɛ wo nsɛm anaasɛ fa wo nne yɛ"
            "Hello, Farmers" -> "Akyire, Kuayɛɛfo"
            "Quick Actions" -> "Mfasoɛ a ɛyɛ mmerɛw"
            "Get professional advice" -> "Fa adwumayɛfo akwankyerɛ"
            "Connect with community" -> "Ka kyerɛ wo ho ne amanaman ntam"
            "Join our network" -> "Ka wo ho kyerɛ yɛn network ho"
            "Find support programs" -> "Hwehwɛ akwankyerɛ nhyehyɛeɛ"
            "Weather Forecast" -> "Sɛnkyerɛnne"
            "Agricultural Advice" -> "Kuayɛɛ ho akwankyerɛ"
            "Market Prices" -> "Tiaa bo"
            "Farm Calculator" -> "Kuayɛɛ ho calculator"
            "Smart calculations for optimal farming" -> "Mfasoɛ a ɛyɛ mmerɛw ma optimal kuayɛɛ"
            "Crop Yield Calculator" -> "Nhaban yield calculator"
            "Calculate Yield" -> "Hwehwɛ yield"
            "Estimated Yield" -> "Yield a ɛyɛ estimated"
            "Today's Market Overview" -> "Nnɛ tiaa ho nhwɛso"
            "Current Prices" -> "Bo a ɛyɛ current"
            "Market Analysis & Trends" -> "Tiaa ho analysis ne trends"
            else -> key
        }
        "Ga" -> when (key) {
            "Chat with Agribot" -> "Kɛ ne Agribot"
            "Your AI agricultural assistant" -> "Wo AI kuayɛɛ ho kyekyefo"
            "Quick questions" -> "Mfasoɛ a ɛyɛ mmerɛw"
            "Tell me about crops" -> "Ka akyerɛ me nea ɛfa nhaban ho"
            "How to control pests?" -> "Sɛnea yɛbɛtumi akyerɛ nkaa?"
            "Market prices" -> "Tiaa bo"
            "Government subsidies" -> "Amanaman ntam akwankyerɛ"
            "Connect with expert" -> "Ka kyerɛ wo ho ne adwumayɛfo"
            "Type your message or use voice..." -> "Twerɛ wo nsɛm anaasɛ fa wo nne yɛ"
            "Hello, Farmers" -> "Akyire, Kuayɛɛfo"
            "Quick Actions" -> "Mfasoɛ a ɛyɛ mmerɛw"
            "Get professional advice" -> "Fa adwumayɛfo akwankyerɛ"
            "Connect with community" -> "Ka kyerɛ wo ho ne amanaman ntam"
            "Join our network" -> "Ka wo ho kyerɛ yɛn network ho"
            "Find support programs" -> "Hwehwɛ akwankyerɛ nhyehyɛeɛ"
            "Weather Forecast" -> "Sɛnkyerɛnne"
            "Agricultural Advice" -> "Kuayɛɛ ho akwankyerɛ"
            "Market Prices" -> "Tiaa bo"
            "Farm Calculator" -> "Kuayɛɛ ho calculator"
            "Smart calculations for optimal farming" -> "Mfasoɛ a ɛyɛ mmerɛw ma optimal kuayɛɛ"
            "Crop Yield Calculator" -> "Nhaban yield calculator"
            "Calculate Yield" -> "Hwehwɛ yield"
            "Estimated Yield" -> "Yield a ɛyɛ estimated"
            "Today's Market Overview" -> "Nnɛ tiaa ho nhwɛso"
            "Current Prices" -> "Bo a ɛyɛ current"
            "Market Analysis & Trends" -> "Tiaa ho analysis ne trends"
            else -> key
        }
        "Ewe" -> when (key) {
            "Chat with Agribot" -> "Gbe ne Agribot"
            "Your AI agricultural assistant" -> "Wo AI kuayɛɛ ho kyekyefo"
            "Quick questions" -> "Mfasoɛ a ɛyɛ mmerɛw"
            "Tell me about crops" -> "Ka akyerɛ me nea ɛfa nhaban ho"
            "How to control pests?" -> "Sɛnea yɛbɛtumi akyerɛ nkaa?"
            "Market prices" -> "Tiaa bo"
            "Government subsidies" -> "Amanaman ntam akwankyerɛ"
            "Connect with expert" -> "Ka kyerɛ wo ho ne adwumayɛfo"
            "Type your message or use voice..." -> "Twerɛ wo nsɛm anaasɛ fa wo nne yɛ"
            "Hello, Farmers" -> "Akyire, Kuayɛɛfo"
            "Quick Actions" -> "Mfasoɛ a ɛyɛ mmerɛw"
            "Get professional advice" -> "Fa adwumayɛfo akwankyerɛ"
            "Connect with community" -> "Ka kyerɛ wo ho ne amanaman ntam"
            "Join our network" -> "Ka wo ho kyerɛ yɛn network ho"
            "Find support programs" -> "Hwehwɛ akwankyerɛ nhyehyɛeɛ"
            "Weather Forecast" -> "Sɛnkyerɛnne"
            "Agricultural Advice" -> "Kuayɛɛ ho akwankyerɛ"
            "Market Prices" -> "Tiaa bo"
            "Farm Calculator" -> "Kuayɛɛ ho calculator"
            "Smart calculations for optimal farming" -> "Mfasoɛ a ɛyɛ mmerɛw ma optimal kuayɛɛ"
            "Crop Yield Calculator" -> "Nhaban yield calculator"
            "Calculate Yield" -> "Hwehwɛ yield"
            "Estimated Yield" -> "Yield a ɛyɛ estimated"
            "Today's Market Overview" -> "Nnɛ tiaa ho nhwɛso"
            "Current Prices" -> "Bo a ɛyɛ current"
            "Market Analysis & Trends" -> "Tiaa ho analysis ne trends"
            else -> key
        }
        "Dagbani" -> when (key) {
            "Chat with Agribot" -> "Yɛli ne Agribot"
            "Your AI agricultural assistant" -> "Wo AI kuayɛɛ ho kyekyefo"
            "Quick questions" -> "Mfasoɛ a ɛyɛ mmerɛw"
            "Tell me about crops" -> "Ka akyerɛ me nea ɛfa nhaban ho"
            "How to control pests?" -> "Sɛnea yɛbɛtumi akyerɛ nkaa?"
            "Market prices" -> "Tiaa bo"
            "Government subsidies" -> "Amanaman ntam akwankyerɛ"
            "Connect with expert" -> "Ka kyerɛ wo ho ne adwumayɛfo"
            "Type your message or use voice..." -> "Twerɛ wo nsɛm anaasɛ fa wo nne yɛ"
            "Hello, Farmers" -> "Akyire, Kuayɛɛfo"
            "Quick Actions" -> "Mfasoɛ a ɛyɛ mmerɛw"
            "Get professional advice" -> "Fa adwumayɛfo akwankyerɛ"
            "Connect with community" -> "Ka kyerɛ wo ho ne amanaman ntam"
            "Join our network" -> "Ka wo ho kyerɛ yɛn network ho"
            "Find support programs" -> "Hwehwɛ akwankyerɛ nhyehyɛeɛ"
            "Weather Forecast" -> "Sɛnkyerɛnne"
            "Agricultural Advice" -> "Kuayɛɛ ho akwankyerɛ"
            "Market Prices" -> "Tiaa bo"
            "Farm Calculator" -> "Kuayɛɛ ho calculator"
            "Smart calculations for optimal farming" -> "Mfasoɛ a ɛyɛ mmerɛw ma optimal kuayɛɛ"
            "Crop Yield Calculator" -> "Nhaban yield calculator"
            "Calculate Yield" -> "Hwehwɛ yield"
            "Estimated Yield" -> "Yield a ɛyɛ estimated"
            "Today's Market Overview" -> "Nnɛ tiaa ho nhwɛso"
            "Current Prices" -> "Bo a ɛyɛ current"
            "Market Analysis & Trends" -> "Tiaa ho analysis ne trends"
            else -> key
        }
        "Fante" -> when (key) {
            "Chat with Agribot" -> "Kasa ne Agribot"
            "Your AI agricultural assistant" -> "Wo AI kuayɛɛ ho kyekyefo"
            "Quick questions" -> "Mfasoɛ a ɛyɛ mmerɛw"
            "Tell me about crops" -> "Ka akyerɛ me nea ɛfa nhaban ho"
            "How to control pests?" -> "Sɛnea yɛbɛtumi akyerɛ nkaa?"
            "Market prices" -> "Tiaa bo"
            "Government subsidies" -> "Amanaman ntam akwankyerɛ"
            "Connect with expert" -> "Ka kyerɛ wo ho ne adwumayɛfo"
            "Type your message or use voice..." -> "Twerɛ wo nsɛm anaasɛ fa wo nne yɛ"
            "Hello, Farmers" -> "Akyire, Kuayɛɛfo"
            "Quick Actions" -> "Mfasoɛ a ɛyɛ mmerɛw"
            "Get professional advice" -> "Fa adwumayɛfo akwankyerɛ"
            "Connect with community" -> "Ka kyerɛ wo ho ne amanaman ntam"
            "Join our network" -> "Ka wo ho kyerɛ yɛn network ho"
            "Find support programs" -> "Hwehwɛ akwankyerɛ nhyehyɛeɛ"
            "Weather Forecast" -> "Sɛnkyerɛnne"
            "Agricultural Advice" -> "Kuayɛɛ ho akwankyerɛ"
            "Market Prices" -> "Tiaa bo"
            "Farm Calculator" -> "Kuayɛɛ ho calculator"
            "Smart calculations for optimal farming" -> "Mfasoɛ a ɛyɛ mmerɛw ma optimal kuayɛɛ"
            "Crop Yield Calculator" -> "Nhaban yield calculator"
            "Calculate Yield" -> "Hwehwɛ yield"
            "Estimated Yield" -> "Yield a ɛyɛ estimated"
            "Today's Market Overview" -> "Nnɛ tiaa ho nhwɛso"
            "Current Prices" -> "Bo a ɛyɛ current"
            "Market Analysis & Trends" -> "Tiaa ho analysis ne trends"
            else -> key
        }
        "Hausa" -> when (key) {
            "Chat with Agribot" -> "Tattaunawa ne Agribot"
            "Your AI agricultural assistant" -> "Wo AI kuayɛɛ ho kyekyefo"
            "Quick questions" -> "Mfasoɛ a ɛyɛ mmerɛw"
            "Tell me about crops" -> "Ka akyerɛ me nea ɛfa nhaban ho"
            "How to control pests?" -> "Sɛnea yɛbɛtumi akyerɛ nkaa?"
            "Market prices" -> "Tiaa bo"
            "Government subsidies" -> "Amanaman ntam akwankyerɛ"
            "Connect with expert" -> "Ka kyerɛ wo ho ne adwumayɛfo"
            "Type your message or use voice..." -> "Twerɛ wo nsɛm anaasɛ fa wo nne yɛ"
            "Hello, Farmers" -> "Akyire, Kuayɛɛfo"
            "Quick Actions" -> "Mfasoɛ a ɛyɛ mmerɛw"
            "Get professional advice" -> "Fa adwumayɛfo akwankyerɛ"
            "Connect with community" -> "Ka kyerɛ wo ho ne amanaman ntam"
            "Join our network" -> "Ka wo ho kyerɛ yɛn network ho"
            "Find support programs" -> "Hwehwɛ akwankyerɛ nhyehyɛeɛ"
            "Weather Forecast" -> "Sɛnkyerɛnne"
            "Agricultural Advice" -> "Kuayɛɛ ho akwankyerɛ"
            "Market Prices" -> "Tiaa bo"
            "Farm Calculator" -> "Kuayɛɛ ho calculator"
            "Smart calculations for optimal farming" -> "Mfasoɛ a ɛyɛ mmerɛw ma optimal kuayɛɛ"
            "Crop Yield Calculator" -> "Nhaban yield calculator"
            "Calculate Yield" -> "Hwehwɛ yield"
            "Estimated Yield" -> "Yield a ɛyɛ estimated"
            "Today's Market Overview" -> "Nnɛ tiaa ho nhwɛso"
            "Current Prices" -> "Bo a ɛyɛ current"
            "Market Analysis & Trends" -> "Tiaa ho analysis ne trends"
            else -> key
        }
        else -> key
    }
}

private fun currentRoute(navController: NavHostController): String? =
    navController.currentBackStackEntry?.destination?.route

@Composable
private fun BottomBar(navController: NavHostController, items: List<Dest>) {
    NavigationBar(
        containerColor = SurfaceLight,
        tonalElevation = 8.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        
        items.forEach { item ->
            val icon = when (item) {
                Dest.Home -> Icons.Filled.Home
                Dest.Chat -> Icons.AutoMirrored.Filled.Chat
                Dest.Weather -> Icons.Filled.WbSunny
                Dest.Market -> Icons.Filled.ShoppingCart
                Dest.FarmTools -> Icons.Filled.Agriculture
                Dest.Profile -> Icons.Filled.Person
                else -> Icons.Filled.Home
            }
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (item.route == Dest.Home.route) {
                        // If clicking Home, clear the entire back stack and go to home
                        navController.navigate(Dest.Home.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    } else {
                        // For other tabs, use normal navigation
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = { 
                    Icon(
                        icon, 
                        contentDescription = item.label,
                        modifier = Modifier.size(24.dp),
                        tint = if (currentRoute == item.route) PrimaryGreen else MediumText
                    ) 
                },
                label = { 
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = if (currentRoute == item.route) FontWeight.SemiBold else FontWeight.Normal,
                            color = if (currentRoute == item.route) PrimaryGreen else MediumText
                        )
                    ) 
                }
            )
        }
    }
}

@Composable
private fun HomeLanding(
    navController: NavHostController,
    selectedLanguage: String,
    currentUser: User?,
    onExperts: () -> Unit,
    onFarmers: () -> Unit,
    onRegisterFarmer: () -> Unit,
    onSubsidies: () -> Unit,
) {
    var animationTriggered by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    
    // Get current date
    val currentDate = remember {
        val calendar = java.util.Calendar.getInstance()
        val dayOfWeek = when (calendar.get(java.util.Calendar.DAY_OF_WEEK)) {
            java.util.Calendar.SUNDAY -> "Sunday"
            java.util.Calendar.MONDAY -> "Monday"
            java.util.Calendar.TUESDAY -> "Tuesday"
            java.util.Calendar.WEDNESDAY -> "Wednesday"
            java.util.Calendar.THURSDAY -> "Thursday"
            java.util.Calendar.FRIDAY -> "Friday"
            java.util.Calendar.SATURDAY -> "Saturday"
            else -> "Unknown"
        }
        val day = calendar.get(java.util.Calendar.DAY_OF_MONTH)
        val month = when (calendar.get(java.util.Calendar.MONTH)) {
            0 -> "Jan"
            1 -> "Feb"
            2 -> "Mar"
            3 -> "Apr"
            4 -> "May"
            5 -> "Jun"
            6 -> "Jul"
            7 -> "Aug"
            8 -> "Sep"
            9 -> "Oct"
            10 -> "Nov"
            11 -> "Dec"
            else -> "Unknown"
        }
        val year = calendar.get(java.util.Calendar.YEAR)
        "$dayOfWeek, $day $month $year"
    }
    
    LaunchedEffect(Unit) {
        animationTriggered = true
    }
    
    val headerAlpha by animateFloatAsState(
        targetValue = if (animationTriggered) 1f else 0f,
        animationSpec = tween(800),
        label = "header_alpha"
    )
    
    val contentScale by animateFloatAsState(
        targetValue = if (animationTriggered) 1f else 0.8f,
        animationSpec = tween(1000),
        label = "content_scale"
    )
    
    val contentOffset by animateFloatAsState(
        targetValue = if (animationTriggered) 0f else 50f,
        animationSpec = tween(1000),
        label = "content_offset"
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.agribot_hero),
            contentDescription = "Agribot Hero Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        
        // Gradient Overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF2D5A27).copy(alpha = 0.4f),
                            Color(0xFF7FB069).copy(alpha = 0.3f),
                            Color(0xFF2D5A27).copy(alpha = 0.5f)
                        )
                    )
                )
        )
        
        // Background Agribot Logo Watermark
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.agribot_logo_option2_removebg_preview),
                contentDescription = "Background Logo",
                modifier = Modifier
                    .size(200.dp)
                    .alpha(0.1f),
                contentScale = ContentScale.Fit
            )
        }
        
        // Scrollable Content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Header Section with Animation
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(headerAlpha)
                        .offset(y = (-contentOffset).dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Logo and Title
                    Image(
                        painter = painterResource(id = R.drawable.agribot_logo_option2_removebg_preview),
                        contentDescription = "Agribot Logo",
                        modifier = Modifier.size(64.dp),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "THE NEW ERA OF AGRICULTURE",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            letterSpacing = 1.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Sustainable farming solutions for a better tomorrow",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color.White.copy(alpha = 0.9f),
                            letterSpacing = 0.3.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }

            // Weather Overview Card with Animation
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(contentScale)
                        .offset(y = contentOffset.dp),
                    colors = CardDefaults.cardColors(containerColor = PrimaryGreen),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (currentUser != null) {
                                    "Hello, ${currentUser.name}!"
                                } else {
                                    LanguageTranslations.getLocalizedText("Hello, Farmers", selectedLanguage)
                                },
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                            Icon(
                                Icons.Filled.Refresh,
                                contentDescription = "Refresh",
                                modifier = Modifier.size(24.dp),
                                tint = Color.White
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            currentDate,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        )
                        
                        Spacer(modifier = Modifier.height(20.dp))
                        
                        // Weather Info
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            WeatherMetric(
                                icon = Icons.Filled.Cloud,
                                value = "+16°C",
                                label = "Temperature",
                                iconTint = Color.White
                            )
                            WeatherMetric(
                                icon = Icons.Filled.Thermostat,
                                value = "22°C",
                                label = "Soil temp",
                                iconTint = LightGreen
                            )
                            WeatherMetric(
                                icon = Icons.Filled.WaterDrop,
                                value = "68%",
                                label = "Humidity",
                                iconTint = InfoBlue
                            )
                        }
                    }
                }
            }

            // Search Bar
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(contentScale)
                        .offset(y = (contentOffset * 0.8).dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = "Search",
                            modifier = Modifier.size(24.dp),
                            tint = MediumText
                        )
                        Spacer(modifier = Modifier.size(12.dp))
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = {
                                Text(
                                    LanguageTranslations.getLocalizedText("Search for crops, tools, or advice...", selectedLanguage),
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = LightText
                                    )
                                )
                            },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryGreen,
                                unfocusedBorderColor = BorderColor
                            ),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        IconButton(
                            onClick = { 
                                // Handle search functionality
                                if (searchQuery.isNotEmpty()) {
                                    // Simulate search action
                                    searchQuery = ""
                                }
                            }
                        ) {
                            Icon(
                                Icons.Filled.Search,
                                contentDescription = "Search",
                                tint = PrimaryGreen
                            )
                        }
                    }
                }
            }

            // Quick Actions Grid with Animation
            item {
                Column(
                    modifier = Modifier
                        .scale(contentScale)
                        .offset(y = (contentOffset * 0.6).dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                                                    Text(
                                    LanguageTranslations.getLocalizedText("Quick Actions", selectedLanguage),
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        letterSpacing = 0.5.sp
                                    )
                                )
                    
                    // First Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        ActionCard(
                            title = "Experts",
                            subtitle = LanguageTranslations.getLocalizedText("Get Professional Advice", selectedLanguage),
                            icon = Icons.Filled.Science,
                            onClick = onExperts,
                            modifier = Modifier.weight(1f),
                            backgroundColor = InfoBlue
                        )
                        ActionCard(
                            title = "Farmers",
                            subtitle = LanguageTranslations.getLocalizedText("Connect with Community", selectedLanguage),
                            icon = Icons.Filled.Person,
                            onClick = onFarmers,
                            modifier = Modifier.weight(1f),
                            backgroundColor = SuccessGreen
                        )
                    }
                    
                    // Second Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        ActionCard(
                            title = "Register",
                            subtitle = LanguageTranslations.getLocalizedText("Join Our Network", selectedLanguage),
                            icon = Icons.Filled.PersonAdd,
                            onClick = onRegisterFarmer,
                            modifier = Modifier.weight(1f),
                            backgroundColor = WarningOrange
                        )
                        ActionCard(
                            title = "Subsidies",
                            subtitle = LanguageTranslations.getLocalizedText("Find Support Programs", selectedLanguage),
                            icon = Icons.Filled.PriceCheck,
                            onClick = onSubsidies,
                            modifier = Modifier.weight(1f),
                            backgroundColor = SecondaryGreen
                        )
                    }
                }
            }
            
            // Crop Calendar Section
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(contentScale)
                        .offset(y = (contentOffset * 0.4).dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Filled.CalendarToday,
                                contentDescription = "Calendar",
                                modifier = Modifier.size(28.dp),
                                tint = PrimaryGreen
                            )
                            Spacer(modifier = Modifier.size(12.dp))
                            Text(
                                "This Month's Farming Calendar",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = DarkText
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Calendar Items
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            CalendarItem(
                                date = "Week 1",
                                activity = "Plant maize and beans",
                                status = "In Progress",
                                color = SuccessGreen
                            )
                            CalendarItem(
                                date = "Week 2",
                                activity = "Apply first fertilizer",
                                status = "Upcoming",
                                color = WarningOrange
                            )
                            CalendarItem(
                                date = "Week 3",
                                activity = "Pest control check",
                                status = "Upcoming",
                                color = InfoBlue
                            )
                            CalendarItem(
                                date = "Week 4",
                                activity = "Harvest early vegetables",
                                status = "Upcoming",
                                color = PrimaryGreen
                            )
                        }
                    }
                }
            }
            
            // Bottom Info Card with Animation
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(contentScale)
                        .offset(y = (contentOffset * 0.2).dp),
                    colors = CardDefaults.cardColors(containerColor = AccentGold),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Filled.Info,
                            contentDescription = "Info",
                            modifier = Modifier.size(24.dp),
                            tint = DarkText
                        )
                        Spacer(modifier = Modifier.size(12.dp))
                        Text(
                            "Access all features from the bottom navigation",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = DarkText,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ActionCard(
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector,
    backgroundColor: Color = SurfaceLight
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    icon,
                    contentDescription = title,
                    modifier = Modifier.size(32.dp),
                    tint = if (backgroundColor == SurfaceLight) PrimaryGreen else Color.White
                )
                Text(
                    title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = if (backgroundColor == SurfaceLight) DarkText else Color.White
                    )
                )
                Text(
                    subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = if (backgroundColor == SurfaceLight) MediumText else Color.White.copy(alpha = 0.9f),
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
}

@Composable
private fun WeatherMetric(
    icon: ImageVector,
    value: String,
    label: String,
    iconTint: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            icon,
            contentDescription = label,
            modifier = Modifier.size(32.dp),
            tint = iconTint
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            value,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        )
        Text(
            label,
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color.White.copy(alpha = 0.8f)
            )
        )
    }
}

@Composable
private fun CalendarItem(
    date: String,
    activity: String,
    status: String,
    color: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = SurfaceLight),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    date,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )
                )
                Text(
                    activity,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MediumText
                    )
                )
            }
            
            Card(
                colors = CardDefaults.cardColors(containerColor = color),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    status,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
        }
    }
}


