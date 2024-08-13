package com.example.atividade01dm.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object AppDataStoreKeys {
    val AUTENTICADO = booleanPreferencesKey("autenticado")
    val TOKEN = stringPreferencesKey("token")
    val NOME = stringPreferencesKey("nome")
    val EMAIL = stringPreferencesKey("email")
    val FOTO = stringPreferencesKey("foto")
}