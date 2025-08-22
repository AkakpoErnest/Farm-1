package com.agribot.services

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.agribot.data.User
import com.agribot.data.UserRole
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseAuthService {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    
    // Get current user
    fun getCurrentUser(): FirebaseUser? = auth.currentUser
    
    // Check if user is authenticated
    fun isAuthenticated(): Boolean = auth.currentUser != null
    
    // Sign in with email and password
    suspend fun signIn(email: String, password: String): Result<User> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user
            if (firebaseUser != null) {
                // Get user data from Firestore using custom deserializer
                val userDoc = firestore.collection("users").document(firebaseUser.uid).get().await()
                var user = User.fromFirestore(userDoc)
                
                // Fallback to standard deserialization if custom fails
                if (user == null) {
                    try {
                        user = userDoc.toObject(User::class.java)
                    } catch (e: Exception) {
                        // If both fail, create a basic user object
                        user = User(
                            id = firebaseUser.uid,
                            email = firebaseUser.email ?: "",
                            name = firebaseUser.displayName ?: "User",
                            role = UserRole.FARMER
                        )
                    }
                }
                
                if (user != null) {
                    Result.success(user)
                } else {
                    Result.failure(Exception("User data not found"))
                }
            } else {
                Result.failure(Exception("Authentication failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // Create user with email and password
    suspend fun createUser(email: String, password: String, userData: Map<String, String>, role: UserRole): Result<User> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user
            if (firebaseUser != null) {
                // Create user object
                val user = User(
                    id = firebaseUser.uid,
                    email = email,
                    name = userData["name"] ?: "",
                    role = role,
                    phone = userData["phone"],
                    location = userData["location"],
                    farmSize = userData["farmSize"],
                    crops = userData["crops"]?.split(",")?.map { it.trim() } ?: emptyList(),
                    experience = userData["experience"]
                )
                
                // Save user to Firestore
                firestore.collection("users").document(firebaseUser.uid).set(user).await()
                
                Result.success(user)
            } else {
                Result.failure(Exception("User creation failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // Sign out
    fun signOut() {
        auth.signOut()
    }
    
    // Password reset
    suspend fun resetPassword(email: String): Result<Unit> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // Update user profile
    suspend fun updateProfile(user: User): Result<Unit> {
        return try {
            firestore.collection("users").document(user.id).set(user).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
