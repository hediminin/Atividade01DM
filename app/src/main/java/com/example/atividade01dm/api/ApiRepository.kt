package com.example.atividade01dm.api

import com.example.atividade01dm.api.request.LoginRequestBody
import com.example.atividade01dm.api.response.LoginResponseBody
import com.example.atividade01dm.api.response.UsuariosResponseBody

/*
Classe que contém os métodos de chamada à API.
Os métodos serão executados dentro do ViewModel.
Cada método retorna o estado da api representado pelo objeto ApiState.
 */
class ApiRepository: BaseRepository() {
    suspend fun login(requestBody: LoginRequestBody) : ApiState<LoginResponseBody> {
        return makeApiCall { ApiClient.apiEndpoint.login(requestBody) }
    }

    suspend fun getUsuarios() : ApiState<UsuariosResponseBody> {
        return makeApiCall { ApiClient.apiEndpoint.usuarios() }
    }
}