package com.example.bundlebundle.order.retrofit

import com.example.bundlebundle.order.retrofit.dataclass.KakaoAddressResponse
import com.example.bundlebundle.retrofit.dataclass.cart.CartVO
import com.example.bundlebundle.retrofit.dataclass.member.LoginTokenVO
import com.example.bundlebundle.retrofit.dataclass.member.MemberVO

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Path

interface AddressApiService {
    @GET("v2/local/search/address.json")
    fun searchAddress(
        @Query("query") query: String,
    ): Call<KakaoAddressResponse>


}