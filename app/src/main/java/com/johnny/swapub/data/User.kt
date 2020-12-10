package com.johnny.swapub.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class User(
    var id: String = "",
    var name: String? = "",
    var image: String = "",
    val clubList: List<Club>? = mutableListOf(),
    val place: Place? = null,
    val favoriteList: List<String>? = mutableListOf(),
    val swappingList: List<String>? = mutableListOf(),
    val swappedList: List<String>? = mutableListOf()
) : Parcelable




