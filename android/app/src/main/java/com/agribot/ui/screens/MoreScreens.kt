package com.agribot.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.draw.alpha
import com.agribot.R

// Data classes needed by DetailedScreens.kt
data class Expert(
    val name: String,
    val specialization: String,
    val experience: String,
    val contact: String,
    val availability: String,
    val education: String,
    val languages: List<String>
)

data class Farmer(
    val name: String,
    val location: String,
    val farmSize: String,
    val crops: List<String>,
    val experience: String,
    val contact: String,
    val registrationDate: String
)

data class Subsidy(
    val name: String,
    val description: String,
    val amount: String,
    val eligibility: String,
    val deadline: String,
    val applicationProcess: String
)

data class FarmTool(
    val name: String,
    val description: String,
    val price: String,
    val priority: String,
    val benefits: String
)

data class Field(
    val name: String,
    val location: String,
    val harvestDate: String,
    val yield: String,
    val status: String
)

// Complete implementations for the screens that App.kt needs
@Composable
fun FarmToolsScreen(selectedLanguage: String = "English") {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.farm_background),
            contentDescription = "Farm Background",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.2f),
            contentScale = ContentScale.Crop
        )
        
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        ) {
            // Header
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary,
                    shadowElevation = 4.dp
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.agribot_logo_option2_removebg_preview),
                            contentDescription = "Agribot Logo",
                            modifier = Modifier.size(32.dp),
                            contentScale = ContentScale.Fit
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Farm Tools & Equipment",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Essential Tools Section
            item {
                Text(
                    text = "Essential Farm Tools",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            // Tools Grid
            item {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(getEssentialTools()) { tool ->
                        FarmToolCard(tool = tool)
                    }
                }
            }

            // Modern Equipment Section
            item {
                Text(
                    text = "Modern Equipment",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            // Equipment List
            items(getModernEquipment()) { equipment ->
                EquipmentCard(equipment = equipment)
            }

            // Tool Maintenance Tips
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
                            text = "Tool Maintenance Tips",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E7D32)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        val tips = listOf(
                            "Clean tools after each use to prevent rust",
                            "Store tools in a dry place",
                            "Sharpen cutting tools regularly",
                            "Oil moving parts monthly",
                            "Check handles for cracks or damage"
                        )
                        
                        tips.forEach { tip ->
                            Row(
                                modifier = Modifier.padding(vertical = 2.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    text = "• ",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color(0xFF2E7D32)
                                )
                                Text(
                                    text = tip,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color(0xFF2E7D32)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FarmToolCard(tool: FarmTool) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = tool.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = tool.description,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = tool.price,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50)
            )
        }
    }
}

@Composable
fun EquipmentCard(equipment: ModernEquipment) {
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
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = equipment.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = equipment.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    text = "Benefits: ${equipment.benefits}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF2E7D32)
                )
            }
            
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = equipment.price,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Surface(
                    color = when (equipment.priority) {
                        "High" -> Color(0xFFFFEBEE)
                        "Medium" -> Color(0xFFFFF3E0)
                        else -> Color(0xFFE8F5E8)
                    },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = equipment.priority,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = when (equipment.priority) {
                            "High" -> Color(0xFFD32F2F)
                            "Medium" -> Color(0xFFE65100)
                            else -> Color(0xFF2E7D32)
                        }
                    )
                }
            }
        }
    }
}

// Data class for modern equipment
data class ModernEquipment(
    val name: String,
    val description: String,
    val price: String,
    val priority: String,
    val benefits: String
)

// Helper functions
fun getEssentialTools(): List<FarmTool> {
    return listOf(
        FarmTool("Machete", "Essential cutting tool for clearing vegetation", "GH₵ 25", "High", "Versatile cutting tool"),
        FarmTool("Hoe", "Primary cultivation tool for breaking soil", "GH₵ 30", "High", "Soil preparation"),
        FarmTool("Shovel", "Digging and moving soil", "GH₵ 35", "High", "Digging and planting"),
        FarmTool("Rake", "Leveling and collecting debris", "GH₵ 20", "Medium", "Field preparation"),
        FarmTool("Watering Can", "Manual irrigation for small plots", "GH₵ 15", "Medium", "Precise watering"),
        FarmTool("Pruning Shears", "Trimming plants and harvesting", "GH₵ 40", "Medium", "Plant maintenance")
    )
}

fun getModernEquipment(): List<ModernEquipment> {
    return listOf(
        ModernEquipment(
            "Tractor (Small)", 
            "Multi-purpose mechanized farming", 
            "GH₵ 45,000", 
            "High", 
            "Faster cultivation, multiple attachments"
        ),
        ModernEquipment(
            "Irrigation System", 
            "Automated drip irrigation setup", 
            "GH₵ 2,500", 
            "High", 
            "Water conservation, consistent watering"
        ),
        ModernEquipment(
            "Seed Planter", 
            "Precision seed placement tool", 
            "GH₵ 800", 
            "Medium", 
            "Uniform planting, time saving"
        ),
        ModernEquipment(
            "Fertilizer Spreader", 
            "Even distribution of fertilizers", 
            "GH₵ 400", 
            "Medium", 
            "Uniform application, reduced waste"
        ),
        ModernEquipment(
            "Harvest Crates", 
            "Durable storage for harvested crops", 
            "GH₵ 60/crate", 
            "Low", 
            "Organized storage, transport ready"
        ),
        ModernEquipment(
            "Weighing Scale", 
            "Digital scale for accurate measurements", 
            "GH₵ 200", 
            "Low", 
            "Precise measurements, better pricing"
        )
    )
}

@Composable  
fun ExpertsScreen(selectedLanguage: String = "English") {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "Experts Screen",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun FarmersScreen(selectedLanguage: String = "English") {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "Farmers Screen", 
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun RegisterFarmerScreen(selectedLanguage: String = "English") {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "Register Farmer Screen",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun SubsidiesScreen(selectedLanguage: String = "English") {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "Subsidies Screen",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }
}
