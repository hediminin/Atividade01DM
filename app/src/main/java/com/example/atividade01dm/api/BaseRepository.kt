package com.example.atividade01dm.api

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import okio.IOException
import retrofit2.Response

/*
Classe responsável por executar a chamada aos endpoints da API e realizar o  tratamento de erros.
O método makeApiCall fará a chamada remota e realizará, caso necessário, o tratamento e conversão dos erros.
O método convertErrorBody converse o JSON das mensagens de erro da API para a classe ErrorResponse.
É importante que esta chamada remota e tratamento de erros esteja em uma classe separada para que não seja
necessário repetir o mesmo código em diversos locais.
 */
abstract class BaseRepository {

    suspend fun <T> makeApiCall(apiToBeCalled: suspend () -> Response<T>): ApiState<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<T> = apiToBeCalled()

                if (response.isSuccessful) {
                    ApiState.Success(
                        data = response.body()!!
                    )
                } else {
                    val errorResponse = convertErrorBody(response.errorBody())
                    ApiState.Error(
                        message = errorResponse.message
                    )
                }
            } catch (e: IOException) {
                ApiState.Error(
                    message = "Falha na conexão com o servidor"
                )
            } catch (e: Exception) {
                ApiState.Error(
                    message = "Não foi possível concluir sua solicitação"
                )
            }
        }
    }

    private fun convertErrorBody(errorBody: ResponseBody?): ErrorResponse {
        try {
            errorBody?.source()?.let {
                return Gson().fromJson(it.readUtf8(), ErrorResponse::class.java)
            } ?: run {
                return ErrorResponse(
                    message = "Não foi possível recuperar a mensagem de falha"
                )
            }
        } catch (e: Exception) {
            return ErrorResponse(
                message = "Não foi possível recuperar a mensagem de falha"
            )
        }
    }
}