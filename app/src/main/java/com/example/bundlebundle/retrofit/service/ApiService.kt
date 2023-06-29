package com.example.bundlebundle.retrofit.service

import com.example.bundlebundle.retrofit.dataclass.Cart
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("individual/{id}")
    fun getInfo(
        @Path("id") memberId: Int
    ): Call<Cart>
}