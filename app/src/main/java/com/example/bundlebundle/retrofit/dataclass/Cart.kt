package com.example.bundlebundle.retrofit.dataclass

data class Cart(
    val cartCnt: Int,
    val memberId: Int,
    val cartProducts: List<CartProduct>
)

data class CartProduct(
    val cartId: Int,
    val productId: Int,
    val productOrigin: String,
    val productBrand: String,
    val productName: String,
    val productThumbnailImg: String,
    val productPrice: String,
    val productCnt: Int
)


