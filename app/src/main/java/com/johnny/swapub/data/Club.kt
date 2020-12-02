package com.johnny.swapub.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Club(
    val id: String? = "",
    val name: String? = "",
    val productList: List<Product>? = null

) : Parcelable