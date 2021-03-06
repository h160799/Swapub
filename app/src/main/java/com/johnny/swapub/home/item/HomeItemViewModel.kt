package com.johnny.swapub.home.item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.johnny.swapub.util.LoadApiStatus
import com.johnny.swapub.R
import com.johnny.swapub.SwapubApplication
import com.johnny.swapub.data.*
import com.johnny.swapub.data.remote.SwapubRepository
import com.johnny.swapub.home.HomeTypeFilter
import com.johnny.swapub.util.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeItemViewModel(
        val homeTypeFilter: HomeTypeFilter,
        val swapubRepository: SwapubRepository
) : ViewModel() {

    private val _itemInfo = MutableLiveData<List<Product>>()
    val itemInfo: LiveData<List<Product>>
        get() = _itemInfo

    private val _itemPlaceInfo = MutableLiveData<List<Product>>()
    val itemPlaceInfo: LiveData<List<Product>>
        get() = _itemPlaceInfo

    val itemInfoSelector = MutableLiveData<List<Product>>()

    private val _userI = MutableLiveData<User>()
    val userI: LiveData<User>
        get() = _userI

    private val _navigateToSelecteditemInfo = MutableLiveData<Product>()
    val navigateToSelecteditemInfo: MutableLiveData<Product>
        get() = _navigateToSelecteditemInfo

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
        getUserInHome()
        getProductsResult()
        getProductsWithPlace()
    }

    fun getProductsResult() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = swapubRepository.getProduct()

            _itemInfo.value = when (result) {
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

    fun getProductsWithPlace() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = swapubRepository.getProductWithPlace()

            _itemPlaceInfo.value = when (result) {
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

    fun getUserInHome() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = swapubRepository.getUserInfo(UserManager.userId)

            _userI.value = when (result) {
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

    fun displayItemProductDetails(product: Product) {
        _navigateToSelecteditemInfo.value = product
    }

    fun displayItemProductDetailsComplete() {
        _navigateToSelecteditemInfo.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}