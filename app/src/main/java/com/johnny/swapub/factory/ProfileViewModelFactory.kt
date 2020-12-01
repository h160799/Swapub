package com.johnny.swapub.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.johnny.swapub.data.User
import com.johnny.swapub.data.remote.SwapubRepository
import com.johnny.swapub.profile.ProfileViewModel

@Suppress("UNCHECKED_CAST")
class ProfileViewModelFactory(
    private val swapubRepository: SwapubRepository,
    private val user: User?
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(swapubRepository, user) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}