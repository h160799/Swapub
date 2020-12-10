package com.johnny.swapub.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.johnny.swapub.data.ChatRoom
import com.johnny.swapub.data.remote.SwapubRepository
import com.johnny.swapub.messageHistory.MessageHistoryViewModel
import com.johnny.swapub.messageHistory.conversation.ConversationViewModel

@Suppress("UNCHECKED_CAST")
class ConversationViewModelFactory(
    private val swapubRepository: SwapubRepository,
    private val chatRoom: ChatRoom,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(ConversationViewModel::class.java) ->
                    ConversationViewModel(swapubRepository,chatRoom)


                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
