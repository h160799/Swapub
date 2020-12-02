package com.johnny.swapub.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InterestList(
    val senderId: String? = "",
    val status: Boolean? = null
): Parcelable