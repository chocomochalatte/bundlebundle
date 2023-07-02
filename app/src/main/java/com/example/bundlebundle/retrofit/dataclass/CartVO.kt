package com.example.bundlebundle.retrofit.dataclass


data class CartVO(
    val cartCnt: Int,
    val cartProducts: List<CartProductVO>
)

data class CartProductVO(
    val cartId: Int,
    val productId: Int,
    val productOrigin: String,
    val productBrand: String,
    val productName: String,
    val productThumbnailImg: String,
    val productPrice: String,
    val productCnt: Int
)
