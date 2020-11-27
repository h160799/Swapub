package com.johnny.swapub.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val user: User,
    val name: String,
    val image: String,
    val categoryList: List<Category>,
    val time: Long,
    val productImage: List<ProductImage> ,
    val location: Location,
    val tradable : Boolean,
    val interestList : InterestList
): Parcelable






