package com.johnny.swapub.messageHistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.johnny.swapub.data.ChatRoom
import com.johnny.swapub.data.User
import com.johnny.swapub.data.remote.SwapubRepository

class MessageHistoryViewModel(swapubRepository: SwapubRepository) : ViewModel() {
    private val _allMessageHistory = MutableLiveData<List<ChatRoom>>()

    val allMessageHistory: LiveData<List<ChatRoom>>

        get() = _allMessageHistory

    private val MessageHistory = FirebaseFirestore.getInstance()
        .collection("message")





}