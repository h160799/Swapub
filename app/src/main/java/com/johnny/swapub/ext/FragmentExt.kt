package com.johnny.swapub.ext

import androidx.fragment.app.Fragment
import com.johnny.swapub.SwapubApplication
import com.johnny.swapub.factory.ViewModelFactory

fun Fragment.getVmFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as SwapubApplication).swapubRepository
    return ViewModelFactory(repository)
}


