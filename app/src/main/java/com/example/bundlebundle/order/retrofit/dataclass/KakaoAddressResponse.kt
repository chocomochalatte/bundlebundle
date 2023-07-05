package com.example.bundlebundle.order.retrofit.dataclass

import com.google.gson.annotations.SerializedName
import okhttp3.Address

data class KakaoAddressResponse(
    val documents: List<AddressDocument>,
    val meta: MetaData
)

data class AddressDocument(
    val address_name: String,
    val y: Double,
    val x: Double
)

data class MetaData(
    val total_count: Int
)

data class RoadAddress(
    @SerializedName("address_name")
    val addressName: String,
    // 다른 필드들을 추가로 정의할 수 있습니다.
)
