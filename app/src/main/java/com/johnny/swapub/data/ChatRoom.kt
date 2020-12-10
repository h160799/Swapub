package com.johnny.swapub.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*


//@Parcelize
//data class messageList(
//    var message: List<Message>? = null
//): Parcelable{
//    fun toMessage(): List<ConversationAdapter.CSText>{
//        val items = mutableListOf<ConversationAdapter.CSText>()
//        message?.let {
//            for (item in it){
//                if (item.asker?.id.toString()  == "123456678"){(
//                    items.add(ConversationAdapter.CSText.Response(item)))
//                    }
//                else{
//                    items.add(ConversationAdapter.CSText.Send(item))
//                }
//            }
//        }
//        return items
//    }
//}


@Parcelize
data class ChatRoom(
    val id: String? = "",
    val time: Long? = -1,
    val productId: String? = "",
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