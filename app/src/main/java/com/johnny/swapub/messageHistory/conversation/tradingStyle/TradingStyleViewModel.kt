package com.johnny.swapub.messageHistory.conversation.tradingStyle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.johnny.swapub.R
import com.johnny.swapub.SwapubApplication
import com.johnny.swapub.data.ChatRoom
import com.johnny.swapub.data.LoadApiStatus
import com.johnny.swapub.data.TradingType
import com.johnny.swapub.data.remote.SwapubRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class TradingStyleViewModel(
    val swapubRepository: SwapubRepository,
    val arguments: ChatRoom

): ViewModel() {

    val chatRoom = arguments

    val tradingTypeSelect = MutableLiveData<String>()

    val tradingType = MutableLiveData<TradingType>().apply {
        value = TradingType()
    }

    var image = MutableLiveData<String>()


    val tradingEditText = MutableLiveData<String>()


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







    fun postTradingType(chatRoomId: String,tradingType: TradingType) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = swapubRepository.postTradingType(chatRoomId,tradingType)) {
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

fun addTradingType(): TradingType{
        return TradingType(
            id = chatRoom.id,
            productId = chatRoom.productId,
            type = tradingTypeSelect.value.toString(),
            time = Calendar.getInstance().timeInMillis,
            image = image.value,
            text = tradingEditText.value
        )
    }


    fun updateTradingSelect(chatRoomId: String, tradingSelect: Boolean) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = swapubRepository.updateTradingSelect(chatRoomId, tradingSelect)) {
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





    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}