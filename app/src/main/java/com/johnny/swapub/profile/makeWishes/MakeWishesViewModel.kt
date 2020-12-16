package com.johnny.swapub.profile.makeWishes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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


class MakeWishesViewModel(
    val swapubRepository: SwapubRepository
) : ViewModel() {

    val productTitleEditText = MutableLiveData<String>()
    val descriptionEditText = MutableLiveData<String>()
    val tradingStyleEditText = MutableLiveData<String>()
    val categoryEditText = MutableLiveData<String>()

    val wishableSelect = MutableLiveData<Boolean>()



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




    fun postTradingInfo(product: Product) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = swapubRepository.postTradingInfo(product)) {
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

    fun addProduct() : Product {
        return Product(
            id = "",
            user = UserManager.userId,
            productTitle = productTitleEditText.value,
            description = descriptionEditText.value,
            tradingStyle = tradingStyleEditText.value,
            category = categoryEditText.value,
            time = Calendar.getInstance().timeInMillis,
            productImage = mutableListOf(
                ""
            ),
            location = Location(
                countries = mutableListOf(
                    Country(
                        id = "",
                        name = "",
                        cities = mutableListOf(
                            City(
                                id = "",
                                name = ""
                            )
                        )
                    )
                )
            ),
            wishable = wishableSelect.value,
            tradable = false,
            interestList = InterestList(
                senderId = "",
                status = false
            )
        )
    }



    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}