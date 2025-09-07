package com.agribot.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.text.style.TextAlign
import com.agribot.ui.screens.LanguageTranslations.getLocalizedText
import com.agribot.data.CalendarEventsManager
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.DayOfWeek
import java.util.*
import java.util.UUID

@Composable
fun CalendarScreen(selectedLanguage: String = "English") {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var showAddEventDialog by remember { mutableStateOf(false) }
    var showNotificationSettings by remember { mutableStateOf(false) }
    
    // Use shared events manager
    val events by remember { mutableStateOf(CalendarEventsManager.events) }
    
    // Initialize sample events on first launch
    LaunchedEffect(Unit) {
        CalendarEventsManager.initializeSampleEvents()
    }
    
    val currentMonth = selectedDate.monthValue
    val currentYear = selectedDate.year
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Header
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = getLocalizedText("Calendar", selectedLanguage),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF16A34A)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = getLocalizedText("Farming Calendar", selectedLanguage),
                        fontSize = 16.sp,
                        color = Color(0xFF6B7280)
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // Notification Settings Button
                    Button(
                        onClick = { showNotificationSettings = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF16A34A)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Notifications,
                            contentDescription = "Notification Settings",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Notification Settings")
                    }
                }
            }
        }
        
        item {
            // Month Navigation
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            selectedDate = selectedDate.minusMonths(1)
                        }
                    ) {
                        Icon(Icons.Filled.ChevronLeft, "Previous Month")
                    }
                    
                    Text(
                        text = "${getMonthName(currentMonth, selectedLanguage)} $currentYear",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    IconButton(
                        onClick = {
                            selectedDate = selectedDate.plusMonths(1)
                        }
                    ) {
                        Icon(Icons.Filled.ChevronRight, "Next Month")
                    }
                }
            }
        }
        
        item {
            // Calendar Grid
            CalendarGrid(
                selectedDate = selectedDate,
                events = events,
                selectedLanguage = selectedLanguage,
                onDateSelected = { date ->
                    selectedDate = date
                }
            )
        }
        
        item {
            // Quick Add Event
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = getLocalizedText("Quick Actions", selectedLanguage),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF16A34A)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Button(
                        onClick = { showAddEventDialog = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF16A34A))
                    ) {
                        Icon(Icons.Filled.Add, "Add Event")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(getLocalizedText("Add Farming Event", selectedLanguage))
                    }
                }
            }
        }
        
        item {
            // Seasonal Farming Guide
            SeasonalFarmingGuide(selectedLanguage = selectedLanguage)
        }
        
        // Display events for selected date
        val selectedDateKey = "${selectedDate.year}-${selectedDate.monthValue.toString().padStart(2, '0')}-${selectedDate.dayOfMonth.toString().padStart(2, '0')}"
        val dayEvents = events[selectedDateKey] ?: emptyList()
        
        if (dayEvents.isNotEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "${getLocalizedText("Events for", selectedLanguage)} ${selectedDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF16A34A)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        dayEvents.forEach { event ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        when (event.type) {
                                            EventType.PLANTING -> Icons.Filled.Agriculture
                                            EventType.HARVEST -> Icons.Filled.Grass
                                            EventType.FERTILIZER -> Icons.Filled.Science
                                            EventType.PEST_CONTROL -> Icons.Filled.BugReport
                                            else -> Icons.Filled.Event
                                        },
                                        contentDescription = event.type.name,
                                        tint = Color(0xFF16A34A),
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = event.title,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 16.sp
                                        )
                                        Text(
                                            text = event.description,
                                            fontSize = 14.sp,
                                            color = Color(0xFF6B7280)
                                        )
                                    }
                                    IconButton(
                                        onClick = {
                                            CalendarEventsManager.deleteEvent(event.id)
                                        }
                                    ) {
                                        Icon(
                                            Icons.Filled.Delete,
                                            contentDescription = getLocalizedText("Delete", selectedLanguage),
                                            tint = Color(0xFFEF4444),
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        // Show all added events section
        val allEvents = CalendarEventsManager.events.values.flatten().sortedBy { it.date }
        if (allEvents.isNotEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = getLocalizedText("All Farming Events", selectedLanguage),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF16A34A)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        allEvents.forEach { event ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        when (event.type) {
                                            EventType.PLANTING -> Icons.Filled.Agriculture
                                            EventType.HARVEST -> Icons.Filled.Grass
                                            EventType.FERTILIZER -> Icons.Filled.Science
                                            EventType.PEST_CONTROL -> Icons.Filled.BugReport
                                            else -> Icons.Filled.Event
                                        },
                                        contentDescription = event.type.name,
                                        tint = Color(0xFF16A34A),
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = event.title,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color(0xFF1F2937)
                                        )
                                        Text(
                                            text = event.description,
                                            fontSize = 14.sp,
                                            color = Color(0xFF6B7280)
                                        )
                                        Text(
                                            text = event.date.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")),
                                            fontSize = 12.sp,
                                            color = Color(0xFF16A34A),
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                    IconButton(
                                        onClick = {
                                            CalendarEventsManager.deleteEvent(event.id)
                                        }
                                    ) {
                                        Icon(
                                            Icons.Filled.Delete,
                                            contentDescription = getLocalizedText("Delete", selectedLanguage),
                                            tint = Color(0xFFEF4444),
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    if (showAddEventDialog) {
        AddEventDialog(
            onDismiss = { showAddEventDialog = false },
            onEventAdded = { event ->
                CalendarEventsManager.addEvent(event)
                showAddEventDialog = false
            },
            selectedLanguage = selectedLanguage,
            selectedDate = selectedDate
        )
    }
    
    // Notification Settings Dialog
    if (showNotificationSettings) {
        NotificationSettingsDialog(
            selectedLanguage = selectedLanguage,
            onDismiss = { showNotificationSettings = false }
        )
    }
}

@Composable
fun CalendarGrid(
    selectedDate: LocalDate,
    events: Map<String, List<CalendarEvent>>,
    selectedLanguage: String,
    onDateSelected: (LocalDate) -> Unit
) {
    // Memoize expensive calculations
    val firstDayOfMonth = remember(selectedDate) { selectedDate.withDayOfMonth(1) }
    val lastDayOfMonth = remember(selectedDate) { selectedDate.withDayOfMonth(selectedDate.lengthOfMonth()) }
    val firstDayOfWeek = remember(firstDayOfMonth) { firstDayOfMonth.dayOfWeek.value }
    val daysInMonth = remember(selectedDate) { selectedDate.lengthOfMonth() }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Day headers
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach { day ->
                    Text(
                        text = day,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = Color(0xFF6B7280)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Calendar days
            var dayCounter = 1
            repeat(6) { week ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    repeat(7) { dayOfWeek ->
                        val dayNumber = if (week == 0 && dayOfWeek < firstDayOfWeek) {
                            null
                        } else if (dayCounter > daysInMonth) {
                            null
                        } else {
                            dayCounter++
                        }
                        
                        if (dayNumber != null) {
                            val currentDate = selectedDate.withDayOfMonth(dayNumber)
                            val dateKey = "${currentDate.year}-${currentDate.monthValue.toString().padStart(2, '0')}-${currentDate.dayOfMonth.toString().padStart(2, '0')}"
                            val hasEvents = events[dateKey]?.isNotEmpty() == true
                            val isToday = currentDate == LocalDate.now()
                            val isSelected = currentDate == selectedDate
                            
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .padding(2.dp)
                                    .background(
                                        when {
                                            isSelected -> Color(0xFF16A34A)
                                            isToday -> Color(0xFFE8F5E8)
                                            else -> Color.Transparent
                                        },
                                        RoundedCornerShape(8.dp)
                                    )
                                    .clickable {
                                        onDateSelected(currentDate)
                                    }
                                    .padding(4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = dayNumber.toString(),
                                        fontSize = 14.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = when {
                                            isSelected -> Color.White
                                            isToday -> Color(0xFF16A34A)
                                            else -> Color(0xFF1F2937)
                                        }
                                    )
                                    if (hasEvents) {
                                        Box(
                                            modifier = Modifier
                                                .size(4.dp)
                                                .background(
                                                    if (isSelected) Color.White else Color(0xFF16A34A),
                                                    RoundedCornerShape(2.dp)
                                                )
                                        )
                                    }
                                }
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .padding(2.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SeasonalFarmingGuide(selectedLanguage: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = getLocalizedText("Seasonal Farming Guide", selectedLanguage),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF16A34A)
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            val currentMonth = LocalDate.now().monthValue
            val season = when (currentMonth) {
                in 3..5 -> "Planting Season"
                in 6..8 -> "Rainy Season"
                in 9..11 -> "Harvest Season"
                else -> "Dry Season"
            }
            
            Text(
                text = getLocalizedText(season, selectedLanguage),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF059669)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            val recommendations = when (currentMonth) {
                in 3..5 -> listOf(
                    "🌱 Plant maize and cassava",
                    "💧 Prepare irrigation systems",
                    "🌾 Start rice cultivation"
                )
                in 6..8 -> listOf(
                    "🌧️ Monitor rainfall patterns",
                    "🛡️ Protect crops from pests",
                    "💚 Apply organic fertilizers"
                )
                in 9..11 -> listOf(
                    "🌾 Harvest mature crops",
                    "📦 Prepare storage facilities",
                    "🌱 Plan next season crops"
                )
                else -> listOf(
                    "☀️ Maintain soil moisture",
                    "🌱 Plant drought-resistant crops",
                    "🛠️ Repair farm equipment"
                )
            }
            
            recommendations.forEach { recommendation ->
                Text(
                    text = recommendation,
                    fontSize = 14.sp,
                    color = Color(0xFF6B7280),
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
        }
    }
}

@Composable
fun AddEventDialog(
    onDismiss: () -> Unit,
    onEventAdded: (CalendarEvent) -> Unit,
    selectedLanguage: String,
    selectedDate: LocalDate = LocalDate.now()
) {
                    var title by remember { mutableStateOf("") }
                var description by remember { mutableStateOf("") }
                var selectedType by remember { mutableStateOf(EventType.PLANTING) }
                var enableReminder by remember { mutableStateOf(true) }
                var reminderTime by remember { mutableStateOf("09:00") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(getLocalizedText("Add Farming Event", selectedLanguage)) },
        text = {
            Column {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(getLocalizedText("Event Title", selectedLanguage)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(getLocalizedText("Description", selectedLanguage)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = getLocalizedText("Event Type", selectedLanguage),
                    fontWeight = FontWeight.SemiBold
                )
                EventType.values().forEach { type ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedType == type,
                            onClick = { selectedType = type }
                        )
                        Text(
                            text = getLocalizedText(type.name, selectedLanguage),
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Reminder Options
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Set Reminder")
                    Switch(
                        checked = enableReminder,
                        onCheckedChange = { enableReminder = it }
                    )
                }
                
                if (enableReminder) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Reminder time:")
                    TextField(
                        value = reminderTime,
                        onValueChange = { reminderTime = it },
                        label = { Text("Time (HH:MM)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        onEventAdded(
                            CalendarEvent(
                                title = title,
                                description = description,
                                type = selectedType,
                                date = selectedDate
                            )
                        )
                    }
                }
            ) {
                Text(getLocalizedText("Add", selectedLanguage))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(getLocalizedText("Cancel", selectedLanguage))
            }
        }
    )
}

data class CalendarEvent(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val type: EventType,
    val date: LocalDate
)

enum class EventType {
    PLANTING,
    HARVEST,
    FERTILIZER,
    PEST_CONTROL,
    OTHER
}

fun getMonthName(month: Int, language: String): String {
    return when (month) {
        1 -> when (language) {
            "Twi" -> "Sanda"
            "Ewe" -> "Dzove"
            "Ga" -> "Sanda"
            "Dagbani" -> "Sanda"
            "Fante" -> "Sanda"
            "Hausa" -> "Janairu"
            else -> "January"
        }
        2 -> when (language) {
            "Twi" -> "Kwakwar"
            "Ewe" -> "Dzodze"
            "Ga" -> "Kwakwar"
            "Dagbani" -> "Kwakwar"
            "Fante" -> "Kwakwar"
            "Hausa" -> "Faburairu"
            else -> "February"
        }
        3 -> when (language) {
            "Twi" -> "Ebɔbira"
            "Ewe" -> "Tedoxe"
            "Ga" -> "Ebɔbira"
            "Dagbani" -> "Ebɔbira"
            "Fante" -> "Ebɔbira"
            "Hausa" -> "Maris"
            else -> "March"
        }
        4 -> when (language) {
            "Twi" -> "Ebɔbira"
            "Ewe" -> "Afɔfie"
            "Ga" -> "Ebɔbira"
            "Dagbani" -> "Ebɔbira"
            "Fante" -> "Ebɔbira"
            "Hausa" -> "Afrilu"
            else -> "April"
        }
        5 -> when (language) {
            "Twi" -> "Esusow Aketseaba"
            "Ewe" -> "Dama"
            "Ga" -> "Esusow Aketseaba"
            "Dagbani" -> "Esusow Aketseaba"
            "Fante" -> "Esusow Aketseaba"
            "Hausa" -> "Mayu"
            else -> "May"
        }
        6 -> when (language) {
            "Twi" -> "Obirade"
            "Ewe" -> "Masa"
            "Ga" -> "Obirade"
            "Dagbani" -> "Obirade"
            "Fante" -> "Obirade"
            "Hausa" -> "Yuni"
            else -> "June"
        }
        7 -> when (language) {
            "Twi" -> "Ayɛwohomumɔ"
            "Ewe" -> "Siamlɔm"
            "Ga" -> "Ayɛwohomumɔ"
            "Dagbani" -> "Ayɛwohomumɔ"
            "Fante" -> "Ayɛwohomumɔ"
            "Hausa" -> "Yuli"
            else -> "July"
        }
        8 -> when (language) {
            "Twi" -> "Daketɔ"
            "Ewe" -> "Deasiamime"
            "Ga" -> "Daketɔ"
            "Dagbani" -> "Daketɔ"
            "Fante" -> "Daketɔ"
            "Hausa" -> "Agusta"
            else -> "August"
        }
        9 -> when (language) {
            "Twi" -> "Fankwa"
            "Ewe" -> "Anyɔnyɔ"
            "Ga" -> "Fankwa"
            "Dagbani" -> "Fankwa"
            "Fante" -> "Fankwa"
            "Hausa" -> "Satumba"
            else -> "September"
        }
        10 -> when (language) {
            "Twi" -> "Ɔbɛsɛ"
            "Ewe" -> "Kele"
            "Ga" -> "Ɔbɛsɛ"
            "Dagbani" -> "Ɔbɛsɛ"
            "Fante" -> "Ɔbɛsɛ"
            "Hausa" -> "Oktoba"
            else -> "October"
        }
        11 -> when (language) {
            "Twi" -> "Ɔberɛfɛw"
            "Ewe" -> "Adeɛmekpɔxe"
            "Ga" -> "Ɔberɛfɛw"
            "Dagbani" -> "Ɔberɛfɛw"
            "Fante" -> "Ɔberɛfɛw"
            "Hausa" -> "Nuwamba"
            else -> "November"
        }
        12 -> when (language) {
            "Twi" -> "Mumu"
            "Ewe" -> "Dzome"
            "Ga" -> "Mumu"
            "Dagbani" -> "Mumu"
            "Fante" -> "Mumu"
            "Hausa" -> "Disamba"
            else -> "December"
        }
        else -> "Unknown"
    }
}

@Composable
fun NotificationSettingsDialog(
    selectedLanguage: String,
    onDismiss: () -> Unit
) {
    var dailyReminderTime by remember { mutableStateOf("08:00") }
    var enableDailyReminders by remember { mutableStateOf(true) }
    var enableEventReminders by remember { mutableStateOf(true) }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Notification Settings",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Daily Reminder Toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Daily Farming Reminders")
                    Switch(
                        checked = enableDailyReminders,
                        onCheckedChange = { enableDailyReminders = it }
                    )
                }
                
                // Daily Reminder Time
                if (enableDailyReminders) {
                    Text("Daily reminder time:")
                    // Simple time picker (you can enhance this)
                    TextField(
                        value = dailyReminderTime,
                        onValueChange = { dailyReminderTime = it },
                        label = { Text("Time (HH:MM)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                
                // Event Reminder Toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Event Reminders")
                    Switch(
                        checked = enableEventReminders,
                        onCheckedChange = { enableEventReminders = it }
                    )
                }
                
                if (enableEventReminders) {
                    Text(
                        text = "You'll receive notifications 1 hour before each farming event",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    // Save notification settings
                    onDismiss()
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
