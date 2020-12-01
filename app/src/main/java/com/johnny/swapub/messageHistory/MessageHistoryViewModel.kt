package com.johnny.swapub.messageHistory

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.johnny.swapub.data.ChatRoom
import com.johnny.swapub.data.Message
import com.johnny.swapub.data.TimeUtil
import com.johnny.swapub.data.remote.SwapubRepository
import com.johnny.swapub.util.Logger
import java.util.*

class MessageHistoryViewModel(swapubRepository: SwapubRepository) : ViewModel() {
    private val _allMessageHistory = MutableLiveData<List<ChatRoom>>()

    val allMessageHistory: LiveData<List<ChatRoom>>

        get() = _allMessageHistory

    val chatRoom = FirebaseFirestore.getInstance()
        .collection("chatRoom")

    fun addData() {
        val document = chatRoom.document()
        val data = hashMapOf(
            "senderImage" to "https://cf.shopee.tw/file/8a9e53d639fc0e77e09dc7b608b48172",
            "senderName" to "Ni A Yi",
            "text" to hashMapOf(
                "responser" to "你那個東西很爛ㄋㄟ ",
                "time" to Calendar.getInstance().timeInMillis
            ),
            "id" to document.id
        )
        document.set(data)
    }

    private fun getData() {

        chatRoom
            .get()
            .addOnSuccessListener { result ->
                val listChatRoom = mutableListOf<ChatRoom>()
                for (document in result) {
                    val chatRoom = ChatRoom(
                            id = document.id,
                            ownerId = document.getString("ownerId"),
                            ownerName= document.getString("ownerName") ,
                            ownerImage = document.getString("ownerImage"),
                            productId = document.getString("productId"),
                            senderId = document.getString("senderId"),
                            senderName = document.getString("senderName"),
                            senderImage = document.getString("senderImage"),
                            text = Message(
                                asker = document.getString("asker"),
                                responser = document.getString("responser"),
                                time = document.getString("time")
                            )
                    )

                    listChatRoom.add(chatRoom)
                    Logger.d("333$listChatRoom")
                }

                _allMessageHistory.value = listChatRoom
            }
    }

    init {
//        addData()
        getData()
    }

}