package com.johnny.swapub.club.clubBookShare

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.johnny.swapub.R
import com.johnny.swapub.SwapubApplication
import com.johnny.swapub.data.LoadApiStatus
import com.johnny.swapub.data.Result
import com.johnny.swapub.data.remote.SwapubRepository
import com.johnny.swapub.util.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ClubBookShareViewModel(
    val swapubRepository: SwapubRepository

) : ViewModel() {

    private val _userClubList = MutableLiveData<List<String>>()
    val userClubList: LiveData<List<String>>
        get() = _userClubList

    val isClub = MutableLiveData<Boolean>()
        .apply {
            value = false
        }

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
    getUserClub()
}

    fun getUserClub() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = swapubRepository.getUserClub(UserManager.userId)

            _userClubList.value = when (result) {
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

    fun addToClubList(clubId: String = "clubBookShare"){
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING
            val clubList: MutableList<String> = mutableListOf()
            _userClubList.value.let {
                if (it != null) {
                    for (list in it) {
                        clubList.add(list)
                    }
                }
            }
            var isInClubList = false
            for (list in clubList) {
                if (list == clubId) {
                    isInClubList = true
                }
            }
            if (!isInClubList) {
                clubList.add(clubId)
            }
            when (val result = swapubRepository.updateToClubList(clubList)) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                }
                else -> {
                    _error.value = SwapubApplication.instance.getString(R.string.error)
                    _status.value = LoadApiStatus.ERROR
                }
            }
        }
    }

    fun removeToClubList(clubId: String = "clubBookShare"){
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING
            val clubList: MutableList<String> = mutableListOf()
            _userClubList.value.let {
                if (it != null) {
                    for (list in it) {
                        clubList.add(list)
                    }
                }
            }
            clubList.remove(clubId)
            when (val result = swapubRepository.updateToClubList(clubList)) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                }
                else -> {
                    _error.value = SwapubApplication.instance.getString(R.string.error)
                    _status.value = LoadApiStatus.ERROR
                }
            }
        }
    }

    fun isClub(clubList: List<String>) {
        for (list in clubList) {
            if (list == UserManager.user.clubList.toString()) {
                isClub.value = true
            }
        }
    }





    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}