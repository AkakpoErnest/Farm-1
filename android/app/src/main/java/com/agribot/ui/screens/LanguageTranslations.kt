package com.agribot.ui.screens

object LanguageTranslations {
    
    fun getLocalizedText(key: String, language: String): String {
        return when (language) {
            "Twi" -> twiTranslations[key] ?: englishTranslations[key] ?: key
            "Ga" -> gaTranslations[key] ?: englishTranslations[key] ?: key
            "Ewe" -> eweTranslations[key] ?: englishTranslations[key] ?: key
            "Dagbani" -> dagbaniTranslations[key] ?: englishTranslations[key] ?: key
            "Fante" -> fanteTranslations[key] ?: englishTranslations[key] ?: key
            "Hausa" -> hausaTranslations[key] ?: englishTranslations[key] ?: key
            else -> englishTranslations[key] ?: key
        }
    }
    
    private val englishTranslations = mapOf(
        // App Navigation
        "Home" to "Home",
        "Chat" to "Chat",
        "Weather" to "Weather",
        "Market" to "Market",
        "Tools" to "Tools",
        "Profile" to "Profile",
        
        // Chat Screen
        "chat_title" to "Agribot Chat",
        "chat_welcome" to "Hello! I'm Agribot, your AI agricultural assistant. How can I help you today?",
        "chat_placeholder" to "Type your message...",
        "chat_send" to "Send",
        "chat_typing" to "Agribot is typing...",
        "chat_subsidies_response" to "Government subsidies are financial assistance programs that help farmers with costs. Current programs include: 1) Fertilizer Subsidy - 50% off certified fertilizers, 2) Seed Subsidy - Free improved seeds for small farmers, 3) Equipment Loan - Low-interest loans for farm machinery, 4) Insurance Support - 70% premium coverage for crop insurance. Apply through your local agricultural office or online at agric.gov.gh",
        "chat_weather_response" to "Current weather conditions are ideal for farming activities. Temperature: 25°C, Humidity: 65%, Wind: 12 km/h. No significant weather alerts. Perfect conditions for planting maize and vegetables. Remember to check daily forecasts for optimal timing.",
        "chat_market_response" to "Current market prices: Maize: GH₵ 2.50/kg, Rice: GH₵ 4.20/kg, Tomatoes: GH₵ 3.80/kg, Cassava: GH₵ 1.20/kg. Prices are stable with slight upward trend. Good time to sell stored grains. Monitor prices daily for best selling opportunities.",
        "chat_fertilizer_response" to "Recommended fertilizers: NPK 15-15-15 for general use, Urea for nitrogen boost, and organic compost for soil health. Apply NPK at planting, Urea 3 weeks after planting. Use organic compost to improve soil structure. Always test soil pH first.",
        "chat_pest_response" to "Common pests: Aphids, Armyworms, and Fruit Flies. Control methods: 1) Neem oil spray (organic), 2) Crop rotation, 3) Early planting, 4) Natural predators. For severe infestations, use approved pesticides. Always follow safety guidelines.",
        "chat_irrigation_response" to "Water management tips: 1) Drip irrigation for vegetables, 2) Sprinkler for grains, 3) Water early morning or evening, 4) Check soil moisture before watering, 5) Mulch to retain moisture. Current soil moisture is adequate for most crops.",
        "chat_seed_response" to "Best planting time: Early rainy season (March-April). Recommended seeds: Certified hybrid maize, improved rice varieties, disease-resistant tomatoes. Seed rate: Maize 25kg/ha, Rice 40kg/ha. Store seeds in cool, dry place. Use treated seeds for better germination.",
        "chat_harvest_response" to "Harvest timing: Maize 90-120 days, Rice 100-130 days, Tomatoes 70-90 days. Signs of readiness: Maize kernels hard, Rice grains firm, Tomatoes fully colored. Harvest in dry weather. Store in well-ventilated area. Monitor for pests during storage.",
        "chat_government_response" to "Government agricultural programs: 1) Planting for Food and Jobs (PFJ) - Free seeds and fertilizers, 2) Rearing for Food and Jobs (RFJ) - Livestock support, 3) Agricultural Mechanization - Equipment subsidies, 4) Youth in Agriculture - Training and funding. Contact your district agricultural office for applications.",
        "chat_default_response" to "Thank you for your question! I'm here to help with agricultural advice, market information, weather updates, and government programs. Feel free to ask about specific crops, farming techniques, or any other agricultural topics.",
        "chat_voice_input" to "Voice Input",
        "chat_voice_prompt" to "Speak your agricultural question...",
        
        // Weather Screen
        "weather_title" to "Weather",
        "weather_partly_cloudy" to "Partly Cloudy",
        "weather_humidity" to "Humidity",
        "weather_wind" to "Wind",
        "weather_visibility" to "Visibility",
        "weather_7day_forecast" to "7-Day Forecast",
        "weather_alerts" to "Weather Alerts",
        "weather_agricultural_advice" to "Agricultural Advice",
        
        // Market Screen
        "market_title" to "Market",
        "market_overview" to "Market Overview",
        "market_current_prices" to "Current Prices",
        "market_analysis" to "Market Analysis",
        "market_buy" to "Buy",
        "market_sell" to "Sell",
        "market_wait" to "Wait",
        
        // Profile Screen
        "profile_title" to "Profile",
        "profile_farm_analytics" to "Farm Analytics",
        "profile_details" to "Profile Details",
        "profile_quick_actions" to "Quick Actions",
        "profile_edit_profile" to "Edit Profile",
        "profile_settings" to "Settings",
        "profile_help_support" to "Help & Support",
        "profile_logout" to "Logout"
    )
    
    private val twiTranslations = mapOf(
        // App Navigation
        "Home" to "Fie",
        "Chat" to "Kasa",
        "Weather" to "Sɛnkyerɛnne",
        "Market" to "Tiaa",
        "Tools" to "Mfaso",
        "Profile" to "Nkaabɔ",
        
        // Home Screen
        "Hello, Farmers" to "Akyire, Kuayɛɛfo",
        "Quick Actions" to "Mfasoɛ a ɛyɛ mmerɛw",
        "Get Professional Advice" to "Fa adwumayɛfo akwankyerɛ",
        "Connect with Community" to "Ka kyerɛ wo ho ne amanaman ntam",
        "Join Our Network" to "Ka wo ho kyerɛ yɛn network ho",
        "Find Support Programs" to "Hwehwɛ akwankyerɛ nhyehyɛeɛ",
        "Search for crops, tools, or advice..." to "Hwehwɛ nhaban, mfasoɛ, anaa akwankyerɛ...",
        
        // Chat Screen
        "Chat with Agribot" to "Kasa ne Agribot",
        "Your AI agricultural assistant" to "Wo AI kuayɛɛ ho kyekyefo",
        "Quick questions" to "Mfasoɛ a ɛyɛ mmerɛw",
        "Tell me about crops" to "Ka akyerɛ me nea ɛfa nhaban ho",
        "How to control pests?" to "Sɛnea yɛbɛtumi akyerɛ nkaa?",
        "Market prices" to "Tiaa bo",
        "Government subsidies" to "Amanaman ntam akwankyerɛ",
        "Connect with expert" to "Ka kyerɛ wo ho ne adwumayɛfo",
        "Type your message or use voice..." to "Twerɛ wo nsɛm anaasɛ fa wo nne yɛ",
        "chat_title" to "Agribot Kasa",
        "chat_welcome" to "Akyire! Me yɛ Agribot, wo kuayɛɛ ho AI kyekyefo. Sɛnea me bɛtumi akyerɛ wo nnɛ?",
        "chat_placeholder" to "Twerɛ wo nsɛm...",
        "chat_send" to "Soma",
        "chat_typing" to "Agribot rekyerɛ...",
        "chat_subsidies_response" to "Amanaman ntam akwankyerɛ yɛ sikakorabea nhyehyɛeɛ a ɛkyerɛ kuayɛɛfo ho sika. Nhyehyɛeɛ a ɛyɛ current yɛ: 1) Fertilizer Subsidy - 50% off certified fertilizers, 2) Seed Subsidy - Free improved seeds ma kuayɛɛfo kakraa, 3) Equipment Loan - Low-interest loans ma kuayɛɛ mfasoɛ, 4) Insurance Support - 70% premium coverage ma crop insurance. Fa wo application efiri wo local kuayɛɛ office anaasɛ online wɔ agric.gov.gh",
        "chat_weather_response" to "Sɛnkyerɛnne a ɛyɛ current yɛ ideal ma kuayɛɛ adwumayɛ. Temperature: 25°C, Humidity: 65%, Wind: 12 km/h. Ɛnyɛ weather alerts a ɛyɛ significant. Perfect conditions ma maize ne vegetables planting. Fa wo kae sɛ wo check daily forecasts ma optimal timing.",
        "chat_market_response" to "Current market prices: Maize: GH₵ 2.50/kg, Rice: GH₵ 4.20/kg, Tomatoes: GH₵ 3.80/kg, Cassava: GH₵ 1.20/kg. Prices yɛ stable ne slight upward trend. Ɛyɛ time a ɛyɛ papa ma wo tɔn stored grains. Monitor prices daily ma best selling opportunities.",
        "chat_fertilizer_response" to "Fertilizers a ɛyɛ recommended: NPK 15-15-15 ma general use, Urea ma nitrogen boost, ne organic compost ma asaase health. Fa NPK wɔ planting bere mu, Urea 3 weeks akyiri planting. Fa organic compost ma wo improve asaase structure. Always test asaase pH first.",
        "chat_pest_response" to "Pests a ɛyɛ common: Aphids, Armyworms, ne Fruit Flies. Control methods: 1) Neem oil spray (organic), 2) Crop rotation, 3) Early planting, 4) Natural predators. Ma severe infestations, fa approved pesticides. Always follow safety guidelines.",
        "chat_irrigation_response" to "Nsuo ho management tips: 1) Drip irrigation ma vegetables, 2) Sprinkler ma grains, 3) Fa nsuo early morning anaasɛ evening, 4) Check asaase moisture ansa na wo fa nsuo, 5) Mulch ma wo retain moisture. Current asaase moisture yɛ adequate ma crops pii.",
        "chat_seed_response" to "Best planting time: Early rainy season (March-April). Seeds a ɛyɛ recommended: Certified hybrid maize, improved rice varieties, disease-resistant tomatoes. Seed rate: Maize 25kg/ha, Rice 40kg/ha. Store seeds wɔ cool, dry place. Fa treated seeds ma better germination.",
        "chat_harvest_response" to "Harvest timing: Maize 90-120 days, Rice 100-130 days, Tomatoes 70-90 days. Signs a ɛkyerɛ readiness: Maize kernels hard, Rice grains firm, Tomatoes fully colored. Harvest wɔ dry weather. Store wɔ well-ventilated area. Monitor ma pests during storage.",
        "chat_government_response" to "Government kuayɛɛ nhyehyɛeɛ: 1) Planting for Food and Jobs (PFJ) - Free seeds ne fertilizers, 2) Rearing for Food and Jobs (RFJ) - Livestock support, 3) Agricultural Mechanization - Equipment subsidies, 4) Youth in Agriculture - Training ne funding. Contact wo district kuayɛɛ office ma applications.",
        "chat_default_response" to "Meda wo ase wo asɛm ho! Me wɔ ha ma wo kyerɛ kuayɛɛ ho akwankyerɛ, market information, weather updates, ne government nhyehyɛeɛ. Feel free ma wo bisɛ specific crops, kuayɛɛ techniques, anaasɛ any other kuayɛɛ topics",
        "chat_voice_input" to "Nne Ho Input",
        "chat_voice_prompt" to "Ka wo kuayɛɛ ho asɛm...",
        
        // Weather Screen
        "Weather Forecast" to "Sɛnkyerɛnne",
        "Partly Cloudy" to "Ɛwom sɛ ɛyɛ cloudy",
        "Soil Temp" to "Asaase temperature",
        "Humidity" to "Humidity",
        "Wind" to "Mframa",
        "Visibility" to "Hwɛ so",
        "Pressure" to "Pressure",
        "Sunrise" to "Awia firi",
        "Sunset" to "Awia kɔ",
        "UV Index" to "UV Index",
        "Air Quality" to "Mframa quality",
        "Rainfall" to "Nsutiri",
        "Night Temp" to "Anadwo temperature",
        "Agricultural Advice" to "Kuayɛɛ ho akwankyerɛ",
        "7-Day Forecast" to "7 Da forecast",
        "Weather Alerts" to "Sɛnkyerɛnne alerts",
        "Crop Recommendations" to "Nhaban akwankyerɛ",
        "weather_title" to "Sɛnkyerɛnne",
        "weather_partly_cloudy" to "Ɛwom sɛ ɛyɛ cloudy",
        "weather_humidity" to "Humidity",
        "weather_wind" to "Mframa",
        "weather_visibility" to "Hwɛ so",
        "weather_7day_forecast" to "7 Da Forecast",
        "weather_alerts" to "Sɛnkyerɛnne Alerts",
        "weather_agricultural_advice" to "Kuayɛɛ Ho Akwankyerɛ",
        
        // Market Screen
        "Today's Market Overview" to "Nnɛ tiaa ho nhwɛso",
        "Current Prices" to "Bo a ɛyɛ current",
        "Market Analysis & Trends" to "Tiaa ho analysis ne trends",
        "Buy" to "Tɔ",
        "Sell" to "Tɔn",
        "Wait" to "Gyae",
        "market_title" to "Tiaa",
        "market_overview" to "Tiaa Ho Nhwɛso",
        "market_current_prices" to "Bo A Ɛyɛ Current",
        "market_analysis" to "Tiaa Ho Analysis",
        "market_buy" to "Tɔ",
        "market_sell" to "Tɔn",
        "market_wait" to "Gyae",
        
        // Profile Screen
        "profile_title" to "Nkaabɔ",
        "profile_farm_analytics" to "Kuayɛɛ Ho Analytics",
        "profile_details" to "Nkaabɔ Ho Details",
        "profile_quick_actions" to "Mfasoɛ A Ɛyɛ Mmerɛw",
        "profile_edit_profile" to "Edit Nkaabɔ",
        "profile_settings" to "Settings",
        "profile_help_support" to "Help & Support",
        "profile_logout" to "Logout",
        
        // Expert Screens
        "Agricultural Experts" to "Kuayɛɛ ho adwumayɛfo",
        "Connect with Professional Agricultural Experts" to "Ka kyerɛ wo ho ne kuayɛɛ ho adwumayɛfo",
        "Crop Specialists" to "Nhaban ho adwumayɛfo",
        "Experts in specific crop cultivation and management" to "Adwumayɛfo a wɔyɛ nhaban ho adwumayɛ",
        "Soil Scientists" to "Asaase ho adwumayɛfo",
        "Experts in soil health and fertility management" to "Adwumayɛfo a wɔyɛ asaase ho adwumayɛ",
        "Pest Management" to "Nkaa ho adwumayɛ",
        "Experts in integrated pest management" to "Adwumayɛfo a wɔyɛ nkaa ho adwumayɛ",
        "Agricultural Economics" to "Kuayɛɛ ho economics",
        "Experts in farm business and market analysis" to "Adwumayɛfo a wɔyɛ kuayɛɛ ho adwumayɛ",
        "Book a Consultation" to "Book consultation",
        "Get personalized advice from our experts" to "Fa akwankyerɛ a ɛyɛ wo dea efiri yɛn adwumayɛfo hɔ",
        "Book Now" to "Book Afei",
        
        // Farmer Community
        "Farmer Community" to "Kuayɛɛfo community",
        "Connect with Fellow Farmers" to "Ka kyerɛ wo ho ne kuayɛɛfo foforɔ",
        "Discussion Forums" to "Discussion forums",
        "Share experiences and ask questions" to "Fa wo experience ne bisɛ asɛm",
        "Knowledge Sharing" to "Nimdeɛ ho adwumayɛ",
        "Learn from successful farmers" to "Kyerɛ efiri kuayɛɛfo a wɔyɛɛ adwumayɛ hɔ",
        "Local Groups" to "Local groups",
        "Connect with farmers in your area" to "Ka kyerɛ wo ho ne kuayɛɛfo a wɔwɔ wo area hɔ",
        "Join the Community" to "Ka wo ho kyerɛ community ho",
        "Become part of our growing farmer community" to "Yɛ kuayɛɛfo community a ɛrekoaa no fa",
        "Join Now" to "Ka Afei",
        
        // AI Responses
        "Based on current conditions, it's a great day for planting! Temperature is optimal at 22°C with 68% humidity. Consider irrigation for young crops." to "Based on current conditions, it's a great day for planting! Temperature is optimal at 22°C with 68% humidity. Consider irrigation for young crops.",
        "This season is perfect for maize, beans, and vegetables. Make sure to test your soil pH first - most crops prefer 6.0-7.0. Would you like specific planting dates?" to "This season is perfect for maize, beans, and vegetables. Make sure to test your soil pH first - most crops prefer 6.0-7.0. Would you like specific planting dates?",
        "For natural pest control, try neem oil, companion planting with marigolds, and regular crop rotation. Monitor your plants daily for early detection." to "For natural pest control, try neem oil, companion planting with marigolds, and regular crop rotation. Monitor your plants daily for early detection.",
        "That's an interesting question about farming! I'd be happy to help you with that. Could you provide more specific details about your farming situation?" to "That's an interesting question about farming! I'd be happy to help you with that. Could you provide more specific details about your farming situation?",
        "Welcome to Agribot! I'm your AI agricultural assistant. I can help you with:" to "Welcome to Agribot! I'm your AI agricultural assistant. I can help you with:",
        "What would you like to know about farming today?" to "What would you like to know about farming today?",
        "Agribot is typing" to "Agribot is typing",
        "Current Weather" to "Current Weather",
        "Partly cloudy with a high of 28°C. Perfect conditions for outdoor farming activities." to "Partly cloudy with a high of 28°C. Perfect conditions for outdoor farming activities.",
        "Temperature & Humidity" to "Temperature & Humidity",
        "Farming Recommendations" to "Farming Recommendations",
        "Market Prices" to "Market Prices",
        "Market conditions are favorable for most crops. Prices are stable with upward trends for vegetables and grains. Strong demand for tomatoes and leafy greens." to "Market conditions are favorable for most crops. Prices are stable with upward trends for vegetables and grains. Strong demand for tomatoes and leafy greens.",
        "Trading Recommendations" to "Trading Recommendations",
        "John Farmer" to "John Farmer",
        "Professional Farmer" to "Professional Farmer",
        "Fields" to "Fields",
        "Experience" to "Experience",
        "Crops" to "Crops",
        "Personal Information" to "Personal Information",
        "Location" to "Location",
        "Kumasi, Ghana" to "Kumasi, Ghana",
        "Phone" to "Phone",
        "Email" to "Email",
        "Member Since" to "Member Since",
        "Specialization" to "Specialization",
        "Education" to "Education",
        "Farm Analytics" to "Farm Analytics",
        "Efficiency" to "Efficiency",
        "Active Fields" to "Active Fields",
        "Annual Income" to "Annual Income",
        "Quick Actions" to "Quick Actions",
        "Edit Profile" to "Edit Profile",
        "View Reports" to "View Reports",
        
        // AI Responses
        "Based on current conditions, it's a great day for planting! Temperature is optimal at 22°C with 68% humidity. Consider irrigation for young crops." to "Sɛnea ɛyɛɛ nnɛ, ɛyɛ da pa ma nhaban a wɔbɛtumi akyerɛ! Temperature yɛ pa wɔ 22°C ne 68% humidity. Hwɛ sɛ wo ma nsu ma nhaban a ɛyɛ mmerɛw.",
        "This season is perfect for maize, beans, and vegetables. Make sure to test your soil pH first - most crops prefer 6.0-7.0. Would you like specific planting dates?" to "Ɛberɛ yi yɛ pa ma aburo, aborɔ, ne nhaban. Hwɛ sɛ wo test wo asaase pH kan - nhaban pii pɛ 6.0-7.0. Wo pɛ sɛ wo hwɛ sɛnea wɔbɛtumi akyerɛ nhaban?",
        "For natural pest control, try neem oil, companion planting with marigolds, and regular crop rotation. Monitor your plants daily for early detection." to "Ma nkaa ho adwumayɛ a ɛyɛ natural, fa neem oil, companion planting ne marigolds, ne regular crop rotation. Hwɛ wo nhaban daa ma early detection.",
        "That's an interesting question about farming! I'd be happy to help you with that. Could you provide more specific details about your farming situation?" to "Ɛyɛ asɛm a ɛyɛ mmerɛw a ɛfa kuayɛɛ ho! Me bɛyɛ wo mmaada. Wo bɛtumi akyerɛ me nea ɛfa wo kuayɛɛ ho ho?",
        "Welcome to Agribot! I'm your AI agricultural assistant. I can help you with:" to "Akwaaba wɔ Agribot! Me yɛ wo AI kuayɛɛ ho kyekyefo. Me bɛtumi akyerɛ wo nea ɛfa:",
        "What would you like to know about farming today?" to "Dɛn na wo pɛ sɛ wo nim wɔ kuayɛɛ ho nnɛ?",
        "Agribot is typing" to "Agribot rekyerɛ",
        "Current Weather" to "Sɛnkyerɛnne a ɛyɛ nnɛ",
        "Partly cloudy with a high of 28°C. Perfect conditions for outdoor farming activities." to "Ɛwom sɛ ɛyɛ cloudy ne 28°C. Ɛyɛ pa ma kuayɛɛ adwumayɛ a wɔyɛ wɔ afaase.",
        "Temperature & Humidity" to "Temperature ne Humidity",
        "Farming Recommendations" to "Kuayɛɛ ho akwankyerɛ",
        "Market Prices" to "Tiaa bo",
        "Market conditions are favorable for most crops. Prices are stable with upward trends for vegetables and grains. Strong demand for tomatoes and leafy greens." to "Tiaa ho yɛ pa ma nhaban pii. Bo yɛ stable ne upward trends ma vegetables ne grains. Tomatoes ne leafy greens wɔ demand kɛse.",
        "Trading Recommendations" to "Tiaa ho akwankyerɛ",
        "John Farmer" to "John Kuayɛɛfo",
        "Professional Farmer" to "Adwumayɛfo Kuayɛɛfo",
        "Fields" to "Afaase",
        "Experience" to "Experience",
        "Crops" to "Nhaban",
        "Personal Information" to "Wo ho nimdeɛ",
        "Location" to "Bea",
        "Kumasi, Ghana" to "Kumasi, Ghana",
        "Phone" to "Fone",
        "Email" to "Email",
        "Member Since" to "Member Firi",
        "Specialization" to "Specialization",
        "Education" to "Nwomasua",
        "Farm Analytics" to "Kuayɛɛ ho analytics",
        "Efficiency" to "Efficiency",
        "Active Fields" to "Afaase a ɛyɛ active",
        "Annual Income" to "Afe biara sika",
        "Quick Actions" to "Mfasoɛ a ɛyɛ mmerɛw",
        "Edit Profile" to "Edit Profile",
        "View Reports" to "Hwɛ Reports",
        
        // Additional Chat Content
        "Crop recommendations and planting advice" to "Nhaban akwankyerɛ ne akyerɛ akyerɛ",
        "Pest control strategies" to "Nkaa ho adwumayɛ nhyehyɛeɛ",
        "Market prices and trends" to "Tiaa bo ne trends",
        "Government subsidies and programs" to "Amanaman ntam subsidies ne nhyehyɛeɛ",
        "Connect with agricultural experts" to "Ka kyerɛ wo ho ne kuayɛɛ ho adwumayɛfo",
        
        // Registration
        "Farmer Registration" to "Kuayɛɛfo registration",
        "Join Our Agricultural Network" to "Ka wo ho kyerɛ yɛn kuayɛɛ network ho",
        "Registration Benefits" to "Registration benefits",
        "Access to expert consultations" to "Kwan a ɛwɔ expert consultations ho",
        "Market price updates" to "Tiaa bo updates",
        "Weather forecasts" to "Sɛnkyerɛnne forecasts",
        "Government subsidy information" to "Amanaman ntam subsidy information",
        "Community networking" to "Community networking",
        "Training programs" to "Training nhyehyɛeɛ",
        "Financial support options" to "Financial support options",
        "Technology access" to "Technology access",
        "Registration Form" to "Registration form",
        "Full Name" to "Din nyinaa",
        "Phone Number" to "Phone number",
        "Location" to "Bea",
        "Farm Size (acres)" to "Kuayɛɛ size (acres)",
        "Submit Registration" to "Fa registration",
        "Success Stories" to "Success stories",
        
        // Subsidies
        "Government Subsidies" to "Amanaman ntam subsidies",
        "Available Support Programs" to "Support nhyehyɛeɛ a ɛwɔ hɔ",
        "Fertilizer Subsidy Program" to "Fertilizer subsidy nhyehyɛeɛ",
        "50% subsidy on NPK and Urea fertilizers" to "50% subsidy NPK ne Urea fertilizers ho",
        "Registered farmers with valid ID" to "Kuayɛɛfo a wɔyɛɛ registered ne valid ID",
        "Ongoing" to "Ɛrekoaa",
        "Ministry of Food and Agriculture" to "Ministry of Food and Agriculture",
        "Planting for Food and Jobs" to "Planting ma Food and Jobs",
        "Free seeds and technical support" to "Free seeds ne technical support",
        "Smallholder farmers with less than 5 acres" to "Kuayɛɛfo a wɔwɔ acres 5 so",
        "MoFA Extension Services" to "MoFA Extension Services",
        "Youth in Agriculture" to "Youth in Agriculture",
        "Support for young farmers" to "Support ma kuayɛɛfo a wɔyɛ mmerɛw",
        "Farmers aged 18-35 years" to "Kuayɛɛfo a wɔyɛ mfe 18-35",
        "Youth in Agriculture" to "Youth in Agriculture",
        "Startup capital" to "Startup capital",
        "Land access support" to "Asaase access support",
        "Modern farming equipment" to "Modern kuayɛɛ equipment",
        "Mentorship programs" to "Mentorship nhyehyɛeɛ",
        "Apply Now" to "Apply Afei",
        "Application Guide" to "Application guide",
        "Eligibility" to "Eligibility",
        "Deadline" to "Deadline",
        "Contact" to "Contact",
        "Benefits" to "Benefits",
        "Reduced fertilizer costs" to "Fertilizer costs a ɛyɛ mmerɛw",
        "Improved soil fertility" to "Asaase fertility a ɛyɛɛ yie",
        "Higher crop yields" to "Nhaban yields a ɛyɛ kɛse",
        "Sustainable farming practices" to "Kuayɛɛ practices a ɛyɛ sustainable",
        "Free quality seeds" to "Free quality seeds",
        "Technical guidance" to "Technical guidance",
        "Market access support" to "Tiaa access support",
        "Training programs" to "Training nhyehyɛeɛ"
    )
    
    private val gaTranslations = mapOf(
        // App Navigation
        "Home" to "Shi",
        "Chat" to "Kasa",
        "Weather" to "Sɛnkyerɛnne",
        "Market" to "Tiaa",
        "Tools" to "Mfaso",
        "Profile" to "Nkaabɔ",
        
        // Home Screen
        "Hello, Farmers" to "Akyire, Kuayɛɛfo",
        "Quick Actions" to "Mfasoɛ a ɛyɛ mmerɛw",
        "Get Professional Advice" to "Fa adwumayɛfo akwankyerɛ",
        "Connect with Community" to "Ka kyerɛ wo ho ne amanaman ntam",
        "Join Our Network" to "Ka wo ho kyerɛ yɛn network ho",
        "Find Support Programs" to "Hwehwɛ akwankyerɛ nhyehyɛeɛ",
        "Search for crops, tools, or advice..." to "Hwehwɛ nhaban, mfasoɛ, anaa akwankyerɛ...",
        
        // Chat Screen
        "Chat with Agribot" to "Kasa ne Agribot",
        "Your AI agricultural assistant" to "Wo AI kuayɛɛ ho kyekyefo",
        "Quick questions" to "Mfasoɛ a ɛyɛ mmerɛw",
        "Tell me about crops" to "Ka akyerɛ me nea ɛfa nhaban ho",
        "How to control pests?" to "Sɛnea yɛbɛtumi akyerɛ nkaa?",
        "Market prices" to "Tiaa bo",
        "Government subsidies" to "Amanaman ntam akwankyerɛ",
        "Connect with expert" to "Ka kyerɛ wo ho ne adwumayɛfo",
        "Type your message or use voice..." to "Twerɛ wo nsɛm anaasɛ fa wo nne yɛ",
        
        // Weather Screen
        "Weather Forecast" to "Sɛnkyerɛnne",
        "Partly Cloudy" to "Ɛwom sɛ ɛyɛ cloudy",
        "Soil Temp" to "Asaase temperature",
        "Humidity" to "Humidity",
        "Wind" to "Mframa",
        "Visibility" to "Hwɛ so",
        "Pressure" to "Pressure",
        "Sunrise" to "Awia firi",
        "Sunset" to "Awia kɔ",
        "UV Index" to "UV Index",
        "Air Quality" to "Mframa quality",
        "Rainfall" to "Nsutiri",
        "Night Temp" to "Anadwo temperature",
        "Agricultural Advice" to "Kuayɛɛ ho akwankyerɛ",
        "7-Day Forecast" to "7 Da forecast",
        "Weather Alerts" to "Sɛnkyerɛnne alerts",
        "Crop Recommendations" to "Nhaban akwankyerɛ"
    )
    
    private val eweTranslations = mapOf(
        // App Navigation
        "Home" to "Aƒe",
        "Chat" to "Kasa",
        "Weather" to "Sɛnkyerɛnne",
        "Market" to "Tiaa",
        "Tools" to "Mfaso",
        "Profile" to "Nkaabɔ",
        
        // Home Screen
        "Hello, Farmers" to "Akyire, Kuayɛɛfo",
        "Quick Actions" to "Mfasoɛ a ɛyɛ mmerɛw",
        "Get Professional Advice" to "Fa adwumayɛfo akwankyerɛ",
        "Connect with Community" to "Ka kyerɛ wo ho ne amanaman ntam",
        "Join Our Network" to "Ka wo ho kyerɛ yɛn network ho",
        "Find Support Programs" to "Hwehwɛ akwankyerɛ nhyehyɛeɛ",
        "Search for crops, tools, or advice..." to "Hwehwɛ nhaban, mfasoɛ, anaa akwankyerɛ...",
        
        // Chat Screen
        "Chat with Agribot" to "Kasa ne Agribot",
        "Your AI agricultural assistant" to "Wo AI kuayɛɛ ho kyekyefo",
        "Quick questions" to "Mfasoɛ a ɛyɛ mmerɛw",
        "Tell me about crops" to "Ka akyerɛ me nea ɛfa nhaban ho",
        "How to control pests?" to "Sɛnea yɛbɛtumi akyerɛ nkaa?",
        "Market prices" to "Tiaa bo",
        "Government subsidies" to "Amanaman ntam akwankyerɛ",
        "Connect with expert" to "Ka kyerɛ wo ho ne adwumayɛfo",
        "Type your message or use voice..." to "Twerɛ wo nsɛm anaasɛ fa wo nne yɛ"
    )
    
    private val dagbaniTranslations = mapOf(
        // App Navigation
        "Home" to "Yili",
        "Chat" to "Kasa",
        "Weather" to "Sɛnkyerɛnne",
        "Market" to "Tiaa",
        "Tools" to "Mfaso",
        "Profile" to "Nkaabɔ",
        
        // Home Screen
        "Hello, Farmers" to "Akyire, Kuayɛɛfo",
        "Quick Actions" to "Mfasoɛ a ɛyɛ mmerɛw",
        "Get Professional Advice" to "Fa adwumayɛfo akwankyerɛ",
        "Connect with Community" to "Ka kyerɛ wo ho ne amanaman ntam",
        "Join Our Network" to "Ka wo ho kyerɛ yɛn network ho",
        "Find Support Programs" to "Hwehwɛ akwankyerɛ nhyehyɛeɛ",
        "Search for crops, tools, or advice..." to "Hwehwɛ nhaban, mfasoɛ, anaa akwankyerɛ...",
        
        // Chat Screen
        "Chat with Agribot" to "Kasa ne Agribot",
        "Your AI agricultural assistant" to "Wo AI kuayɛɛ ho kyekyefo",
        "Quick questions" to "Mfasoɛ a ɛyɛ mmerɛw",
        "Tell me about crops" to "Ka akyerɛ me nea ɛfa nhaban ho",
        "How to control pests?" to "Sɛnea yɛbɛtumi akyerɛ nkaa?",
        "Market prices" to "Tiaa bo",
        "Government subsidies" to "Amanaman ntam akwankyerɛ",
        "Connect with expert" to "Ka kyerɛ wo ho ne adwumayɛfo",
        "Type your message or use voice..." to "Twerɛ wo nsɛm anaasɛ fa wo nne yɛ"
    )
    
    private val fanteTranslations = mapOf(
        // App Navigation
        "Home" to "Fie",
        "Chat" to "Kasa",
        "Weather" to "Sɛnkyerɛnne",
        "Market" to "Tiaa",
        "Tools" to "Mfaso",
        "Profile" to "Nkaabɔ",
        
        // Home Screen
        "Hello, Farmers" to "Akyire, Kuayɛɛfo",
        "Quick Actions" to "Mfasoɛ a ɛyɛ mmerɛw",
        "Get Professional Advice" to "Fa adwumayɛfo akwankyerɛ",
        "Connect with Community" to "Ka kyerɛ wo ho ne amanaman ntam",
        "Join Our Network" to "Ka wo ho kyerɛ yɛn network ho",
        "Find Support Programs" to "Hwehwɛ akwankyerɛ nhyehyɛeɛ",
        "Search for crops, tools, or advice..." to "Hwehwɛ nhaban, mfasoɛ, anaa akwankyerɛ...",
        
        // Chat Screen
        "Chat with Agribot" to "Kasa ne Agribot",
        "Your AI agricultural assistant" to "Wo AI kuayɛɛ ho kyekyefo",
        "Quick questions" to "Mfasoɛ a ɛyɛ mmerɛw",
        "Tell me about crops" to "Ka akyerɛ me nea ɛfa nhaban ho",
        "How to control pests?" to "Sɛnea yɛbɛtumi akyerɛ nkaa?",
        "Market prices" to "Tiaa bo",
        "Government subsidies" to "Amanaman ntam akwankyerɛ",
        "Connect with expert" to "Ka kyerɛ wo ho ne adwumayɛfo",
        "Type your message or use voice..." to "Twerɛ wo nsɛm anaasɛ fa wo nne yɛ"
    )
    
    private val hausaTranslations = mapOf(
        // App Navigation
        "Home" to "Gida",
        "Chat" to "Kasa",
        "Weather" to "Sɛnkyerɛnne",
        "Market" to "Tiaa",
        "Tools" to "Mfaso",
        "Profile" to "Nkaabɔ",
        
        // Home Screen
        "Hello, Farmers" to "Akyire, Kuayɛɛfo",
        "Quick Actions" to "Mfasoɛ a ɛyɛ mmerɛw",
        "Get Professional Advice" to "Fa adwumayɛfo akwankyerɛ",
        "Connect with Community" to "Ka kyerɛ wo ho ne amanaman ntam",
        "Join Our Network" to "Ka wo ho kyerɛ yɛn network ho",
        "Find Support Programs" to "Hwehwɛ akwankyerɛ nhyehyɛeɛ",
        "Search for crops, tools, or advice..." to "Hwehwɛ nhaban, mfasoɛ, anaa akwankyerɛ...",
        
        // Chat Screen
        "Chat with Agribot" to "Kasa ne Agribot",
        "Your AI agricultural assistant" to "Wo AI kuayɛɛ ho kyekyefo",
        "Quick questions" to "Mfasoɛ a ɛyɛ mmerɛw",
        "Tell me about crops" to "Ka akyerɛ me nea ɛfa nhaban ho",
        "How to control pests?" to "Sɛnea yɛbɛtumi akyerɛ nkaa?",
        "Market prices" to "Tiaa bo",
        "Government subsidies" to "Amanaman ntam akwankyerɛ",
        "Connect with expert" to "Ka kyerɛ wo ho ne adwumayɛfo",
        "Type your message or use voice..." to "Twerɛ wo nsɛm anaasɛ fa wo nne yɛ"
    )
}
