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

class HomeItemViewModel(
    val homeTypeFilter: HomeTypeFilter,
    val swapubRepository: SwapubRepository
) : ViewModel() {

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
    val user =  FirebaseFirestore.getInstance()
        .collection("user")

    fun addData() {
        val document = product.document()
        val data = Product(
            id = document.id,
            user = "12345678", //userId
            productTitle =
                    "日本KINTO 提式輕巧保溫瓶 500ml共8色可選",
            description = "時尚又實用",
            tradingStyle = "物品交換．大容量馬克杯",
            category = "配件",
            time = 0,
            productImage = mutableListOf(
                "https://s.yimg.com/zp/MerchandiseImages/A377C865AC-SP-6987044.jpg"
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

    fun addUserData() {
        val document = user.document()
        val data = User(
            id = "12345678",
            name = "Dato",
            image = "https://files.bountyhunter.co/bhuntr/public/201508/5e68c386-cc1e-4724-b336-aa1459a90fe6_800x800.jpg",
            clubList = mutableListOf(
                Club(
                    id = "",
                    name = "",
                    productList = null
                )
            ),
            place = Place(
                country = "台灣",
                city = "台北市"
            ),
            favoriteList  = mutableListOf(

            ),
            swappingList = mutableListOf(

            ),
            swappedList = mutableListOf(

            )
        )
        document.set(data)
    }



    init {
//        addData()
//        getData()
//        addUserData()
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