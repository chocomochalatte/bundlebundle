package com.example.bundlebundle.retrofit

import com.example.bundlebundle.LoginActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitObject {
    private const val BASE_URL = "http://10.0.2.2:8080/bundlebundle/api/"

    @Provides
    @Singleton
    fun okHttpClient(interceptor: AppInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor) // okHttp에 인터셉터 추가
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()
    }

    @Provides
    @Singleton
    fun retrofit(): ApiClient {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient(AppInterceptor())) // okHttpClient를 Retrofit 빌더에 추가
            .build()
            .create(ApiClient::class.java)
    }

    class AppInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain) : Response = with(chain) {
            val accessToken = LoginActivity.prefs.getString("accessToken", "") // ViewModel에서 지정한 key로 JWT 토큰을 가져온다.
            val newRequest = request().newBuilder()
                .addHeader("authorization", accessToken) // 헤더에 authorization라는 key로 JWT 를 넣어준다.
                .build()
            proceed(newRequest)
        }
    }
}