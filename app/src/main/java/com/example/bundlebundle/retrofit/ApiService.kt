package com.example.bundlebundle.retrofit

import com.example.bundlebundle.retrofit.dataclass.cart.CartVO
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
    ): Call<CartVO>

    @POST("member/oauth/token/{id}")
    fun gettoken(
        @Path("id") token: String
    ): Call<LoginTokenVO>

    @GET("member/info")
    fun getmember(
    ): Call<MemberVO>

}