package com.johnny.swapub.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val user: String? = "",
    val name: String? = "",
    val image: String? = "",
    val categoryList: List<Category>? = null,
    val time: Long = 0,
    val productImage: List<String>? = null,//image url
    val location: Location? = null,
    val tradable: Boolean? = null,
    val interestList: InterestList? = null
): Parcelable






