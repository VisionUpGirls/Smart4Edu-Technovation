package com.example.technovation

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.settingsDataStore by preferencesDataStore(name = "settings_prefs")

class SettingsPrefs(private val context: Context) {

    private val KEY_LANGUAGE = stringPreferencesKey("language") // "ro" or "en"

    val languageFlow: Flow<String> =
        context.settingsDataStore.data.map { prefs ->
            prefs[KEY_LANGUAGE] ?: "ro"
        }

    suspend fun setLanguage(value: String) {
        context.settingsDataStore.edit { prefs ->
            prefs[KEY_LANGUAGE] = value
        }
    }
}
