package com.johnny.swapub.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(
    val countries: List<Country>
): Parcelable

@Parcelize
data class Country(
    val name: String,
    val cities: List<City>,
    val id: String
): Parcelable

@Parcelize
data class City(
    val name: String,
    val id: String
): Parcelable

