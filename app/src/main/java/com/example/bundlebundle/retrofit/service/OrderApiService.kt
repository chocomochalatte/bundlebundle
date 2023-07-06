package com.example.bundlebundle.retrofit.service

import com.example.bundlebundle.retrofit.dataclass.group.GroupIdVO
import com.example.bundlebundle.retrofit.dataclass.group.GroupNicknameVO
import com.example.bundlebundle.retrofit.dataclass.group.GroupVO
import com.example.bundlebundle.retrofit.dataclass.order.GroupOrderVO
import com.example.bundlebundle.retrofit.dataclass.order.ProductOrderVO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderApiService {

    @POST("order/groupcart")
    fun makeWholeGroupCartOrder(): Call<GroupOrderVO>

    @GET("order/{orderId}")
    fun showOrderProducts(
        @Path("orderId") orderId: Int
    ): Call<List<ProductOrderVO>>

}