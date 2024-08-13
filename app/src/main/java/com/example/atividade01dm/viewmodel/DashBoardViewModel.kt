package com.example.atividade01dm.viewmodel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.atividade01dm.api.ApiRepository
import com.example.atividade01dm.api.ApiState
import com.example.atividade01dm.api.response.DashboardResponseBody
import com.example.atividade01dm.datastore.AppDataStore
import com.example.atividade01dm.datastore.AppDataStoreKeys
import com.example.atividade01dm.datastore.PerfilLocal
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DashBoardViewModel(
    private val application: Application
) : AndroidViewModel(application) {
    private val apiRepository = ApiRepository()
    private val appDataStore = AppDataStore(application.applicationContext)

    private val _dashboardResponseBody = mutableStateOf<ApiState<DashboardResponseBody>>(ApiState.Created())
    val dashboardResponseBody: State<ApiState<DashboardResponseBody>> = _dashboardResponseBody

    var perfilLocal by mutableStateOf(PerfilLocal())

    init {
        loadLocalProfile()
    }

    fun getDashboard() {
        viewModelScope.launch {
            _dashboardResponseBody.value = ApiState.Loading()
            _dashboardResponseBody.value = apiRepository.getDashboard()
        }
    }


    private fun loadLocalProfile() {
        viewModelScope.launch {
            perfilLocal.nome = appDataStore.getString(AppDataStoreKeys.NOME).first()
            perfilLocal.email = appDataStore.getString(AppDataStoreKeys.EMAIL).first()
            perfilLocal.foto = appDataStore.getString(AppDataStoreKeys.FOTO).first()
        }
    }
}