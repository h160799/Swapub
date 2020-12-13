package com.johnny.swapub.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.johnny.swapub.data.ChatRoom
import com.johnny.swapub.data.remote.SwapubRepository
import com.johnny.swapub.messageHistory.conversation.tradingStyle.TradingStyleViewModel
import com.johnny.swapub.messageHistory.conversation.ConversationViewModel
import com.johnny.swapub.messageHistory.conversation.tradingSuccessOrNot.TradingSuccessorNotViewModel

@Suppress("UNCHECKED_CAST")
class ChatRoomViewModelFactory(
    private val swapubRepository: SwapubRepository,
    private val chatRoom: ChatRoom,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(ConversationViewModel::class.java) ->
                    ConversationViewModel(swapubRepository,chatRoom)

                isAssignableFrom(TradingStyleViewModel::class.java) ->
                    TradingStyleViewModel(swapubRepository,chatRoom)

                isAssignableFrom(TradingSuccessorNotViewModel::class.java) ->
                    TradingSuccessorNotViewModel(swapubRepository,chatRoom)


                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
