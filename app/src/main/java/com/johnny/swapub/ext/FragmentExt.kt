package com.johnny.swapub.ext

import androidx.fragment.app.Fragment
import com.johnny.swapub.SwapubApplication
import com.johnny.swapub.data.ChatRoom
import com.johnny.swapub.data.Product
import com.johnny.swapub.data.User
import com.johnny.swapub.factory.*
import com.johnny.swapub.home.HomeTypeFilter

fun Fragment.getVmFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as SwapubApplication).swapubRepository
    return ViewModelFactory(repository)
}
fun Fragment.getVmFactory(homeTypeFilter: HomeTypeFilter): HomeItemViewModelFactory {
    val repository = (requireContext().applicationContext as SwapubApplication).swapubRepository
    return HomeItemViewModelFactory(repository, homeTypeFilter)
}

fun Fragment.getVmFactory(product: Product): ProductViewModelFactory {
    val repository = (requireContext().applicationContext as SwapubApplication).swapubRepository
    return ProductViewModelFactory(repository,product )
}

fun Fragment.getVmFactory(chatRoom: ChatRoom): ChatRoomViewModelFactory {
    val repository = (requireContext().applicationContext as SwapubApplication).swapubRepository
    return ChatRoomViewModelFactory(repository,chatRoom )
}



//fun Fragment.getVmFactory(user: User): UserViewModelFactory {
//    val repository = (requireContext().applicationContext as SwapubApplication).swapubRepository
//    return UserViewModelFactory(repository,user )
//}



