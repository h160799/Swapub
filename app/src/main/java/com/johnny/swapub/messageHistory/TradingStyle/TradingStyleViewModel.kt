package com.johnny.swapub.messageHistory.TradingStyle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.johnny.swapub.data.ChatRoom
import com.johnny.swapub.data.remote.SwapubRepository

class TradingStyleViewModel(
    val swapubRepository: SwapubRepository,
    val arguments: ChatRoom
): ViewModel() {

     val chatRoom = arguments



}