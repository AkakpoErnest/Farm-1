package com.agribot.data

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.agribot.ui.screens.CalendarEvent
import com.agribot.ui.screens.EventType
import java.time.LocalDate

object CalendarEventsManager {
    private var _events by mutableStateOf(mutableMapOf<String, List<CalendarEvent>>())
    
    val events: Map<String, List<CalendarEvent>> get() = _events
    
    fun addEvent(event: CalendarEvent) {
        val dateKey = "${event.date.year}-${event.date.monthValue.toString().padStart(2, '0')}-${event.date.dayOfMonth.toString().padStart(2, '0')}"
        val currentEvents = _events[dateKey] ?: emptyList()
        val newEvents = _events.toMutableMap()
        newEvents[dateKey] = currentEvents + event
        _events = newEvents
    }
    
    fun getUpcomingEvents(limit: Int = 5): List<CalendarEvent> {
        val today = LocalDate.now()
        val allEvents = mutableListOf<CalendarEvent>()
        
        _events.values.forEach { eventList ->
            allEvents.addAll(eventList)
        }
        
        return allEvents
            .filter { it.date >= today }
            .sortedBy { it.date }
            .take(limit)
    }
    
    fun initializeSampleEvents() {
        if (_events.isEmpty()) {
            val today = LocalDate.now()
            val tomorrow = today.plusDays(1)
            val nextWeek = today.plusDays(7)
            
            addEvent(CalendarEvent(
                title = "Plant Maize",
                description = "Time to plant maize seeds",
                type = EventType.PLANTING,
                date = today
            ))
            
            addEvent(CalendarEvent(
                title = "Apply Fertilizer",
                description = "Apply organic fertilizer to crops",
                type = EventType.FERTILIZER,
                date = tomorrow
            ))
            
            addEvent(CalendarEvent(
                title = "Pest Control",
                description = "Check for pests and apply control measures",
                type = EventType.PEST_CONTROL,
                date = nextWeek
            ))
        }
    }
    
    fun updateEvents(newEvents: Map<String, List<CalendarEvent>>) {
        _events = newEvents.toMutableMap()
    }
    
    fun deleteEvent(eventId: String) {
        val newEvents = _events.toMutableMap()
        for ((dateKey, eventList) in newEvents) {
            val filteredEvents = eventList.filter { it.id != eventId }
            if (filteredEvents.size != eventList.size) {
                // Event was found and removed
                if (filteredEvents.isEmpty()) {
                    newEvents.remove(dateKey)
                } else {
                    newEvents[dateKey] = filteredEvents
                }
                break
            }
        }
        _events = newEvents
    }
}
