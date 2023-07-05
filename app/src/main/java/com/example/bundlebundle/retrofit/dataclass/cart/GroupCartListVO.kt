package com.example.bundlebundle.retrofit.dataclass.cart

import android.os.Parcel
import android.os.Parcelable

data class GroupCartListVO(
    val totalCnt: Int,
    val memberId: Int,
    val groupCart: List<GroupCartItemsVO>
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        TODO("groupCart")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(totalCnt)
        parcel.writeInt(memberId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GroupCartListVO> {
        override fun createFromParcel(parcel: Parcel): GroupCartListVO {
            return GroupCartListVO(parcel)
        }

        override fun newArray(size: Int): Array<GroupCartListVO?> {
            return arrayOfNulls(size)
        }
    }
}

data class GroupCartItemsVO(
    val memberId: Int,
    val cartCnt: Int,
    val groupNickname: String,
    val cartProducts: List<GroupCartProduct>
)

data class GroupCartProduct(
    val groupId: Int,
    val memberId: Int,
    val productId: Int,
    val productOrigin: String,
    val productBrand: String,
    val productName: String,
    val productThumbnailImg: String,
    val productPrice: Int,
    val productCnt: Int,
    val discountRate: Int
)
