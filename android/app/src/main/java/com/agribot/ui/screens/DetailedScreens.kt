package com.agribot.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.agribot.R
import com.agribot.ui.theme.*
import com.agribot.ui.screens.LanguageTranslations

@Composable
fun ExpertsDetailedScreen(
    onBack: () -> Unit,
    selectedLanguage: String = "English"
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
    ) {
        // Header
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = PrimaryGreen,
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Image(
                    painter = painterResource(id = R.drawable.agribot_logo_option2_removebg_preview),
                    contentDescription = "Agribot Logo",
                    modifier = Modifier.size(32.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    LanguageTranslations.getLocalizedText("Agricultural Experts", selectedLanguage),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
        }
        
        // Scrollable Content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    LanguageTranslations.getLocalizedText("Connect with Professional Agricultural Experts", selectedLanguage),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )
                )
            }
            
            // Expert Categories
            item {
                ExpertCategoryCard(
                    title = LanguageTranslations.getLocalizedText("Crop Specialists", selectedLanguage),
                    description = LanguageTranslations.getLocalizedText("Experts in specific crop cultivation and management", selectedLanguage),
                    icon = Icons.Filled.Agriculture,
                    experts = listOf(
                        Expert("Dr. Kwame Asante", "Maize & Rice Expert", "15 years", "+233 20 123 4567", "Weekdays 9AM-5PM", "PhD Agriculture", listOf("English", "Twi")),
                        Expert("Dr. Ama Osei", "Vegetable Specialist", "12 years", "+233 24 987 6543", "Weekdays 8AM-4PM", "MSc Horticulture", listOf("English", "Ga"))
                    ),
                    selectedLanguage = selectedLanguage
                )
            }
            
            item {
                ExpertCategoryCard(
                    title = LanguageTranslations.getLocalizedText("Soil Scientists", selectedLanguage),
                    description = LanguageTranslations.getLocalizedText("Experts in soil health and fertility management", selectedLanguage),
                    icon = Icons.Filled.Science,
                    experts = listOf(
                        Expert("Prof. Kofi Mensah", "Soil Chemistry", "20 years", "+233 26 555 1234", "Weekdays 10AM-6PM", "PhD Soil Science", listOf("English", "Dagbani")),
                        Expert("Dr. Sarah Addo", "Soil Microbiology", "8 years", "+233 27 888 9999", "Weekdays 9AM-5PM", "MSc Microbiology", listOf("English", "Ewe"))
                    ),
                    selectedLanguage = selectedLanguage
                )
            }
            
            item {
                ExpertCategoryCard(
                    title = LanguageTranslations.getLocalizedText("Pest Management", selectedLanguage),
                    description = LanguageTranslations.getLocalizedText("Experts in integrated pest management", selectedLanguage),
                    icon = Icons.Filled.BugReport,
                    experts = listOf(
                        Expert("Dr. Emmanuel Boateng", "IPM Specialist", "18 years", "+233 28 777 6666", "Weekdays 8AM-4PM", "PhD Entomology", listOf("English", "Fante")),
                        Expert("Dr. Grace Ampah", "Biological Control", "10 years", "+233 29 111 2222", "Weekdays 9AM-5PM", "MSc Biology", listOf("English", "Hausa"))
                    ),
                    selectedLanguage = selectedLanguage
                )
            }
            
            item {
                ExpertCategoryCard(
                    title = LanguageTranslations.getLocalizedText("Agricultural Economics", selectedLanguage),
                    description = LanguageTranslations.getLocalizedText("Experts in farm business and market analysis", selectedLanguage),
                    icon = Icons.Filled.Business,
                    experts = listOf(
                        Expert("Prof. Daniel Owusu", "Farm Economics", "22 years", "+233 30 333 4444", "Weekdays 9AM-6PM", "PhD Economics", listOf("English", "Twi")),
                        Expert("Dr. Comfort Asante", "Market Analysis", "14 years", "+233 31 555 6666", "Weekdays 8AM-5PM", "MSc Agricultural Economics", listOf("English", "Ga"))
                    ),
                    selectedLanguage = selectedLanguage
                )
            }
            
            // Consultation Booking
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = SuccessGreen),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            LanguageTranslations.getLocalizedText("Book a Consultation", selectedLanguage),
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            LanguageTranslations.getLocalizedText("Get personalized advice from our experts. Book a consultation session for detailed guidance on your farming challenges.", selectedLanguage),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                LanguageTranslations.getLocalizedText("Book Now", selectedLanguage),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SuccessGreen
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FarmersDetailedScreen(
    onBack: () -> Unit,
    selectedLanguage: String = "English"
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
    ) {
        // Header
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = SuccessGreen,
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Image(
                    painter = painterResource(id = R.drawable.agribot_logo_option2_removebg_preview),
                    contentDescription = "Agribot Logo",
                    modifier = Modifier.size(32.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    LanguageTranslations.getLocalizedText("Farmer Community", selectedLanguage),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
        }
        
        // Scrollable Content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    LanguageTranslations.getLocalizedText("Connect with Fellow Farmers", selectedLanguage),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
            
            // Community Features
            item {
                CommunityFeatureCard(
                    title = LanguageTranslations.getLocalizedText("Discussion Forums", selectedLanguage),
                    description = LanguageTranslations.getLocalizedText("Share experiences and ask questions", selectedLanguage),
                    icon = Icons.AutoMirrored.Filled.Chat,
                    features = listOf(
                        "Crop growing tips",
                        "Pest control solutions",
                        "Market price discussions",
                        "Weather impact on crops"
                    ),
                    selectedLanguage = selectedLanguage
                )
            }
            
            item {
                CommunityFeatureCard(
                    title = LanguageTranslations.getLocalizedText("Knowledge Sharing", selectedLanguage),
                    description = LanguageTranslations.getLocalizedText("Learn from successful farmers", selectedLanguage),
                    icon = Icons.Filled.School,
                    features = listOf(
                        "Best practices",
                        "Innovation techniques",
                        "Success stories",
                        "Troubleshooting guides"
                    ),
                    selectedLanguage = selectedLanguage
                )
            }
            
            item {
                CommunityFeatureCard(
                    title = LanguageTranslations.getLocalizedText("Local Groups", selectedLanguage),
                    description = LanguageTranslations.getLocalizedText("Connect with farmers in your area", selectedLanguage),
                    icon = Icons.Filled.LocationOn,
                    features = listOf(
                        "Regional farming groups",
                        "Local market information",
                        "Community projects",
                        "Shared resources"
                    ),
                    selectedLanguage = selectedLanguage
                )
            }
            
            // Join Community
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = InfoBlue),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            LanguageTranslations.getLocalizedText("Join the Community", selectedLanguage),
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            LanguageTranslations.getLocalizedText("Become part of our growing farmer community. Share knowledge, learn from others, and build lasting connections.", selectedLanguage),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                LanguageTranslations.getLocalizedText("Join Now", selectedLanguage),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = InfoBlue
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RegisterFarmerDetailedScreen(
    onBack: () -> Unit,
    selectedLanguage: String = "English"
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
    ) {
        // Header
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = WarningOrange,
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Image(
                    painter = painterResource(id = R.drawable.agribot_logo_option2_removebg_preview),
                    contentDescription = "Agribot Logo",
                    modifier = Modifier.size(32.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    LanguageTranslations.getLocalizedText("Farmer Registration", selectedLanguage),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
        }
        
        // Scrollable Content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    LanguageTranslations.getLocalizedText("Join Our Agricultural Network", selectedLanguage),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )
                )
            }
            
            // Benefits
            item {
                BenefitsCard(
                    title = LanguageTranslations.getLocalizedText("Registration Benefits", selectedLanguage),
                    benefits = listOf(
                        LanguageTranslations.getLocalizedText("Access to expert consultations", selectedLanguage),
                        LanguageTranslations.getLocalizedText("Market price updates", selectedLanguage),
                        LanguageTranslations.getLocalizedText("Weather forecasts", selectedLanguage),
                        LanguageTranslations.getLocalizedText("Government subsidy information", selectedLanguage),
                        LanguageTranslations.getLocalizedText("Community networking", selectedLanguage),
                        LanguageTranslations.getLocalizedText("Training programs", selectedLanguage),
                        LanguageTranslations.getLocalizedText("Financial support options", selectedLanguage),
                        LanguageTranslations.getLocalizedText("Technology access", selectedLanguage)
                    ),
                    selectedLanguage = selectedLanguage
                )
            }
            
            // Registration Form
            item {
                RegistrationFormCard(selectedLanguage = selectedLanguage)
            }
            
            // Success Stories
            item {
                SuccessStoriesCard(selectedLanguage = selectedLanguage)
            }
        }
    }
}

@Composable
fun SubsidiesDetailedScreen(
    onBack: () -> Unit,
    selectedLanguage: String = "English"
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
    ) {
        // Header
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = SecondaryGreen,
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Image(
                    painter = painterResource(id = R.drawable.agribot_logo_option2_removebg_preview),
                    contentDescription = "Agribot Logo",
                    modifier = Modifier.size(32.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    LanguageTranslations.getLocalizedText("Government Subsidies", selectedLanguage),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
        }
        
        // Scrollable Content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    LanguageTranslations.getLocalizedText("Available Support Programs", selectedLanguage),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )
                )
            }
            
            // Subsidy Programs
            item {
                SubsidyProgramCard(
                    title = LanguageTranslations.getLocalizedText("Fertilizer Subsidy Program", selectedLanguage),
                    description = LanguageTranslations.getLocalizedText("50% subsidy on NPK and Urea fertilizers", selectedLanguage),
                    eligibility = LanguageTranslations.getLocalizedText("Registered farmers with valid ID", selectedLanguage),
                    deadline = LanguageTranslations.getLocalizedText("Ongoing", selectedLanguage),
                    contact = LanguageTranslations.getLocalizedText("Ministry of Food and Agriculture", selectedLanguage) + ": 0302-665-000",
                    benefits = listOf(
                        LanguageTranslations.getLocalizedText("Reduced fertilizer costs", selectedLanguage),
                        LanguageTranslations.getLocalizedText("Improved soil fertility", selectedLanguage),
                        LanguageTranslations.getLocalizedText("Higher crop yields", selectedLanguage),
                        LanguageTranslations.getLocalizedText("Sustainable farming practices", selectedLanguage)
                    ),
                    selectedLanguage = selectedLanguage
                )
            }
            
            item {
                SubsidyProgramCard(
                    title = LanguageTranslations.getLocalizedText("Planting for Food and Jobs", selectedLanguage),
                    description = LanguageTranslations.getLocalizedText("Free seeds and technical support", selectedLanguage),
                    eligibility = LanguageTranslations.getLocalizedText("Smallholder farmers with less than 5 acres", selectedLanguage),
                    deadline = LanguageTranslations.getLocalizedText("Ongoing", selectedLanguage),
                    contact = LanguageTranslations.getLocalizedText("MoFA Extension Services", selectedLanguage) + ": 0302-665-001",
                    benefits = listOf(
                        LanguageTranslations.getLocalizedText("Free quality seeds", selectedLanguage),
                        LanguageTranslations.getLocalizedText("Technical guidance", selectedLanguage),
                        LanguageTranslations.getLocalizedText("Market access support", selectedLanguage),
                        LanguageTranslations.getLocalizedText("Training programs", selectedLanguage)
                    ),
                    selectedLanguage = selectedLanguage
                )
            }
            
            item {
                SubsidyProgramCard(
                    title = LanguageTranslations.getLocalizedText("Youth in Agriculture", selectedLanguage),
                    description = LanguageTranslations.getLocalizedText("Support for young farmers", selectedLanguage),
                    eligibility = LanguageTranslations.getLocalizedText("Farmers aged 18-35 years", selectedLanguage),
                    deadline = "December 31, 2024",
                    contact = LanguageTranslations.getLocalizedText("Youth in Agriculture", selectedLanguage) + ": 0302-665-002",
                    benefits = listOf(
                        LanguageTranslations.getLocalizedText("Startup capital", selectedLanguage),
                        LanguageTranslations.getLocalizedText("Land access support", selectedLanguage),
                        LanguageTranslations.getLocalizedText("Modern farming equipment", selectedLanguage),
                        LanguageTranslations.getLocalizedText("Mentorship programs", selectedLanguage)
                    ),
                    selectedLanguage = selectedLanguage
                )
            }
            
            // Application Guide
            item {
                ApplicationGuideCard(selectedLanguage = selectedLanguage)
            }
        }
    }
}

// Helper Composable Functions
@Composable
private fun ExpertCategoryCard(
    title: String,
    description: String,
    icon: ImageVector,
    experts: List<Expert>,
    selectedLanguage: String = "English"
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    icon,
                    contentDescription = title,
                    modifier = Modifier.size(32.dp),
                    tint = PrimaryGreen
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = DarkText
                        )
                    )
                    Text(
                        description,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MediumText
                        )
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            experts.forEach { expert ->
                ExpertItem(expert = expert)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun ExpertItem(expert: Expert) {
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
                    expert.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )
                )
                Text(
                    expert.specialization,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MediumText
                    )
                )
                Text(
                    "Experience: ${expert.experience}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = LightText
                    )
                )
            }
            
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    "Contact",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
        }
    }
}

@Composable
private fun CommunityFeatureCard(
    title: String,
    description: String,
    icon: ImageVector,
    features: List<String>,
    selectedLanguage: String = "English"
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    icon,
                    contentDescription = title,
                    modifier = Modifier.size(32.dp),
                    tint = SuccessGreen
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = DarkText
                        )
                    )
                    Text(
                        description,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MediumText
                        )
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            features.forEach { feature ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Filled.CheckCircle,
                        contentDescription = "Feature",
                        modifier = Modifier.size(20.dp),
                        tint = SuccessGreen
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        feature,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = DarkText
                        )
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Composable
private fun BenefitsCard(
    title: String,
    benefits: List<String>,
    selectedLanguage: String = "English"
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            benefits.forEach { benefit ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = "Benefit",
                        modifier = Modifier.size(20.dp),
                        tint = WarningOrange
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        benefit,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = DarkText
                        )
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun RegistrationFormCard(selectedLanguage: String = "English") {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
                            Text(
                    LanguageTranslations.getLocalizedText("Registration Form", selectedLanguage),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )
                )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = "",
                onValueChange = { },
                label = { Text(LanguageTranslations.getLocalizedText("Full Name", selectedLanguage)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            OutlinedTextField(
                value = "",
                onValueChange = { },
                label = { Text(LanguageTranslations.getLocalizedText("Phone Number", selectedLanguage)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            OutlinedTextField(
                value = "",
                onValueChange = { },
                label = { Text(LanguageTranslations.getLocalizedText("Location", selectedLanguage)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            OutlinedTextField(
                value = "",
                onValueChange = { },
                label = { Text(LanguageTranslations.getLocalizedText("Farm Size (acres)", selectedLanguage)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
                            Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = WarningOrange),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        LanguageTranslations.getLocalizedText("Submit Registration", selectedLanguage),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }
        }
    }
}

@Composable
private fun SuccessStoriesCard(selectedLanguage: String = "English") {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
                            Text(
                    LanguageTranslations.getLocalizedText("Success Stories", selectedLanguage),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )
                )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                "John from Kumasi increased his maize yield by 40% after joining our network and receiving expert guidance on soil management and pest control.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MediumText,
                    lineHeight = 24.sp
                )
            )
        }
    }
}

@Composable
private fun SubsidyProgramCard(
    title: String,
    description: String,
    eligibility: String,
    deadline: String,
    contact: String,
    benefits: List<String>,
    selectedLanguage: String = "English"
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                description,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MediumText,
                    fontWeight = FontWeight.Medium
                )
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                                Text(
                LanguageTranslations.getLocalizedText("Eligibility", selectedLanguage) + ":",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )
            )
            Text(
                eligibility,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MediumText
                )
            )
        }
        
        Column {
            Text(
                LanguageTranslations.getLocalizedText("Deadline", selectedLanguage) + ":",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )
            )
            Text(
                deadline,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MediumText
                )
            )
        }
    }
    
    Spacer(modifier = Modifier.height(16.dp))
    
    Text(
        LanguageTranslations.getLocalizedText("Contact", selectedLanguage) + ": $contact",
        style = MaterialTheme.typography.bodyMedium.copy(
            color = InfoBlue,
            fontWeight = FontWeight.SemiBold
        )
    )
    
    Spacer(modifier = Modifier.height(16.dp))
    
    Text(
        LanguageTranslations.getLocalizedText("Benefits", selectedLanguage) + ":",
        style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.Bold,
            color = DarkText
        )
    )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            benefits.forEach { benefit ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Filled.CheckCircle,
                        contentDescription = "Benefit",
                        modifier = Modifier.size(20.dp),
                        tint = SuccessGreen
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        benefit,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = DarkText
                        )
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = SecondaryGreen),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    LanguageTranslations.getLocalizedText("Apply Now", selectedLanguage),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
        }
    }
}

@Composable
private fun ApplicationGuideCard(selectedLanguage: String = "English") {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AccentGold),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
                            Text(
                    LanguageTranslations.getLocalizedText("Application Guide", selectedLanguage),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )
                )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                "1. Gather required documents (ID, land documents, farm photos)\n" +
                "2. Visit your local MoFA office or apply online\n" +
                "3. Complete the application form\n" +
                "4. Submit supporting documents\n" +
                "5. Wait for approval (usually 2-4 weeks)\n" +
                "6. Receive your subsidy or support package",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = DarkText,
                    lineHeight = 24.sp
                )
            )
        }
    }
}

// Data Classes - Using Expert from MoreScreens.kt
