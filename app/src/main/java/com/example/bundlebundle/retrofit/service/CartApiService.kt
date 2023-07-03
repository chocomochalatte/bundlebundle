package com.example.bundlebundle.retrofit.service

import com.example.bundlebundle.retrofit.dataclass.CartCheckVO
import com.example.bundlebundle.retrofit.dataclass.CartVO
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path


interface CartApiService {

    @GET("cart/individual/{memberId}")
    fun checkCart(
        @Path("memberId") memberId: Int
    ): Call<CartVO>

    @DELETE("cart/{memberId}/{productId}")
    fun deleteCartItem(
        @Path("memberId") memberId: Int,
        @Path("productId") productId: Int
    ): Call<CartCheckVO>

}