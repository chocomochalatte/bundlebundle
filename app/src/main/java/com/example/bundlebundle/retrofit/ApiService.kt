package com.example.bundlebundle.retrofit

import com.example.bundlebundle.retrofit.dataclass.Cart
import com.example.bundlebundle.retrofit.dataclass.ProductVO
import com.example.bundlebundle.retrofit.dataclass.member.LoginTokenVO
import com.example.bundlebundle.retrofit.dataclass.member.MemberVO

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Path

interface ApiService {

    @POST("individual/{id}")
    fun getInfo(
        @Query("id") memberId: Int
    ): Call<Cart>

    @GET("products")
    fun showProducts(
        @Query("sort") sortType: String
    ): Call<List<ProductVO>>

    @GET("products/{productId}")
    fun showProductDetail(
        @Path("productId") productId: Int
    ): Call<ProductVO>

    @POST("member/oauth/token/{id}")
    fun gettoken(
        @Path("id") token: String
    ): Call<LoginTokenVO>

    @GET("member/info")
    fun getmember(
    ): Call<MemberVO>


}