package com.example.atividade01dm.api

/*
Classe que representa o estado da API.
O estado muda no decorrer das chamadas remotas e é repassado pelo ViewModel
à interface do usuário para que ela possa ser atualizada de acordo com as
mudanças.
 */
sealed class ApiState<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Created<T> : ApiState<T>()
    class Loading<T> : ApiState<T>()
    class Success<T>(data: T) : ApiState<T>(
        data = data
    )
    class Error<T>(message: String) : ApiState<T>(
        message = message
    )
}