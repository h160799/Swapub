package com.johnny.swapub.home.item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.johnny.swapub.R
import com.johnny.swapub.SwapubApplication
import com.johnny.swapub.data.*
import com.johnny.swapub.data.remote.SwapubRepository
import com.johnny.swapub.home.HomeTypeFilter
import com.johnny.swapub.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.Result

class HomeItemViewModel(val homeTypeFilter: HomeTypeFilter, val swapubRepository: SwapubRepository) : ViewModel() {

    private val _itemInfo = MutableLiveData<List<Product>>()
    val itemInfo: LiveData<List<Product>>
        get() = _itemInfo


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

    fun displayItemProductDetails(product: Product) {
        _navigateToSelecteditemInfo.value = product
    }

    fun displayItemProductDetailsComplete() {
        _navigateToSelecteditemInfo.value = null
    }

    val product = FirebaseFirestore.getInstance()
        .collection("product")

    fun addData() {
        val document = product.document()
        val data = Product(
            id = document.id,
            user = "12345678", //userId
            productTitle = "圍巾",
            description = "超級保暖，透氣不悶濕．",
            tradingStyle = "日文教學 10 小時．",
            categoryList = mutableListOf(
                "配件"
            ),
            time = 0,
            productImage = mutableListOf(
                "https://image-cdn-flare.qdm.cloud/q58cd0f6b43d44/image/cache/data/2019/03/22/fcdc868fe3c41e33b2cb884e70c01a92-max-w-1024.jpg"
            ),
            location = Location(
                countries = mutableListOf(
                    Country(
                        id = document.id,
                        name = "台灣",
                        cities = mutableListOf(
                            City(
                                id = document.id,
                                name = "台北市"
                            )
                        )
                    )
                )
            ),
            tradable = false,
            interestList = InterestList(
                    senderId = "23456789",
                    status = false
                )
            )
        document.set(data)
    }

//    private fun getData() {
//
//        product
//            .get()
//            .addOnSuccessListener { result ->
//                val listProduct = mutableListOf<Product>()
//                for (document in result) {
//                    val product = document.toObject(Product::class.java)
//
//                    listProduct.add(product)
//                    Logger.d("333$listProduct")
//                }
//
//                _itemInfo.value = listProduct
//            }
//    }

    init {
//        addData()
//        getData()
        getProductsResult()
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

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}