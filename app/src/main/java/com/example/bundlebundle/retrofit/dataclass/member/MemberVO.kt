package com.example.bundlebundle.retrofit.dataclass.member

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp


data class MemberVO(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("username")
    val username: String?,
    val password: String?,
    @SerializedName("account_email")
    val email: String?,
    val address: String?,
    val phoneNumber: String?,
    @SerializedName("created_at")
    val createdAt: Timestamp?,
    val groupNickname: String?,
    val groupId: Int?,
    val userProfileImg: String?
)