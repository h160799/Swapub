package com.johnny.swapub.home.item

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johnny.swapub.data.Product
import com.johnny.swapub.home.HomeTypeFilter
import kotlinx.coroutines.launch

class HomeItemViewModel(val type: HomeTypeFilter) : ViewModel() {

    private val _itemInfo = MutableLiveData<List<Product>>()
    val itemInfo: LiveData<List<Product>>
        get() = _itemInfo


    private val _navigateToSelecteditemInfo = MutableLiveData<Product>()
    val navigateToSelecteditemInfo: MutableLiveData<Product>
        get() = _navigateToSelecteditemInfo

    init {
        getItemInfo(listOf())
    }

    fun getItemInfo(oldProduct: List<Product>) {

    }




}