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
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

/*
Interface que representa o mapeamento dos endpoints da API para métodos correspondentes.
Aqui informamos quais dados serão enviados e recebidos em cada endpoint.

Com esse mapeamento o Retrofit irá:
 - implementar estes métodos para que possa realizar as chamadas a API;
 - converter os dados de envio e resposta em JSON.
 */
interface ApiEndpoint {
    @POST("/login")
    suspend fun login(@Body requestBody: LoginRequestBody) : Response<LoginResponseBody>

    @GET("/usuarios")
    suspend fun getUsuarios() : Response<UsuariosResponseBody>

    @GET("/usuarios/{id}")
    suspend fun getUsuario(@Path("id") id: String) : Response<UsuarioResponseBody>

    @PUT("/usuarios/{id}")
    suspend fun usuarioEdita(
        @Path("id") id: String,
        @Body requestBody: UsuarioEditaRequestBody
    ) : Response<UsuarioEditaResponseBody>

    @GET("/dashboard")
    suspend fun getDashboard() : Response<DashboardResponseBody>

    @Multipart
    @POST("/imagens")
    suspend fun uploadFotoPerfil(
        @Part image: MultipartBody.Part
    ): Response<UploadFotoPerfilResponseBody>
}