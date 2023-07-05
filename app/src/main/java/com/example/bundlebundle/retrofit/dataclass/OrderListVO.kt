package com.example.bundlebundle.retrofit.dataclass

data class OrderListVO(
    val orderList:List<OrderVO>
)

data class OrderVO(
    val productId: Int,
    val orderId: Int,
    val productCnt: Int,
    val name: String,
    val price: Int,
    val productThumbnailImg: String
)
