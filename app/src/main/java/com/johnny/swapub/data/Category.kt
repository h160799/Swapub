package com.johnny.swapub.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
    val category: String
): Parcelable