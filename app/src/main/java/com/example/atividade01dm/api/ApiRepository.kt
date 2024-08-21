package com.example.atividade01dm.api

import com.example.atividade01dm.api.request.LoginRequestBody
import com.example.atividade01dm.api.request.UsuarioEditaRequestBody
import com.example.atividade01dm.api.response.DashboardResponseBody
import com.example.atividade01dm.api.response.LoginResponseBody
import com.example.atividade01dm.api.response.UploadFotoPerfilResponseBody
import com.example.atividade01dm.api.response.UsuarioEditaResponseBody
import com.example.atividade01dm.api.response.UsuarioResponseBody
import com.example.atividade01dm.api.response.UsuariosResponseBody
import okhttp3.MultipartBody

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
        return makeApiCall { ApiClient.apiEndpoint.getUsuarios() }
    }

    suspend fun getUsuario(id: String) : ApiState<UsuarioResponseBody> {
        return makeApiCall { ApiClient.apiEndpoint.getUsuario(id) }
    }

    suspend fun usuarioEdita(id: String, requestBody: UsuarioEditaRequestBody) : ApiState<UsuarioEditaResponseBody> {
        return makeApiCall { ApiClient.apiEndpoint.usuarioEdita(id, requestBody) }
    }

    suspend fun getDashboard() : ApiState<DashboardResponseBody> {
        return makeApiCall { ApiClient.apiEndpoint.getDashboard() }
    }

    suspend fun uploadFotoPerfil(
        image: MultipartBody.Part
        ) : ApiState<UploadFotoPerfilResponseBody> {
            return makeApiCall { ApiClient.apiEndpoint.uploadFotoPerfil(image)
        }
    }

    suspend fun uploadFotoPerfil(
        id: String,
        image: MultipartBody.Part
    ) : ApiState<UploadFotoPerfilResponseBody> {
        return makeApiCall { ApiClient.apiEndpoint.uploadFotoPerfil(id, image)
        }
    }
}