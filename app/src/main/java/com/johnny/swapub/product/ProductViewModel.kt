package com.johnny.swapub.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.johnny.swapub.util.LoadApiStatus
import com.johnny.swapub.R
import com.johnny.swapub.SwapubApplication
import com.johnny.swapub.data.*
import com.johnny.swapub.data.remote.SwapubRepository
import com.johnny.swapub.util.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class ProductViewModel(
        private val swapubRepository: SwapubRepository,
        private val arguments: Product

) : ViewModel() {

    private val _senderInfo = MutableLiveData<User>()
    val senderInfo: LiveData<User>
        get() = _senderInfo

    var userId: String = UserManager.userId

    private val _productDetail = MutableLiveData<Product>().apply {
        value = arguments
    }

    val productDetail: LiveData<Product>
        get() = _productDetail

    private val _userDetail = MutableLiveData<User>()
    val userDetail: LiveData<User>
        get() = _userDetail

    private val _userFavorList = MutableLiveData<List<String>>()
    val userFavorList: LiveData<List<String>>
        get() = _userFavorList

    val isFavor = MutableLiveData<Boolean>()
            .apply {
                value = false
            }

    private val _interestMessage = MutableLiveData<Boolean>()
    val interestMessage: LiveData<Boolean>
        get() = _interestMessage

    private val _interestMessageText = MutableLiveData<Boolean>()
    val interestMessageText: LiveData<Boolean>
        get() = _interestMessageText

    private val _addChatRoom = MutableLiveData<ChatRoom>()
    val addChatRoom: LiveData<ChatRoom>
        get() = _addChatRoom

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
        getUserDetail(arguments)
        getUserFavor()
    }

    fun getSenderInfo(userId: String) {
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = swapubRepository.getUserInfo(userId)

            _senderInfo.value = when (result) {
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

    fun getUserDetail(arguments: Product) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = swapubRepository.getUserDetail(arguments)

            _userDetail.value = when (result) {
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

    fun getUserFavor() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = swapubRepository.getUserFavor(UserManager.userId)

            _userFavorList.value = when (result) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    result.data
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                is Result.Error -> {
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

    fun addProductToFavorList(productId: String) {
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING
            val favoriteList: MutableList<String> = mutableListOf()
            _userFavorList.value.let {
                if (it != null) {
                    for (list in it) {
                        favoriteList.add(list)
                    }
                }
            }
            var isInFavoriteList = false
            for (list in favoriteList) {
                if (list == productId) {
                    isInFavoriteList = true
                }
            }
            if (!isInFavoriteList) {
                favoriteList.add(productId)
            }

            when (val result = swapubRepository.updateProductToFavorList(productId, favoriteList)) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                }
                else -> {
                    _error.value = SwapubApplication.instance.getString(R.string.error)
                    _status.value = LoadApiStatus.ERROR
                }
            }
        }
    }

    fun removeProductToFavorList(productId: String) {
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING
            val favoriteList: MutableList<String> = mutableListOf()
            _userFavorList.value.let {
                if (it != null) {
                    for (list in it) {
                        favoriteList.add(list)
                    }
                }
            }
            favoriteList.remove(productId)
            when (val result = swapubRepository.updateProductToFavorList(productId, favoriteList)) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                }
                else -> {
                    _error.value = SwapubApplication.instance.getString(R.string.error)
                    _status.value = LoadApiStatus.ERROR
                }
            }
        }
    }

    fun isFavor(favorList: List<String>) {
        for (list in favorList) {
            if (list == _productDetail.value?.id) {
                isFavor.value = true
            }
        }
    }

    fun postInterestMessage(chatRoom: ChatRoom, user: User) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = swapubRepository.postInterestMessage(chatRoom, user)) {
                is com.johnny.swapub.data.Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    _interestMessage.value = true
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

    fun addChatRoom(): ChatRoom {
        return ChatRoom(
                id = "",
                time = Calendar.getInstance().timeInMillis,
                productId = _productDetail.value?.id,
                ownerId = _productDetail.value?.user,
                ownerName = userDetail.value?.name,
                ownerImage = userDetail.value?.image,
                senderId = UserManager.userId,
                senderName = UserManager.userName,
                senderImage = UserManager.userImage,
                text = "我有興趣，想多了解！！！"
        )
    }

    fun postInterestMessageText(message: Message, chatRoom: ChatRoom) {


        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = chatRoom.id?.let { swapubRepository.postMessage(message, it) }) {
                is com.johnny.swapub.data.Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    _interestMessageText.value = true
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

    fun addMessage(): Message {
        return Message(
                id = UserManager.userId,
                time = Calendar.getInstance().timeInMillis,
                image = "",
                senderImage = UserManager.userImage,
                text = "我有興趣，想多了解！！！"
        )
    }

    fun getAddedChatRoom(chatRoom: ChatRoom) {

        coroutineScope.launch {
            _status.value = LoadApiStatus.LOADING
            val result = swapubRepository.getAddedChatRoom(chatRoom)
            _addChatRoom.value = when (result) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    result.data
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                is Result.Error -> {
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
