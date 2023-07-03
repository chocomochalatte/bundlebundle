package com.example.bundlebundle.retrofit.dataclass

data class GroupCartListVO(
    val totalCnt: Int,
    val groupCart: List<GroupCartItemsVO>
)

data class GroupCartItemsVO(
    val memberId: Int,
    val cartCnt: Int,
    val groupNickname: String,
    val cartProducts: List<GroupCartProduct>
)

data class GroupCartProduct(
    val productId: Int,
    val productOrigin: String,
    val productBrand: String,
    val productName: String,
    val productThumbnailImg: String,
    val productPrice: Int,
    val productCnt: Int
)
