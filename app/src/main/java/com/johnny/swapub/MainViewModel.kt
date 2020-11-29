package com.johnny.swapub

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.johnny.swapub.data.remote.SwapubRepository
import com.johnny.swapub.util.CurrentFragmentType

class MainViewModel(private val swapubRepository: SwapubRepository) : ViewModel() {

    // Record current fragment to support data binding
    val currentFragmentType = MutableLiveData<CurrentFragmentType>()

}

