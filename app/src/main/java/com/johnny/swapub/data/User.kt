package com.johnny.swapub.data

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class User(
    val id: String,
    val name: String,
    val image: String,
    val clubList: List<Club>,
    val place: Place,
    val favoriteList: List<Product>,
    val swappingList: List<Product>,
    val swappedList: List<Product>
) : Parcelable




