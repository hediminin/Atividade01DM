package com.example.atividade01dm.api.response

import com.example.atividade01dm.api.model.Usuario

/*
Classe que contém os campos de reposta do recurso /usuarios da API.
Os dados são convertidos em JSON pelo Retrofit quando recebidos.
 */
data class UsuariosResponseBody (
    var docs: List<Usuario>
)