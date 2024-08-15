package com.example.atividade01dm.viewmodel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.atividade01dm.api.ApiRepository
import com.example.atividade01dm.datastore.AppDataStore
import com.example.atividade01dm.datastore.AppDataStoreKeys
import com.example.atividade01dm.datastore.PerfilLocal
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

open class MainViewModel(
    application: Application
) : AndroidViewModel(application) {
    protected val apiRepository = ApiRepository()
    protected val appDataStore = AppDataStore(application.applicationContext)

    protected var _perfilLocal = mutableStateOf(PerfilLocal())
    var perfilLocal: State<PerfilLocal> = _perfilLocal

    init {
        loadLocalProfile()
    }

    private fun loadLocalProfile() {
        viewModelScope.launch {
            runBlocking {
                _perfilLocal.value.autenticado = appDataStore.getBoolean(AppDataStoreKeys.AUTENTICADO).first()
                _perfilLocal.value.nome = appDataStore.getString(AppDataStoreKeys.NOME).first()
                _perfilLocal.value.email = appDataStore.getString(AppDataStoreKeys.EMAIL).first()
                _perfilLocal.value.foto = appDataStore.getString(AppDataStoreKeys.FOTO).first()
            }
        }
    }
}