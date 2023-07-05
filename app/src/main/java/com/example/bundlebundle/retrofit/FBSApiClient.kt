package com.example.bundlebundle.retrofit

import com.example.bundlebundle.retrofit.service.FBSApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FBSApiClient {
    private const val BASE_URL = "https://fcm.googleapis.com/fcm/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(FBSApiClient.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val fbsapiService: FBSApiService by lazy {
        retrofit.create(FBSApiService::class.java)
    }

}

