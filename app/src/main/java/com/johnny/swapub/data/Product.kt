package com.johnny.swapub.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
        val id: String? = "",
        val user: String? = "",
        val productTitle: String? = "",
        val description: String? = "",
        val tradingStyle: String? = "",
        val category: String? = "",
        val time: Long = -1,
        val productImage: List<String>? = listOf(),//image url
        val location: String? = "",
        val tradable: Boolean? = false,
        val wishable: Boolean? = false,
        val interestList: InterestList? = null
) : Parcelable

@Parcelize
data class TradingType(
        val id: String? = "",
        var productId: String? = "",
        var type: String = "",
        var text: String? = "",
        var image: String? = "",
        var time: Long? = -1
) : Parcelable




