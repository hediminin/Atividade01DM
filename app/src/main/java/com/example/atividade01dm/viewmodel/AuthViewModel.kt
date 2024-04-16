package com.example.atividade01dm.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atividade01dm.api.ApiClient
import com.example.atividade01dm.api.ErrorResponse
import com.example.atividade01dm.api.request.LoginRequestBody
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.io.IOException

class AuthViewModel: ViewModel() {
    val error = mutableStateOf("")

    fun login() {
        val requestBody = LoginRequestBody()
        requestBody.email = "alx.delira@gmail.com1"
        requestBody.senha = "12345678"

        viewModelScope.launch {
            try {
                val response = ApiClient.apiEndpoint.login(requestBody)

                if (response.isSuccessful) {
                    response.body()?.let { responseBody ->
                        println(responseBody.token)
                        println(responseBody.usuario.nome)
                    }
                } else {
                    response.errorBody()?.let { responseBody ->
                        val errorResponse = Gson().fromJson(responseBody.source().readUtf8(), ErrorResponse::class.java)
                        error.value = errorResponse.message
                        //println(error.message)
                    }
                }
            } catch (e: IOException) {
                println("Falha na conexão com o servidor")
            } catch (e: Exception) {
                println("Não foi possível concluir sua solicitação")
            }
        }
    }
}