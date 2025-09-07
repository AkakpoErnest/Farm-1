package com.agribot.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.agribot.R

class NotificationReceiver : BroadcastReceiver() {
    
    companion object {
        private const val CHANNEL_ID = "farming_reminders"
    }
    
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            "DAILY_REMINDER" -> {
                showDailyReminder(context)
            }
            else -> {
                // Handle event-specific reminders
                val eventTitle = intent.getStringExtra("event_title") ?: "Farming Event"
                val eventDescription = intent.getStringExtra("event_description") ?: "Time for your farming activity!"
                showEventReminder(context, eventTitle, eventDescription)
            }
        }
    }
    
    private fun showDailyReminder(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        
        // Create notification channel if needed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Farming Reminders",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Daily farming reminders"
                enableVibration(true)
                enableLights(true)
            }
            notificationManager.createNotificationChannel(channel)
        }
        
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("🌾 Daily Farming Reminder")
            .setContentText("Check your farming calendar for today's activities!")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()
        
        notificationManager.notify(1001, notification)
    }
    
    private fun showEventReminder(context: Context, eventTitle: String, eventDescription: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        
        // Create notification channel if needed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Farming Reminders",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Farming event reminders"
                enableVibration(true)
                enableLights(true)
            }
            notificationManager.createNotificationChannel(channel)
        }
        
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("🌾 $eventTitle")
            .setContentText(eventDescription)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()
        
        notificationManager.notify(eventTitle.hashCode(), notification)
    }
}
