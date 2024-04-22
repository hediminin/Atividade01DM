package com.example.atividade01dm.api.model

/*
Classe que representa o objeto Usuário definido na API.
 */
data class Usuario (
    var _id: String = "",
    var nome: String = "",
    var email: String = "",
    var foto: String? = null
)