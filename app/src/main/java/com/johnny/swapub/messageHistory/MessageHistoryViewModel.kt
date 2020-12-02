package com.johnny.swapub.messageHistory

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.johnny.swapub.R
import com.johnny.swapub.SwapubApplication
import com.johnny.swapub.data.ChatRoom
import com.johnny.swapub.data.LoadApiStatus
import com.johnny.swapub.data.Message
import com.johnny.swapub.data.TimeUtil
import com.johnny.swapub.data.remote.SwapubRepository
import com.johnny.swapub.util.Logger
import com.johnny.swapub.util.ServiceLocator.swapubRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class MessageHistoryViewModel(val swapubRepository: SwapubRepository) : ViewModel() {
    private val _allMessageHistory = MutableLiveData<List<ChatRoom>>()

    val allMessageHistory: LiveData<List<ChatRoom>>

        get() = _allMessageHistory

    val chatRoom = FirebaseFirestore.getInstance()
        .collection("chatRoom")


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

    fun addData() {
        val document = chatRoom.document()
        val data = ChatRoom(
            id = document.id,
            ownerId = "",
            ownerName = "",
            ownerImage = "",
            productId = "",
            senderId = "",
            senderName = "Ni A Yi",
            senderImage = "https://cf.shopee.tw/file/8a9e53d639fc0e77e09dc7b608b48172",
            text = Message(
                asker = "",
                responser = "你那個東西很爛ㄋㄟ ",
                time = 0

            )
        )
        document.set(data)
    }

//    private fun getData() {
//
//        chatRoom
//            .get()
//            .addOnSuccessListener { result ->
//                val listChatRoom = mutableListOf<ChatRoom>()
//                for (document in result) {
//                    val chatRoom = document.toObject(ChatRoom::class.java)
//
//                    listChatRoom.add(chatRoom)
//                    Logger.d("333$listChatRoom")
//                }
//
//                _allMessageHistory.value = listChatRoom
//            }
//    }

    init {
//        addData()
//        getData()
        getChatroomsResult()
    }

    fun getChatroomsResult() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = swapubRepository.getMessage()

            _allMessageHistory.value = when (result) {
                is com.johnny.swapub.data.Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    result.data
                }
                is com.johnny.swapub.data.Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                is com.johnny.swapub.data.Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                else -> {
                    _error.value = SwapubApplication.instance.getString(R.string.error)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
            _refreshStatus.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}