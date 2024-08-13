package com.example.atividade01dm.api.request

data class UsuarioEditaRequestBody (
    var email: String = "",
    var nome: String = "",
    var senha: String? = null,
    var foto: String? = null
)