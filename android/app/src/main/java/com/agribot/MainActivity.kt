package com.agribot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.agribot.ui.AgribotApp
import com.agribot.ui.auth.AuthViewModel
import com.agribot.ui.auth.LoginScreen
import com.agribot.ui.theme.AgribotTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AgribotTheme {
                val authViewModel: AuthViewModel = viewModel()
                val authState by authViewModel.authState.collectAsState()
                
                if (authState.isAuthenticated) {
                    // User is logged in, show main app with user data
                    AgribotApp(
                        currentUser = authState.currentUser,
                        authViewModel = authViewModel
                    )
                } else {
                    // User is not logged in, show login screen
                    LoginScreen(
                        onLoggedIn = {
                            // Navigation handled by authState change
                        },
                        onSwitchToRegister = {
                            // Handle registration if needed
                        }
                    )
                }
            }
        }
    }
}


