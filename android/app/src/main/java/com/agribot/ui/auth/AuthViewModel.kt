package com.agribot.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.agribot.data.User
import com.agribot.data.UserRole
import com.agribot.data.UserPreferencesRepository
import com.agribot.services.FirebaseAuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

data class AuthState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val currentUser: User? = null,
    val error: String? = null,
    val isFirstTime: Boolean = true
)

sealed class AuthEvent {
    data class Login(val email: String, val password: String) : AuthEvent()
    data class Register(val userData: Map<String, String>, val role: UserRole) : AuthEvent()
    data object Logout : AuthEvent()
    data object ClearError : AuthEvent()
}

class AuthViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = UserPreferencesRepository(app.applicationContext)
    private val firebaseAuth = FirebaseAuthService()
    val prefs = repo.prefsFlow.stateIn(viewModelScope, SharingStarted.Eagerly, null)
    
    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState
    
    init {
        checkAuthStatus()
    }
    
    private fun checkAuthStatus() {
        viewModelScope.launch {
            // Check if Firebase user is authenticated
            if (firebaseAuth.isAuthenticated()) {
                val firebaseUser = firebaseAuth.getCurrentUser()
                if (firebaseUser != null) {
                    // Get user data from Firestore
                    try {
                        val userDoc = firebaseAuth.getCurrentUser()?.let { user ->
                            // For now, create a basic user object
                            // In production, you'd fetch from Firestore
                            User(
                                id = user.uid,
                                email = user.email ?: "",
                                name = user.displayName ?: "User",
                                role = UserRole.FARMER // Default role, can be updated later
                            )
                        }
                        if (userDoc != null) {
                            _authState.value = _authState.value.copy(
                                isAuthenticated = true,
                                currentUser = userDoc,
                                isFirstTime = false
                            )
                        }
                    } catch (e: Exception) {
                        // Handle error
                    }
                }
            }
        }
    }
    
    fun handleEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.Login -> login(event.email, event.password)
            is AuthEvent.Register -> register(event.userData, event.role)
            is AuthEvent.Logout -> logout()
            is AuthEvent.ClearError -> clearError()
        }
    }
    
    private fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = _authState.value.copy(isLoading = true, error = null)
            
            try {
                // Use Firebase Authentication
                val result = firebaseAuth.signIn(email, password)
                if (result.isSuccess) {
                    val user = result.getOrNull()!!
                    repo.setLoggedIn(email, user.role.name)
                    _authState.value = _authState.value.copy(
                        isLoading = false,
                        isAuthenticated = true,
                        currentUser = user,
                        isFirstTime = false
                    )
                } else {
                    _authState.value = _authState.value.copy(
                        isLoading = false,
                        error = result.exceptionOrNull()?.message ?: "Login failed"
                    )
                }
            } catch (e: Exception) {
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    error = "Login failed: ${e.message}"
                )
            }
        }
    }
    
    private fun register(userData: Map<String, String>, role: UserRole) {
        viewModelScope.launch {
            _authState.value = _authState.value.copy(isLoading = true, error = null)
            
            try {
                // Simple validation
                val requiredFields = listOf("name", "email", "password")
                val missingFields = requiredFields.filter { field ->
                    userData[field]?.isBlank() == true
                }
                
                if (missingFields.isNotEmpty()) {
                    _authState.value = _authState.value.copy(
                        isLoading = false,
                        error = "Please fill in: ${missingFields.joinToString(", ")}"
                    )
                    return@launch
                }
                
                // Create user with Firebase
                val email = userData["email"] ?: ""
                val password = userData["password"] ?: ""
                val result = firebaseAuth.createUser(email, password, userData, role)
                
                if (result.isSuccess) {
                    val newUser = result.getOrNull()!!
                    repo.setLoggedIn(newUser.email, newUser.role.name)
                    _authState.value = _authState.value.copy(
                        isLoading = false,
                        isAuthenticated = true,
                        currentUser = newUser,
                        isFirstTime = false
                    )
                } else {
                    _authState.value = _authState.value.copy(
                        isLoading = false,
                        error = result.exceptionOrNull()?.message ?: "Registration failed"
                    )
                }
            } catch (e: Exception) {
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    error = "Registration failed: ${e.message}"
                )
            }
        }
    }
    
    private fun logout() {
        viewModelScope.launch {
            try {
                // Sign out from Firebase
                firebaseAuth.signOut()
                
                // Clear local preferences
                repo.logout()
                
                // Reset auth state
                _authState.value = AuthState()
                
                // Debug log
                android.util.Log.d("AuthViewModel", "Logout completed successfully")
            } catch (e: Exception) {
                android.util.Log.e("AuthViewModel", "Logout error: ${e.message}")
                // Even if there's an error, reset the state
                _authState.value = AuthState()
            }
        }
    }
    
    private fun clearError() {
        _authState.value = _authState.value.copy(error = null)
    }
    
    // Method to refresh user data from Firebase
    fun refreshUserData() {
        viewModelScope.launch {
            try {
                val firebaseUser = firebaseAuth.getCurrentUser()
                if (firebaseUser != null) {
                    // Get fresh user data from Firestore
                    val userDoc = firebaseAuth.getCurrentUser()?.let { user ->
                        // Try to get from Firestore first
                        try {
                            val firestore = com.google.firebase.firestore.FirebaseFirestore.getInstance()
                            val doc = firestore.collection("users").document(user.uid).get().await()
                            val firestoreUser = com.agribot.data.User.fromFirestore(doc)
                            if (firestoreUser != null) {
                                firestoreUser
                            } else {
                                // Fallback to basic user
                                User(
                                    id = user.uid,
                                    email = user.email ?: "",
                                    name = user.displayName ?: "User",
                                    role = UserRole.FARMER
                                )
                            }
                        } catch (e: Exception) {
                            // Fallback to basic user
                            User(
                                id = user.uid,
                                email = user.email ?: "",
                                name = user.displayName ?: "User",
                                role = UserRole.FARMER
                            )
                        }
                    }
                    
                    if (userDoc != null) {
                        _authState.value = _authState.value.copy(
                            currentUser = userDoc
                        )
                    }
                }
            } catch (e: Exception) {
                // Handle error silently
            }
        }
    }
    
    // Method to update user profile
    fun updateUserProfile(updatedUser: User) {
        viewModelScope.launch {
            try {
                val result = firebaseAuth.updateProfile(updatedUser)
                if (result.isSuccess) {
                    // Update local state
                    _authState.value = _authState.value.copy(
                        currentUser = updatedUser
                    )
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
    
    private fun createUserFromPrefs(email: String, roleString: String): User {
        val role = UserRole.valueOf(roleString)
        return User(
            id = UUID.randomUUID().toString(),
            email = email,
            name = "Demo User",
            role = role
        )
    }
    
    // Demo users for testing
    private fun getDemoUser(email: String): User? {
        val demoUsers = mapOf(
            "farmer@demo.com" to User(
                id = "1",
                email = "farmer@demo.com",
                name = "John Farmer",
                role = UserRole.FARMER,
                phone = "+233 20 123 4567",
                location = "Kumasi, Ghana",
                farmSize = "25",
                crops = listOf("Maize", "Cassava", "Yam")
            ),
            "customer@demo.com" to User(
                id = "2",
                email = "customer@demo.com",
                name = "Sarah Customer",
                role = UserRole.CUSTOMER,
                phone = "+233 24 987 6543",
                location = "Accra, Ghana"
            ),
            "expert@demo.com" to User(
                id = "3",
                email = "expert@demo.com",
                name = "Dr. Expert",
                role = UserRole.EXPERT,
                phone = "+233 26 555 1234",
                location = "Tamale, Ghana",
                experience = "15"
            )
        )
        return demoUsers[email]
    }
}





