package com.johnny.swapub.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.johnny.swapub.MainViewModel
import com.johnny.swapub.SignInViewModel
import com.johnny.swapub.addToFavorite.AddToFavoriteViewModel
import com.johnny.swapub.data.remote.SwapubRepository
import com.johnny.swapub.messageHistory.MessageHistoryViewModel
import com.johnny.swapub.myClub.clubArtWork.ClubArtWorkViewModel
import com.johnny.swapub.myClub.clubBookShare.ClubBookShareViewModel
import com.johnny.swapub.myClub.clubElectronicProduct.ClubElectronicProductViewModel
import com.johnny.swapub.myClub.clubFashion.ClubFashionViewModel
import com.johnny.swapub.myClub.clubLiveLife.ClubLiveLifeViewModel
import com.johnny.swapub.myClub.clubMakeup.ClubMakeupViewModel
import com.johnny.swapub.myClub.clubMenClothes.ClubMenClothesViewModel
import com.johnny.swapub.myClub.clubPlant.ClubPlantViewModel
import com.johnny.swapub.myFavorite.MyFavoriteViewModel
import com.johnny.swapub.myTrading.MyTradingViewModel
import com.johnny.swapub.myTrading.tradingPost.TradingPostViewModel
import com.johnny.swapub.profile.ProfileViewModel
import com.johnny.swapub.profile.makeWishes.MakeWishesViewModel
import com.johnny.swapub.search.SearchViewModel
import com.johnny.swapub.setting.SettingViewModel
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

                isAssignableFrom(MyFavoriteViewModel::class.java) ->
                    MyFavoriteViewModel(swapubRepository)

                isAssignableFrom(AddToFavoriteViewModel::class.java) ->
                    AddToFavoriteViewModel(swapubRepository)

                isAssignableFrom(MessageHistoryViewModel::class.java) ->
                    MessageHistoryViewModel(swapubRepository)

                isAssignableFrom(TradingPostViewModel::class.java) ->
                    TradingPostViewModel(swapubRepository)

                isAssignableFrom(MyTradingViewModel::class.java) ->
                MyTradingViewModel(swapubRepository)

                isAssignableFrom(MakeWishesViewModel::class.java) ->
                    MakeWishesViewModel(swapubRepository)

                isAssignableFrom(ProfileViewModel::class.java) ->
                    ProfileViewModel(swapubRepository)

                isAssignableFrom(SearchViewModel::class.java) ->
                    SearchViewModel(swapubRepository)

                isAssignableFrom(SettingViewModel::class.java) ->
                    SettingViewModel(swapubRepository)

                isAssignableFrom(ClubArtWorkViewModel::class.java) ->
                    ClubArtWorkViewModel(swapubRepository)

                isAssignableFrom(ClubBookShareViewModel::class.java) ->
                    ClubBookShareViewModel(swapubRepository)

                isAssignableFrom(ClubElectronicProductViewModel::class.java) ->
                    ClubElectronicProductViewModel(swapubRepository)

                isAssignableFrom(ClubFashionViewModel::class.java) ->
                    ClubFashionViewModel(swapubRepository)

                isAssignableFrom(ClubLiveLifeViewModel::class.java) ->
                    ClubLiveLifeViewModel(swapubRepository)

                isAssignableFrom(ClubMakeupViewModel::class.java) ->
                    ClubMakeupViewModel(swapubRepository)

                isAssignableFrom(ClubMenClothesViewModel::class.java) ->
                    ClubMenClothesViewModel(swapubRepository)

                isAssignableFrom(ClubPlantViewModel::class.java) ->
                    ClubPlantViewModel(swapubRepository)






                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
