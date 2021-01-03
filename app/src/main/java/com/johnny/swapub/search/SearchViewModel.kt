package com.johnny.swapub.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.johnny.swapub.R
import com.johnny.swapub.SwapubApplication
import com.johnny.swapub.util.LoadApiStatus
import com.johnny.swapub.data.Product
import com.johnny.swapub.data.remote.SwapubRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class SearchViewModel(
        val swapubRepository: SwapubRepository

) : ViewModel() {

    private val _allProducts = MutableLiveData<List<Product>>()
    val allProducts: LiveData<List<Product>>
        get() = _allProducts

    var liveSearch = MutableLiveData<List<Product>>()

    val editSearch = MutableLiveData<String>()

    val search = MutableLiveData<String>()

    private val _navigateToDetail = MutableLiveData<Product>()
    val navigateToDetail: LiveData<Product>
        get() = _navigateToDetail

    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    // error: The internal MutableLiveData that stores the error of the most recent request
    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error

    private val _leave = MutableLiveData<Boolean>()
    val leave: LiveData<Boolean>
        get() = _leave

    // status for the loading icon of swl
    private val _refreshStatus = MutableLiveData<Boolean>()
    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getAllProducts()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun getAllProducts() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = swapubRepository.getProduct()

            _allProducts.value = when (result) {
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

    fun filter(list: List<Product>, query: String): List<Product> {
        val lowerCaseQueryString = query.toLowerCase(Locale.ROOT)
        val filteredList = mutableListOf<Product>()
        for (product in list) {
            val productTitle = product.productTitle?.toLowerCase(Locale.ROOT) ?: ""
            val category = product.category
            if (category != null) {
                if (productTitle.contains(lowerCaseQueryString) || category.contains(lowerCaseQueryString)) {
                    filteredList.add(product)
                }
            } else {
                if (productTitle.contains(lowerCaseQueryString)) {
                    filteredList.add(product)
                }
            }
        }
        return filteredList
    }

    fun navigateToDetail(product: Product) {
        _navigateToDetail.value = product
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }
}



