package com.example.bundlebundle.retrofit.service

import com.example.bundlebundle.retrofit.dataclass.Cart
import com.example.bundlebundle.retrofit.dataclass.member.LoginTokenVO
import com.kakao.sdk.auth.model.OAuthToken
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("individual/{id}")
    fun getInfo(
        @Path("id") memberId: Int
    ): Call<Cart>

    @POST("oauth/token/{id}")
    fun gettoken(
        @Path("id") token: String
    ): Call<LoginTokenVO>
}