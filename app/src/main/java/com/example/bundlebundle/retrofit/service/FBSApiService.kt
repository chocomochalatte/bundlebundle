package com.example.bundlebundle.retrofit.service

import com.example.bundlebundle.retrofit.dataclass.firebase.FcmMessageVO
import com.example.bundlebundle.retrofit.dataclass.firebase.FcmResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface FBSApiService {

    @Headers("Content-Type: application/json", "Authorization: key=AAAAwoPcVpw:APA91bGUhpd_d0FyOv4PBz9VW842m-Xh2WYI2PxUiT77jv9TOMkOX0SmNDKH3_ivqRoAw-aaly838ThRN2NZvrssV3nWEIGlLInC3Uvr8GQjXUajIou71ZSyDZwZnL2gvprmSsZCz0YT")
    @POST("send")
    fun alarm(@Body fcmRequest: FcmMessageVO): Call<FcmResponse>
}
