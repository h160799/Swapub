package com.johnny.swapub.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.johnny.swapub.MainViewModel
import com.johnny.swapub.data.remote.SwapubRepository
import com.johnny.swapub.home.item.HomeItemViewModel
import com.johnny.swapub.messageHistory.MessageHistoryViewModel
import com.johnny.swapub.wishNews.WishNewsViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val swapubRepository: SwapubRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel(swapubRepository)

                isAssignableFrom(MessageHistoryViewModel::class.java) ->
                   MessageHistoryViewModel(swapubRepository)

                isAssignableFrom(WishNewsViewModel::class.java) ->
                   WishNewsViewModel(swapubRepository)

                isAssignableFrom(HomeItemViewModel::class.java) ->
                    HomeItemViewModel(swapubRepository)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
