package com.example.bundlebundle.retrofit

import com.example.bundlebundle.retrofit.dataclass.Cart
import com.example.bundlebundle.retrofit.dataclass.ProductVO
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
    suspend fun showProducts(
        @Query("sort") sortType: String
    ): List<ProductVO>

    @GET("products/{productId}")
    suspend fun showProductDetail(
        @Path("productId") productId: Int
    ): ProductVO



}