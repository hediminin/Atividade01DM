package com.example.atividade01dm.viewmodel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.atividade01dm.api.ApiState
import com.example.atividade01dm.api.request.UsuarioEditaRequestBody
import com.example.atividade01dm.api.response.UsuarioEditaResponseBody
import com.example.atividade01dm.api.response.UsuarioResponseBody
import com.example.atividade01dm.api.response.UsuariosResponseBody
import kotlinx.coroutines.launch

class UsuarioViewModel(
    application: Application
) : MainViewModel(application) {
    private val _usuariosResponseBody = mutableStateOf<ApiState<UsuariosResponseBody>>(ApiState.Created())
    val usuariosResponseBody: State<ApiState<UsuariosResponseBody>> = _usuariosResponseBody

    private val _usuarioResponseBody = mutableStateOf<ApiState<UsuarioResponseBody>>(ApiState.Created())
    val usuarioResponseBody: State<ApiState<UsuarioResponseBody>> = _usuarioResponseBody

    private val _usuarioEditaResponseBody = mutableStateOf<ApiState<UsuarioEditaResponseBody>>(ApiState.Created())
    val usuariosEditaResponseBody: State<ApiState<UsuarioEditaResponseBody>> = _usuarioEditaResponseBody

    fun getUsuarios() {
        viewModelScope.launch {
            _usuariosResponseBody.value = ApiState.Loading()
            _usuariosResponseBody.value = apiRepository.getUsuarios()
        }
    }

    fun getUsuarioById(id: String) {
        viewModelScope.launch {
            _usuarioResponseBody.value = ApiState.Loading()
            _usuarioResponseBody.value = apiRepository.getUsuario(id)
        }
    }

    fun usuarioEdita(id: String, usuarioEditaRequestBody: UsuarioEditaRequestBody) {
        viewModelScope.launch {
            _usuarioEditaResponseBody.value = ApiState.Loading()
            _usuarioEditaResponseBody.value = apiRepository.usuarioEdita(id, usuarioEditaRequestBody)
        }
    }

    fun clearApiState() {
        _usuarioResponseBody.value = ApiState.Created()
        _usuarioEditaResponseBody.value = ApiState.Created()
    }
}