package com.johnny.swapub.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class User(
    val id: String? = "",
    val name: String? = "",
    val image: String = "",
    val clubList: List<Club>? = listOf(),
    val place: Place? = null,
    val favoriteList: List<Product>? = null,
    val swappingList: List<Product>? = null,
    val swappedList: List<Product>? = null
) : Parcelable




