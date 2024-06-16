package com.example.datastoreabg

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("settings")

object SettingsDataStore {
    private val NAME_KEY = stringPreferencesKey(name = "name")
    private val EMAIL_KEY = stringPreferencesKey(name = "email")

    fun getNameFlow(context: Context): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[NAME_KEY] ?: ""
        }
    }

    suspend fun saveName(context: Context, value: String) {
        context.dataStore.edit { preferences ->
            preferences[NAME_KEY] = value
        }
    }

    fun getEmailFlow(context: Context): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[EMAIL_KEY] ?: ""
        }
    }

    suspend fun saveEmail(context: Context, value: String) {
        context.dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = value
        }
    }
}
