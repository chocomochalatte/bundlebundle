package com.example.bundlebundle.retrofit.service

import com.example.bundlebundle.retrofit.dataclass.group.GroupIdVO
import com.example.bundlebundle.retrofit.dataclass.group.GroupNicknameVO
import com.example.bundlebundle.retrofit.dataclass.group.GroupVO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface GroupApiService {

    @GET("member/group-check")
    fun checkIfGroupIsPresent(
        @Header("Authorization") accessToken: String
    ): Call<GroupIdVO>

    @POST("group")
    fun createGroup(
        @Body groupNicknameVO: GroupNicknameVO
    ): Call<GroupVO>

}