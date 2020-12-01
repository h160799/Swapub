package com.johnny.swapub.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ChatRoom(
    val id: String?,
    val ownerId: String?,
    val ownerName: String?,
    val ownerImage: String?,
    val productId: String?,
    val senderId: String?,
    val senderName: String?,
    val senderImage: String?,
    val text: Message
): Parcelable


@Parcelize
data class Message (
    val asker: String?,
    val responser: String?,
    val time: String?
): Parcelable