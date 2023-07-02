package com.example.bundlebundle.retrofit.service

import com.example.bundlebundle.retrofit.dataclass.CartVO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface CartApiService {

    @GET("individual/{memberId}")
    fun checkCart(
        @Path("memberId") memberId: Int
    ): Call<CartVO>


}