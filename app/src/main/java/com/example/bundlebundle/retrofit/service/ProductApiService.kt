package com.example.bundlebundle.retrofit.service

import com.example.bundlebundle.retrofit.dataclass.ProductVO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ProductApiService {
    @GET("products")
    fun showProducts(
        @Query("sort") sortType: String
    ): Call<List<ProductVO>>

    @GET("products/{productId}")
    fun showProductDetail(
        @Path("productId") productId: Int
    ): Call<ProductVO>
}