package com.example.atividade01dm.api

import com.example.atividade01dm.api.request.LoginRequestBody
import com.example.atividade01dm.api.response.LoginResponseBody
import com.example.atividade01dm.api.response.UsuariosResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiEndpoint {
    @POST("/login")
    suspend fun login(@Body requestBody: LoginRequestBody) : Response<LoginResponseBody>

    @GET("/usuarios")
    suspend fun usuarios() : Response<UsuariosResponseBody>
}