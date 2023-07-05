package com.example.bundlebundle.retrofit.dataclass.order

import java.sql.Timestamp

data class ProductOrderVO(
    val productId: Int,
    val memberId: Int,
    val groupId: Int,
    val price: Int,
    val thumbnailImg: String,
    val createdAt: Timestamp,
    val name: String,
    val productCnt: Int,
    val orderId: Int
)