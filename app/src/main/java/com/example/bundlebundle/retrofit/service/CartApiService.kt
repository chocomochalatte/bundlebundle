package com.example.bundlebundle.retrofit.service

import com.example.bundlebundle.retrofit.dataclass.CartChangeVO
import com.example.bundlebundle.retrofit.dataclass.CartCheckVO
import com.example.bundlebundle.retrofit.dataclass.CartVO
import com.example.bundlebundle.retrofit.dataclass.GroupCartChangeVO
import com.example.bundlebundle.retrofit.dataclass.GroupCartListVO
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
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

    @PATCH("cart/{memberId}/{productId}/{productCnt}")
    fun changeCartItemCnt(
        @Path("memberId") memberId: Int,
        @Path("productId") productId: Int,
        @Path("productCnt") productCnt : Int
    ): Call<CartChangeVO>

    //그룹

    @GET("cart/group/{groupId}")
    fun groupcheckCart(
        @Path("groupId") groupId: Int
    ): Call<GroupCartListVO>

    @DELETE("cart/group/{memberId}/{productId}/{groupId}")
    fun deleteGroupCartItem(
        @Path("memberId") memberId: Int,
        @Path("productId") productId: Int,
        @Path("groupId") groupId: Int
    ): Call<CartCheckVO>

    @PATCH("cart/group/{memberId}/{productId}/{groupId}/{productCnt}")
    fun changeGroupCartItemCnt(
        @Path("memberId") memberId: Int,
        @Path("productId") productId: Int,
        @Path("groupId") groupId: Int,
        @Path("productCnt") productCnt : Int
    ): Call<GroupCartChangeVO>

}