package com.example.bundlebundle.retrofit.dataclass.cart

import android.os.Parcel
import android.os.Parcelable


data class CartVO(
    val cartCnt: Int,
    val cartProducts: List<CartProductVO>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        TODO("cartProducts")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(cartCnt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CartVO> {
        override fun createFromParcel(parcel: Parcel): CartVO {
            return CartVO(parcel)
        }

        override fun newArray(size: Int): Array<CartVO?> {
            return arrayOfNulls(size)
        }
    }
}

data class CartProductVO(
    val memberId: Int,
    val cartId: Int,
    val productId: Int,
    val productOrigin: String,
    val productBrand: String,
    val productName: String,
    val productThumbnailImg: String,
    val productPrice: Int,
    val productCnt: Int,
    val discountRate: Int
)
