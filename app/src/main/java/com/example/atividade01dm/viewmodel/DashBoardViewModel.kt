package com.example.atividade01dm.viewmodel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.atividade01dm.api.ApiState
import com.example.atividade01dm.api.response.DashboardResponseBody
import kotlinx.coroutines.launch

class DashBoardViewModel(
    application: Application
) : MainViewModel(application) {
    private val _dashboardResponseBody = mutableStateOf<ApiState<DashboardResponseBody>>(ApiState.Created())
    val dashboardResponseBody: State<ApiState<DashboardResponseBody>> = _dashboardResponseBody

    fun getDashboard() {
        viewModelScope.launch {
            _dashboardResponseBody.value = ApiState.Loading()
            _dashboardResponseBody.value = apiRepository.getDashboard()
        }
    }
}