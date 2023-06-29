package com.example.bundlebundle.retrofit.service

import com.example.bundlebundle.retrofit.dataclass.Baemin
import retrofit2.Call

import retrofit2.http.GET

import retrofit2.http.Query

interface ExampleRetrofitService {
    @GET("contents?typeCode=notice&size=10")
    fun loadNotice(@Query("page") page: String): Call<Baemin>
}