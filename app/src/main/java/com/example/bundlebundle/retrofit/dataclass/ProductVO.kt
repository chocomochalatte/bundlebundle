package com.example.bundlebundle.retrofit.dataclass

data class ProductVO(
    val id: Int,
    val brand: String,
    val name: String,
    val thumbnailImg: String,
    val price: Int,
    val discountRate: Int,
    val origin: String,
    val packageType: String
)