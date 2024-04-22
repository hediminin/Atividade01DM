package com.example.atividade01dm.viewmodel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.atividade01dm.api.ApiRepository
import com.example.atividade01dm.api.ApiState
import com.example.atividade01dm.api.request.LoginRequestBody
import com.example.atividade01dm.api.response.LoginResponseBody
import kotlinx.coroutines.launch

/*
Classe responsável por representar o modelo de visualização.
Ela contém os dados necessários para construir a interface do usuário.
Lembre-se que o ViewModel sobrevive as alterações de configuração, como a rotação da tela.
Desta forma o estado da interface não é perdido em caso de alterações de configuração.
É no ViewModel que vamos acessar os dados da API e repassá-los para a interface.
 */
class AuthViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val apiRepository = ApiRepository()
    private val _loginResponseBody = mutableStateOf<ApiState<LoginResponseBody>>(ApiState.Created())
    val loginResponseBody: State<ApiState<LoginResponseBody>> = _loginResponseBody

    fun login(
        email: String,
        senha: String
    ) {
        if (email.isBlank()) {
            _loginResponseBody.value = ApiState.Error("Informe o usuário")
            return
        }

        if (senha.isBlank()) {
            _loginResponseBody.value = ApiState.Error("Informe a senha")
            return
        }

        val requestBody = LoginRequestBody()
        requestBody.email = email
        requestBody.senha = senha

        _loginResponseBody.value = ApiState.Loading()

        viewModelScope.launch {
            _loginResponseBody.value = apiRepository.login(requestBody)
            if (_loginResponseBody.value is ApiState.Success) {
                //Salva dados do usuário
            }
        }
    }
}