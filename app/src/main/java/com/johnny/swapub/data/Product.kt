package com.johnny.swapub.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val id: String? = "",
    val user: String? = "",
    val productTitle: String? = "",
    val description: String? = "",
    val tradingStyle: String = "",
    val categoryList: List<String>? = listOf(),
    val time: Long = 0,
    val productImage: List<String>? = listOf(),//image url
    val location: Location? = null,
    val tradable: Boolean? = null,
    val interestList: InterestList? = null
): Parcelable






