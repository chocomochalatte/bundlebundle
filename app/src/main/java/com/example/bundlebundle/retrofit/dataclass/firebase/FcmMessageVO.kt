package com.example.bundlebundle.retrofit.dataclass.firebase

data class FcmMessageVO(
    val to: String,
    val priority: String,
    val data: FcmData
)

data class FcmData(
    val title: String,
    val body: String
)

data class FcmResponse(
    val multicastId: Long,
    val success: Int,
    val failure: Int,
    val canonicalIds: Int,
    val results: List<FcmResult>
)

data class FcmResult(
    val messageId: String
)

