package com.johnny.swapub.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*



@Parcelize
data class ChatRoom(
    val id: String? = "",
    var time: Long? = -1,
    val productId: String? = "",
    var tradingSelect: Boolean? = false,
    val ownerId: String? = "",
    val ownerName: String? = "",
    val ownerImage: String? = "",
    val senderId: String? = "",
    val senderName: String? = "",
    val senderImage: String? = "",
    val text: String? = ""
): Parcelable


@Parcelize
data class Message(
    var id: String? = "",
    var senderImage: String = "",
    var text: String? = "",
    var time: Long? = -1,
    var image: String? = ""
): Parcelable

