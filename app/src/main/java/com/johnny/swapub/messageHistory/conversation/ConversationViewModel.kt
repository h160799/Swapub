package com.johnny.swapub.messageHistory.conversation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.johnny.swapub.R
import com.johnny.swapub.SwapubApplication
import com.johnny.swapub.data.ChatRoom
import com.johnny.swapub.data.LoadApiStatus
import com.johnny.swapub.data.Message
import com.johnny.swapub.data.Product
import com.johnny.swapub.data.remote.SwapubRepository
import com.johnny.swapub.util.Logger
import com.johnny.swapub.util.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ConversationViewModel(private val swapubRepository: SwapubRepository,
                            private val arguments: ChatRoom
) : ViewModel() {

    private val _chatRoom = MutableLiveData<ChatRoom>().apply {
        value = arguments
    }

    val chatRoom: LiveData<ChatRoom>
        get() = _chatRoom

    var image = MutableLiveData<String>()

    var liveMessages = MutableLiveData<List<Message>>()

    val message = MutableLiveData<Message>().apply {
        value = Message()
    }

    val document = MutableLiveData<String>().apply {
        value =  chatRoom.value?.id
    }

    val tradingStyle = MutableLiveData<Boolean>().apply {
        value = arguments.ownerId == UserManager.userId
    }

    private val _conversationProduct = MutableLiveData<Product>()
    val conversationProduct: LiveData<Product>
        get() = _conversationProduct


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
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]${this}")
        Logger.i("------------------------------------")
        message.value?.senderImage = UserManager.userImage
        message.value?.id = UserManager.userId.toString()
        getLiveMessagesResult()
        arguments.productId?.let { getOneProduct(it) }
    }


    fun getLiveMessagesResult() {
        liveMessages = swapubRepository.getMessage(document.value.toString())
        _status.value = LoadApiStatus.DONE
        _refreshStatus.value = false
    }


    fun postMessage(message: Message, document: String) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = swapubRepository.postMessage(message, document)) {
                is com.johnny.swapub.data.Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                }
                is com.johnny.swapub.data.Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                }
                is com.johnny.swapub.data.Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                }
                else -> {
                    _error.value = SwapubApplication.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                }
            }
        }
    }


    fun getOneProduct(productId: String) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = swapubRepository.getOneProduct(productId)

            _conversationProduct.value = when (result) {
                is com.johnny.swapub.data.Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    Logger.d("ccc${result.data}")
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