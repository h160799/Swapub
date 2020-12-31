package com.johnny.swapub.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.johnny.swapub.MainViewModel
import com.johnny.swapub.SignInViewModel
import com.johnny.swapub.addToFavorite.AddToFavoriteViewModel
import com.johnny.swapub.data.remote.SwapubRepository
import com.johnny.swapub.messageHistory.MessageHistoryViewModel
import com.johnny.swapub.club.clubArtWork.ClubArtWorkViewModel
import com.johnny.swapub.club.clubBookShare.ClubBookShareViewModel
import com.johnny.swapub.club.clubElectronicProduct.ClubElectronicProductViewModel
import com.johnny.swapub.club.clubFashion.ClubFashionViewModel
import com.johnny.swapub.club.clubLiveLife.ClubLiveLifeViewModel
import com.johnny.swapub.club.clubMakeup.ClubMakeupViewModel
import com.johnny.swapub.club.clubMenClothes.ClubMenClothesViewModel
import com.johnny.swapub.club.clubPlant.ClubPlantViewModel
import com.johnny.swapub.club.clubSporty.ClubSportyViewModel
import com.johnny.swapub.club.clubVideoGame.ClubVideoGameViewModel
import com.johnny.swapub.club.clubVolunteer.ClubVolunteerViewModel
import com.johnny.swapub.club.clubWomenClothes.ClubWomenClothesViewModel
import com.johnny.swapub.myClub.MyClubViewModel
import com.johnny.swapub.myFavorite.MyFavoriteViewModel
import com.johnny.swapub.myTrading.MyTradingViewModel
import com.johnny.swapub.myTrading.tradingPost.TradingPostViewModel
import com.johnny.swapub.privacyPolicy.PrivacyPolicyViewModel
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

                    isAssignableFrom(PrivacyPolicyViewModel::class.java) ->
                        PrivacyPolicyViewModel(swapubRepository)

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

                    isAssignableFrom(ClubSportyViewModel::class.java) ->
                        ClubSportyViewModel(swapubRepository)

                    isAssignableFrom(ClubVolunteerViewModel::class.java) ->
                        ClubVolunteerViewModel(swapubRepository)

                    isAssignableFrom(ClubWomenClothesViewModel::class.java) ->
                        ClubWomenClothesViewModel(swapubRepository)

                    isAssignableFrom(ClubVideoGameViewModel::class.java) ->
                        ClubVideoGameViewModel(swapubRepository)

                    isAssignableFrom(MyClubViewModel::class.java) ->
                        MyClubViewModel(swapubRepository)

                    else ->
                        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            } as T
}
