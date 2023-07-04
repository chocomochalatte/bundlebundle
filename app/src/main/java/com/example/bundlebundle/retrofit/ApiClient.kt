package com.example.bundlebundle.retrofit

import com.example.bundlebundle.retrofit.service.CartApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiClient {
    private const val BASE_URL = "http://10.0.2.2:8080/bundlebundle/api/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
    val cartapiService: CartApiService by lazy {
        retrofit.create(CartApiService::class.java)
    }
}
