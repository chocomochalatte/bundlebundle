package com.example.bundlebundle.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object ApiClient {
    private const val BASE_URL = "http://10.0.2.2:8080/bundlebundle/api/"
    private var jwtToken: String? = null;

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    fun setJwtToken(token: String?) {
        jwtToken = token
    }

    fun getJwtToken(): String? {
        return jwtToken
    }

}
