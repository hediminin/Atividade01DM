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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DashBoardViewModel(
    private val application: Application
) : AndroidViewModel(application) {
    private val apiRepository = ApiRepository()
    private val appDataStore = AppDataStore(application.applicationContext)

    private val _dashboardResponseBody = mutableStateOf<ApiState<DashboardResponseBody>>(ApiState.Created())
    val dashboardResponseBody: State<ApiState<DashboardResponseBody>> = _dashboardResponseBody

    var nome by mutableStateOf("")
    var email by mutableStateOf("")
    var foto by mutableStateOf("")

    init {
        carregaPerfilLocal()
    }

    fun getDashboard() {
        viewModelScope.launch {
            _dashboardResponseBody.value = ApiState.Loading()
            _dashboardResponseBody.value = apiRepository.getDashboard()
        }
    }


    fun carregaPerfilLocal() {
        viewModelScope.launch {
            nome = appDataStore.getString(AppDataStoreKeys.NOME).first()
            email = appDataStore.getString(AppDataStoreKeys.EMAIL).first()
            foto = appDataStore.getString(AppDataStoreKeys.FOTO).first()
        }
    }
}