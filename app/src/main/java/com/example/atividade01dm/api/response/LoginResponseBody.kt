package com.example.atividade01dm.api.response

import com.example.atividade01dm.api.model.Usuario

data class LoginResponseBody (
    var token: String = "",
    var usuario: Usuario
)