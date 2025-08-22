package com.agribot.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DATASTORE_NAME = "agribot_prefs"

val Context.dataStore by preferencesDataStore(name = DATASTORE_NAME)

object PrefKeys {
    val isLoggedIn: Preferences.Key<Boolean> = booleanPreferencesKey("is_logged_in")
    val email: Preferences.Key<String> = stringPreferencesKey("email")
    val role: Preferences.Key<String> = stringPreferencesKey("role")
    val language: Preferences.Key<String> = stringPreferencesKey("language") // en, tw, ee, ga
}

data class UserPrefs(
    val isLoggedIn: Boolean = false,
    val email: String? = null,
    val role: String? = null,
    val language: String = "en"
)

class UserPreferencesRepository(private val context: Context) {
    val prefsFlow: Flow<UserPrefs> = context.dataStore.data.map { p ->
        UserPrefs(
            isLoggedIn = p[PrefKeys.isLoggedIn] ?: false,
            email = p[PrefKeys.email],
            role = p[PrefKeys.role],
            language = p[PrefKeys.language] ?: "en"
        )
    }

    suspend fun setLoggedIn(email: String, role: String) {
        context.dataStore.edit { p ->
            p[PrefKeys.isLoggedIn] = true
            p[PrefKeys.email] = email
            p[PrefKeys.role] = role
        }
    }

    suspend fun setLanguage(code: String) {
        context.dataStore.edit { p ->
            p[PrefKeys.language] = code
        }
    }

    suspend fun logout() {
        context.dataStore.edit { p ->
            p[PrefKeys.isLoggedIn] = false
            p.remove(PrefKeys.email)
            p.remove(PrefKeys.role)
        }
    }
}





