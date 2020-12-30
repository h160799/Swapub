package com.johnny.swapub

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.johnny.swapub.data.ChatRoom
import com.johnny.swapub.data.LoadApiStatus
import com.johnny.swapub.data.Result
import com.johnny.swapub.data.User
import com.johnny.swapub.data.remote.SwapubRepository
import com.johnny.swapub.util.CurrentFragmentType
import com.johnny.swapub.util.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(private val swapubRepository: SwapubRepository) : ViewModel() {

    var userId: String = UserManager.userId

    // Record current fragment to support data binding
    val currentFragmentType = MutableLiveData<CurrentFragmentType>()
    val navigate = MutableLiveData<Int>()

    private val _getUserData = MutableLiveData<User>()
    val getUserData: LiveData<User>
        get() = _getUserData


    // Handle navigation to profile by bottom nav directly which includes icon change
    private val _navigateToProfileByBottomNav = MutableLiveData<User>()

    val navigateToProfileByBottomNav: LiveData<User>
        get() = _navigateToProfileByBottomNav

    // Handle navigation to home by bottom nav directly which includes icon change
    private val _navigateToHomeByBottomNav = MutableLiveData<Boolean>()

    val navigateToHomeByBottomNav: LiveData<Boolean>
        get() = _navigateToHomeByBottomNav


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
        getUserInfo(userId)
    }

    fun getUserInfo(userId:String) {

        coroutineScope.launch {
            _status.value = LoadApiStatus.LOADING
            val result = swapubRepository.getUserInfo(userId)
            _getUserData.value = when (result) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    UserManager.user = result.data
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


    fun onProfileNavigated() {
        _navigateToProfileByBottomNav.value = null
    }

    fun navigateToHomeByBottomNav() {
        _navigateToHomeByBottomNav.value = true
    }

    fun onHomeNavigated() {
        _navigateToHomeByBottomNav.value = null
    }






}

