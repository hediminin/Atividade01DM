package com.example.atividade01dm.api

import com.example.atividade01dm.MainApplication
import com.example.atividade01dm.datastore.AppDataStore
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
Objeto que representa a biblioteca Retrofit.
Precisamos construir o Objeto Retrofit informando a URL da API e o conversor JSON
que será utilizado, neste caso a biblioteca GSON.
 */
object RetrofitClient {
    private const val BASE_URL = "https://api-estudos.vercel.app"

    private val authInterceptor: AuthInterceptor by lazy {
        val appDataStore = AppDataStore(MainApplication.instance.applicationContext)
        AuthInterceptor(appDataStore)
    }

    private val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }
}

/*
O Retrofit precisa inplementar os endpoints mapeados na interface para que possa
realizar as chamadas a API.
 */
object ApiClient {
    val apiEndpoint: ApiEndpoint by lazy {
        RetrofitClient.retrofit.create(ApiEndpoint::class.java)
    }
}