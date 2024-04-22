package com.example.atividade01dm.api.request

/*
Classe que contém os campos necessários para acessar o recurso /login da API via POST.
Os dados são convertidos em JSON pelo Retrofit antes de serem enviados.
 */
data class LoginRequestBody (
    var email: String = "",
    var senha: String = ""
)