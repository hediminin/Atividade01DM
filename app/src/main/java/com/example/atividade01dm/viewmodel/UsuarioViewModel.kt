package com.example.atividade01dm.viewmodel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.atividade01dm.api.ApiRepository
import com.example.atividade01dm.api.ApiState
import com.example.atividade01dm.api.response.UsuariosResponseBody
import kotlinx.coroutines.launch

class UsuarioViewModel(
    private val application: Application
) : AndroidViewModel(application) {
    private val apiRepository = ApiRepository()
    private val _usuariosResponseBody = mutableStateOf<ApiState<UsuariosResponseBody>>(ApiState.Created())
    val usuariosResponseBody: State<ApiState<UsuariosResponseBody>> = _usuariosResponseBody

    fun getUsuarios() {
        viewModelScope.launch {
            _usuariosResponseBody.value = ApiState.Loading()
            _usuariosResponseBody.value = apiRepository.getUsuarios()
        }
    }
}