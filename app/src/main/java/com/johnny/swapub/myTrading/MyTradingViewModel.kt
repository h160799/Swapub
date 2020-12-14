package com.johnny.swapub.myTrading

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.johnny.swapub.R
import com.johnny.swapub.SwapubApplication
import com.johnny.swapub.data.LoadApiStatus
import com.johnny.swapub.data.Product
import com.johnny.swapub.data.remote.SwapubRepository
import com.johnny.swapub.util.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyTradingViewModel(
    val swapubRepository: SwapubRepository
) : ViewModel() {

    val userId: String = UserManager.userId

    private val _myTradingList = MutableLiveData<List<Product>>()
    val myTradingList: MutableLiveData<List<Product>>
        get() = _myTradingList

    private val _postProduct = MutableLiveData<List<Product>>()
    val postProduct: MutableLiveData<List<Product>>
        get() = _postProduct


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
        getPostProduct(userId)
    }


    fun getPostProduct( userId: String) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = swapubRepository.getPostProduct(userId)

            _postProduct.value = when (result) {
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

        }
    }




    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }




}