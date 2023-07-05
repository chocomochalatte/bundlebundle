package com.example.bundlebundle.retrofit.service

import com.example.bundlebundle.group.GroupMemberCartVO
import com.example.bundlebundle.retrofit.dataclass.cart.CartChangeVO
import com.example.bundlebundle.retrofit.dataclass.cart.CartCheckVO
import com.example.bundlebundle.retrofit.dataclass.cart.CartVO
import com.example.bundlebundle.retrofit.dataclass.cart.GroupCartChangeVO
import com.example.bundlebundle.retrofit.dataclass.cart.GroupCartListVO
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path


interface CartApiService {

    @GET("cart/individual")
    fun checkCart(): Call<CartVO>

    @DELETE("cart/{productId}")
    fun deleteCartItem(
        @Path("productId") productId: Int
    ): Call<CartCheckVO>

    @PATCH("cart/{productId}/{productCnt}")
    fun changeCartItemCnt(
        @Path("productId") productId: Int,
        @Path("productCnt") productCnt : Int
    ): Call<CartChangeVO>

    //그룹
    @GET("cart/group")
    fun groupcheckCart(): Call<GroupCartListVO>

    @DELETE("cart/group/{productId}")
    fun deleteGroupCartItem(
        @Path("productId") productId: Int,
    ): Call<CartCheckVO>

    @PATCH("cart/group/{productId}/{productCnt}")
    fun changeGroupCartItemCnt(
        @Path("productId") productId: Int,
        @Path("productCnt") productCnt : Int
    ): Call<GroupCartChangeVO>

    @POST("cart/group")
    fun addToGroupCart(
    ): Call<GroupMemberCartVO>

    @POST("cart")
    fun addToPersonalCart(
    ): Call<GroupMemberCartVO>

}