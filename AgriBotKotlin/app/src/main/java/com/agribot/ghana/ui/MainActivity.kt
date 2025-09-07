package com.agribot.ghana.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.agribot.ghana.R
import com.agribot.ghana.ui.chat.ChatFragment
import com.agribot.ghana.ui.dashboard.DashboardFragment
import com.agribot.ghana.ui.market.MarketFragment
import com.agribot.ghana.ui.profile.ProfileFragment
import com.agribot.ghana.ui.tools.ToolsFragment
import com.agribot.ghana.ui.weather.WeatherFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        
        // Set up bottom navigation
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> switchToFragment(DashboardFragment())
                R.id.nav_chat -> switchToFragment(ChatFragment())
                R.id.nav_weather -> switchToFragment(WeatherFragment())
                R.id.nav_market -> switchToFragment(MarketFragment())
                R.id.nav_tools -> switchToFragment(ToolsFragment())
                R.id.nav_profile -> switchToFragment(ProfileFragment())
            }
            true
        }

        // Set default fragment
        if (savedInstanceState == null) {
            bottomNav.selectedItemId = R.id.nav_dashboard
        }
    }

    private fun switchToFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}

