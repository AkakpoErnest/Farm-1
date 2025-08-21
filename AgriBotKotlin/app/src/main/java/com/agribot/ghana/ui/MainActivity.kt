package com.agribot.ghana.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.agribot.ghana.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import com.agribot.ghana.ui.dashboard.DashboardFragment
import com.agribot.ghana.ui.chat.ChatFragment
import com.agribot.ghana.ui.weather.WeatherFragment
import com.agribot.ghana.ui.market.MarketFragment
import com.agribot.ghana.ui.tools.ToolsFragment
import com.agribot.ghana.ui.profile.ProfileFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> switchTo(DashboardFragment())
                R.id.nav_chat -> switchTo(ChatFragment())
                R.id.nav_weather -> switchTo(WeatherFragment())
                R.id.nav_market -> switchTo(MarketFragment())
                R.id.nav_tools -> switchTo(ToolsFragment())
                R.id.nav_profile -> switchTo(ProfileFragment())
            }
            true
        }
        if (savedInstanceState == null) bottomNav.selectedItemId = R.id.nav_dashboard
    }

    private fun switchTo(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
