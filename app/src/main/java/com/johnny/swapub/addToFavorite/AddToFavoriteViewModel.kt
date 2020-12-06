package com.johnny.swapub.addToFavorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.johnny.swapub.R
import com.johnny.swapub.SwapubApplication
import com.johnny.swapub.data.*
import com.johnny.swapub.data.remote.SwapubRepository
import com.johnny.swapub.home.HomeTypeFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AddToFavoriteViewModel(
    val swapubRepository: SwapubRepository
) : ViewModel() {

    private val _itemInformation = MutableLiveData<List<Product>>()
    val itemInformation: LiveData<List<Product>>
        get() = _itemInformation


    private val _navigateToSelectedItemInfo = MutableLiveData<Product>()
    val navigateToSelectedItemInfo: MutableLiveData<Product>
        get() = _navigateToSelectedItemInfo

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

    fun displayItemProductDetails(product: Product) {
        _navigateToSelectedItemInfo.value = product
    }

    fun displayItemProductDetailsComplete() {
        _navigateToSelectedItemInfo.value = null
    }

    val product = FirebaseFirestore.getInstance()
        .collection("product")
    val user =  FirebaseFirestore.getInstance()
        .collection("user")


    init {
        getAllProducts()
    }


    fun getAllProducts() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = swapubRepository.getProduct()

            _itemInformation.value = when (result) {
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