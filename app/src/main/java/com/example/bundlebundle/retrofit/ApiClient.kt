package com.example.bundlebundle.retrofit

import android.util.Log
import com.example.bundlebundle.retrofit.service.CartApiService
import com.example.bundlebundle.retrofit.service.GroupApiService
import com.example.bundlebundle.retrofit.service.OrderApiService
import com.example.bundlebundle.retrofit.service.ProductApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object ApiClient {
    private const val BASE_URL = "http://ryulrudaga.com:23000/api/"
//   private const val BASE_URL = "http://10.0.2.2:8080/bundlebundle/api/"
    private var jwtToken: String? = null

    private val retrofit: Retrofit by lazy {
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    val originalRequest: Request = chain.request()

                    // JWT 토큰이 있는 경우 헤더에 추가
                    val token = getJwtToken()
                    val newRequest: Request = if (token != null) {
                        originalRequest.newBuilder()
                            .header("Authorization", "$token")
                            .build()
                    } else {
                        originalRequest
                    }

                    Log.i("TestActivity", "인터셉터를 통해 토큰 담김")
                    Log.i("TestActivity", token.toString())
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

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    val cartApiService: CartApiService by lazy {
        retrofit.create(CartApiService::class.java)
    }

    val productApiService: ProductApiService by lazy {
        retrofit.create(ProductApiService::class.java)
    }

    val groupApiService: GroupApiService by lazy {
        retrofit.create(GroupApiService::class.java)
    }

    val orderApiService: OrderApiService by lazy {
        retrofit.create(OrderApiService::class.java)
    }

    fun setJwtToken(token: String?) {
        jwtToken = token
    }

    fun getJwtToken(): String? {
        return jwtToken
    }
}
