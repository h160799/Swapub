package com.johnny.swapub.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
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

class SettingViewModel(
    val swapubRepository: SwapubRepository
) : ViewModel() {

    var userId: String = UserManager.userId

    private val _userData = MutableLiveData<User>()
    val userData: LiveData<User>
        get() = _userData

    val nameEditText = MutableLiveData<String>()

    val editTextPlace = MutableLiveData<String>()

    var userImage = MutableLiveData<String>()

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
    getUser(userId)
}


    fun getUser(userId:String) {

        coroutineScope.launch {
            _status.value = LoadApiStatus.LOADING
            val result = swapubRepository.getUserInfo(userId)
            _userData.value = when (result) {
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

    fun updateUserInfo(userId: String, image:String, name: String, place:String) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = swapubRepository.updateUserInfo(userId,image,name,place)) {
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


}