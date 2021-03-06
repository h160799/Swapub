package com.johnny.swapub.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class User(
        var id: String = "",
        var name: String? = "",
        var image: String = "",
        val clubList: List<String>? = mutableListOf(),
        var place: String? = "",
        val favoriteList: List<String>? = mutableListOf(),
        val swappingList: List<String>? = mutableListOf(),
        val swappedList: List<String>? = mutableListOf()
) : Parcelable




