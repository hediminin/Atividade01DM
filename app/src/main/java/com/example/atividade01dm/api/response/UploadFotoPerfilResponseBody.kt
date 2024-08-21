package com.example.atividade01dm.api.response

/*
Classe que contém os campos de reposta do recurso /login da API.
Os dados são convertidos em JSON pelo Retrofit quando recebidos.
 */
data class UploadFotoPerfilResponseBody (
    var codigo: Int = 0,
    var mensagem: String = "",
    var dados: DadosUpload
)

data class DadosUpload (
    var tipo_arquivo: String = "",
    var enviado_por: String = "",
    var caminho: String = "",
    var id_imagem: String = "",
    var criado_em: String = "",
    var atualizado_em: String = "",
    var _id: String = ""
)