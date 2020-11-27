package com.johnny.swapub.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data  class Post(
    val id: String,
    val posterId: String,
    val content: String,
    val productImage: String
):Parcelable