package com.example.technovation

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.themeDataStore by preferencesDataStore(name = "theme_prefs")

class ThemePrefs(private val context: Context) {

    private val KEY_DARK = booleanPreferencesKey("dark_theme")

    val isDarkThemeFlow: Flow<Boolean> =
        context.themeDataStore.data.map { prefs ->
            prefs[KEY_DARK] ?: false
        }

    suspend fun setDarkTheme(value: Boolean) {
        context.themeDataStore.edit { prefs ->
            prefs[KEY_DARK] = value
        }
    }
}
