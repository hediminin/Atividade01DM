package com.example.atividade01dm.datastore

data class PerfilLocal(
    var autenticado: Boolean = false,
    var nome: String = "",
    var email: String = "",
    var foto: String? = null
)