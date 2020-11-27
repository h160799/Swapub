package com.johnny.swapub.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ChatRoom (
    val id: String,
    val ownerId: String,
    val productId: String,
    val senderId: String,
    val text: Message
): Parcelable


@Parcelize
data class Message (
    val asker: String,
    val responser: String,
    val time: Long
): Parcelable