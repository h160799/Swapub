package com.johnny.swapub.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.johnny.swapub.MainViewModel
import com.johnny.swapub.SignInViewModel
import com.johnny.swapub.addToFavorite.AddToFavoriteViewModel
import com.johnny.swapub.data.remote.SwapubRepository
import com.johnny.swapub.messageHistory.MessageHistoryViewModel
import com.johnny.swapub.messageHistory.conversation.ConversationViewModel
import com.johnny.swapub.myFavorite.MyFavoriteViewModel
import com.johnny.swapub.myTrading.tradingPost.TradingPostViewModel
import com.johnny.swapub.profile.ProfileViewModel
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

                isAssignableFrom(SignInViewModel::class.java) ->
                    SignInViewModel(swapubRepository)


                isAssignableFrom(WishNewsViewModel::class.java) ->
                   WishNewsViewModel(swapubRepository)

                isAssignableFrom(ProfileViewModel::class.java) ->
                    WishNewsViewModel(swapubRepository)

                isAssignableFrom(MyFavoriteViewModel::class.java) ->
                    MyFavoriteViewModel(swapubRepository)

                isAssignableFrom(AddToFavoriteViewModel::class.java) ->
                    AddToFavoriteViewModel(swapubRepository)

                isAssignableFrom(MessageHistoryViewModel::class.java) ->
                    MessageHistoryViewModel(swapubRepository)

                isAssignableFrom(TradingPostViewModel::class.java) ->
                    TradingPostViewModel(swapubRepository)



                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
