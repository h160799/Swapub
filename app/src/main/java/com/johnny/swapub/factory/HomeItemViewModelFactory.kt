package com.johnny.swapub.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.johnny.swapub.data.remote.SwapubRepository
import com.johnny.swapub.home.HomeTypeFilter
import com.johnny.swapub.home.item.HomeItemViewModel

@Suppress("UNCHECKED_CAST")
class HomeItemViewModelFactory(
        private val swapubRepository: SwapubRepository,
        private val homeTypeFilter: HomeTypeFilter
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>) =
            with(modelClass) {
                when {
                    isAssignableFrom(HomeItemViewModel::class.java) ->
                        HomeItemViewModel(homeTypeFilter, swapubRepository)
                    else ->
                        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            } as T
}
