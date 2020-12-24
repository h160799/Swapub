package com.johnny.swapub.myClub

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.johnny.swapub.R
import com.johnny.swapub.SwapubApplication
import com.johnny.swapub.data.Club
import com.johnny.swapub.data.LoadApiStatus
import com.johnny.swapub.data.Product
import com.johnny.swapub.data.Result
import com.johnny.swapub.data.User
import com.johnny.swapub.data.remote.SwapubRepository
import com.johnny.swapub.util.Logger
import com.johnny.swapub.util.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyClubViewModel(
    val swapubRepository: SwapubRepository
) : ViewModel() {


    private val _userF = MutableLiveData<User>()
    val userF: LiveData<User>
        get() = _userF

    private val _myClub = MutableLiveData<List<Club>>()
    val myClub: MutableLiveData<List<Club>>
        get() = _myClub

    val getClubArtWork = MutableLiveData<Boolean>()
    val getClubBookShare = MutableLiveData<Boolean>()
    val getClubElectronicProduct = MutableLiveData<Boolean>()
    val getClubFashion = MutableLiveData<Boolean>()
    val getClubLiveLife = MutableLiveData<Boolean>()
    val getClubMakeup = MutableLiveData<Boolean>()
    val getClubMenClothes = MutableLiveData<Boolean>()
    val getClubPlant = MutableLiveData<Boolean>()
    val getClubSporty = MutableLiveData<Boolean>()
    val getClubVideoGame = MutableLiveData<Boolean>()
    val getClubVolunteer = MutableLiveData<Boolean>()
    val getClubWomenMenClothes = MutableLiveData<Boolean>()






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
        getUserClubList()
//        Logger.d("7777777777${_userClubList.value}")
    }


    fun getUserClubList() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = swapubRepository.getUserClubList(UserManager.userId)

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

    fun getClub(clubId: List<String>) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = swapubRepository.getClub(clubId)

            _myClub.value = when (result) {
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