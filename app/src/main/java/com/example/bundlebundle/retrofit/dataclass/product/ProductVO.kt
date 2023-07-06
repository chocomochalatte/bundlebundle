package com.example.bundlebundle.retrofit.dataclass.product

import android.os.Parcel
import android.os.Parcelable

data class ProductVO(
    val id: Int,
    val brand: String?,
    val name: String?,
    val thumbnailImg: String?,
    val price: Int,
    val discountRate: Int,
    val origin: String?,
    val packageType: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun describeContents(): Int {return 0}

    override fun writeToParcel(p0: Parcel, p1: Int) {
    }

    companion object CREATOR : Parcelable.Creator<ProductVO> {
        override fun createFromParcel(parcel: Parcel): ProductVO {
            return ProductVO(parcel)
        }

        override fun newArray(size: Int): Array<ProductVO?> {
            return arrayOfNulls(size)
        }
    }
}