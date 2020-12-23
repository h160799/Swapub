package com.johnny.swapub.messageHistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.johnny.swapub.data.*
import com.johnny.swapub.data.remote.SwapubRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.util.*

class MessageHistoryViewModel(
    private val swapubRepository: SwapubRepository

    ) : ViewModel() {
    private val _allMessageHistory = MutableLiveData<List<ChatRoom>>()
    val allMessageHistory: LiveData<List<ChatRoom>>
        get() = _allMessageHistory

    private val _chatRoom = MutableLiveData<ChatRoom>().apply {
        value
    }
    val chatRoom: LiveData<ChatRoom>
        get() = _chatRoom

    var liveChatRooms = MutableLiveData<List<ChatRoom>>()

    var isEmpty = MutableLiveData<Boolean>()

    // Handle navigation to conversation
    private val _navigateToConversation = MutableLiveData<ChatRoom>()
    val navigateToConversation: LiveData<ChatRoom>
        get() = _navigateToConversation


    val chatRoomA = FirebaseFirestore.getInstance()
        .collection("chatRoom")

    val message = FirebaseFirestore.getInstance()
        .collection("message")


    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    // error: The internal MutableLiveData that stores the error of the most recent request
    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error

    // status for the loading icon of swl
    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)



    init {
//        addMessage()
        getMessageHistory()
    }



    fun addMessage() {
        val document = chatRoomA.document().collection("message").document()
        val data = Message(
            id = "12345678",
            senderImage = "https://stickershop.line-scdn.net/stickershop/v1/product/583/LINEStorePC/main.png;compress=true",
            text = "好像不錯唷",
            time = Calendar.getInstance().timeInMillis,
            image = ""
               )

        document.set(data)
    }


    fun getMessageHistory() {
        liveChatRooms = swapubRepository.getMessageHistory()
        _status.value = LoadApiStatus.DONE
        _refreshStatus.value = false
    }

    fun navigateToConversation(chatRoom: ChatRoom) {
        _navigateToConversation.value = chatRoom
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onConversationlNavigated() {
        _navigateToConversation.value = null
    }
}

