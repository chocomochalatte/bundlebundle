package com.example.bundlebundle.order.retrofit

import android.util.Log
import com.example.bundlebundle.retrofit.ApiClient
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object AddressApiClient {
    private const val BASE_URL = "https://dapi.kakao.com/"
    private const val API_KEY = "8259e98f8a15733a3dce39bcafabbb0d"

    private val httpClient = OkHttpClient.Builder()
        .build()

    private val retrofit: Retrofit by lazy {
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    val originalRequest: Request = chain.request()
                    val newRequest: Request = if (true) {
                        originalRequest.newBuilder()
                            .header("Authorization", "KakaoAK $API_KEY")
                            .build()
                    } else {
                        originalRequest
                    }

                    return chain.proceed(newRequest)
                }
            })
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }


    val addressApiService: AddressApiService by lazy {
        retrofit.create(AddressApiService::class.java)
    }
}
