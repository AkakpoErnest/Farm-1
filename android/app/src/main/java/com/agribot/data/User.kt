package com.agribot.data

import androidx.annotation.Keep
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude

@Keep
data class User(
    val id: String = "",
    val email: String = "",
    val name: String = "",
    val role: UserRole = UserRole.FARMER,
    val phone: String? = null,
    val location: String? = null,
    val farmSize: String? = null,
    val crops: List<String> = emptyList(),
    val experience: String? = null,
    val avatar: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val isVerified: Boolean = false,
    val preferences: Map<String, String> = emptyMap()
) {
    // Required no-argument constructor for Firestore
    constructor() : this(
        id = "",
        email = "",
        name = "",
        role = UserRole.FARMER,
        phone = null,
        location = null,
        farmSize = null,
        crops = emptyList(),
        experience = null,
        avatar = null,
        createdAt = System.currentTimeMillis(),
        updatedAt = System.currentTimeMillis(),
        isVerified = false,
        preferences = emptyMap()
    )
    
    // Custom deserializer for Firestore
    companion object {
        fun fromFirestore(document: DocumentSnapshot): User? {
            return try {
                val data = document.data
                if (data != null) {
                    User(
                        id = document.id,
                        email = data["email"] as? String ?: "",
                        name = data["name"] as? String ?: "",
                        role = try {
                            UserRole.valueOf(data["role"] as? String ?: "FARMER")
                        } catch (e: Exception) {
                            UserRole.FARMER
                        },
                        phone = data["phone"] as? String,
                        location = data["location"] as? String,
                        farmSize = data["farmSize"] as? String,
                        crops = (data["crops"] as? List<*>)?.mapNotNull { it as? String } ?: emptyList(),
                        experience = data["experience"] as? String,
                        avatar = data["avatar"] as? String,
                        createdAt = data["createdAt"] as? Long ?: System.currentTimeMillis(),
                        updatedAt = data["updatedAt"] as? Long ?: System.currentTimeMillis(),
                        isVerified = data["isVerified"] as? Boolean ?: false,
                        preferences = (data["preferences"] as? Map<*, *>)?.mapValues { (it.value as? String) ?: "" }?.let { it as Map<String, String> } ?: emptyMap()
                    )
                } else null
            } catch (e: Exception) {
                null
            }
        }
    }
}

@Keep
enum class UserRole(val displayName: String, val description: String, val icon: String) {
    FARMER("Farmer", "Agricultural producer and crop cultivator", "🌾"),
    CUSTOMER("Customer", "Agricultural product buyer and consumer", "🛒"),
    EXPERT("Expert", "Agricultural consultant and specialist", "👨‍🌾")
}

// Extension functions for better UX
fun UserRole.getRequiredFields(): List<String> {
    return when (this) {
        UserRole.FARMER -> listOf("name", "phone", "location", "farmSize", "crops")
        UserRole.CUSTOMER -> listOf("name", "phone", "location")
        UserRole.EXPERT -> listOf("name", "phone", "location", "experience")
    }
}

fun UserRole.getFieldLabels(): Map<String, String> {
    return when (this) {
        UserRole.FARMER -> mapOf(
            "name" to "Full Name",
            "phone" to "Phone Number",
            "location" to "Farm Location",
            "farmSize" to "Farm Size (acres)",
            "crops" to "Main Crops Grown"
        )
        UserRole.CUSTOMER -> mapOf(
            "name" to "Full Name",
            "phone" to "Phone Number",
            "location" to "Location"
        )
        UserRole.EXPERT -> mapOf(
            "name" to "Full Name",
            "phone" to "Phone Number",
            "location" to "Service Area",
            "experience" to "Years of Experience"
        )
    }
}
