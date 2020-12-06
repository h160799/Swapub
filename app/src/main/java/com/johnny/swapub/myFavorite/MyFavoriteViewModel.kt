package com.johnny.swapub.myFavorite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.johnny.swapub.R
import com.johnny.swapub.SwapubApplication
import com.johnny.swapub.data.LoadApiStatus
import com.johnny.swapub.data.Product
import com.johnny.swapub.data.User
import com.johnny.swapub.data.remote.SwapubRepository
import com.johnny.swapub.util.ServiceLocator
import com.johnny.swapub.util.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyFavoriteViewModel(
    val swapubRepository: SwapubRepository
) : ViewModel() {

    private val _userF = MutableLiveData<User>()
    val userF: LiveData<User>
        get() = _userF

    private val _favoriteListPage = MutableLiveData<List<Product>>()
    val favoriteListPage: MutableLiveData<List<Product>>
        get() = _favoriteListPage

    private val _favoriteProduct = MutableLiveData<List<Product>>()
    val favoriteProduct: MutableLiveData<List<Product>>
        get() = _favoriteProduct


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
    getFavoriteList()
}




    fun getFavoriteList() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = swapubRepository.getFavoriteList(userL = UserManager.user.id.toString())

            _userF.value = when (result) {
                is com.johnny.swapub.data.Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    Log.d("fff","${result.data}")

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

    fun getFavoriteProduct(productId: List<String>) {


            coroutineScope.launch {

                _status.value = LoadApiStatus.LOADING

                val result = swapubRepository.getFavoriteProduct(productId)

                _favoriteProduct.value = when (result) {
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