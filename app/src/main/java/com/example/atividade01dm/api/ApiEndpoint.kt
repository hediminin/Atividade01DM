package com.example.atividade01dm.api

import com.example.atividade01dm.api.request.LoginRequestBody
import com.example.atividade01dm.api.response.LoginResponseBody
import com.example.atividade01dm.api.response.UsuariosResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

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
    suspend fun usuarios() : Response<UsuariosResponseBody>
}