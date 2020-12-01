package com.johnny.swapub.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.johnny.swapub.data.User
import com.johnny.swapub.data.remote.SwapubRepository
import com.johnny.swapub.messageHistory.MessageHistoryViewModel

@Suppress("UNCHECKED_CAST")
class MessageHistoryViewModelFactory(
    private val swapubRepository: SwapubRepository,
    private val user: User?
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MessageHistoryViewModel::class.java)) {
            return MessageHistoryViewModel(swapubRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}