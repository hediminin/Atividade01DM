package com.example.atividade01dm.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

const val SETTINGS = "settings"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(SETTINGS)

class AppDataStore(
    private val context: Context
) {
    private val dataStore: DataStore<Preferences> = PreferenceDataStoreFactory.create(
        produceFile = { context.preferencesDataStoreFile(SETTINGS) }
    )

    suspend fun putBoolean(key: Preferences.Key<Boolean>, value: Boolean) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    fun getBoolean(key: Preferences.Key<Boolean>): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[key] ?: false
        }
    }

    suspend fun putString(key: Preferences.Key<String>, value: String) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    fun getString(key: Preferences.Key<String>): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[key] ?: ""
        }
    }
}