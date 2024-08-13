package com.example.atividade01dm.api.response

data class UsuarioResponseBody (
    var _id: String = "",
    var nome: String = "",
    var email: String = "",
    var foto: String? = null
)