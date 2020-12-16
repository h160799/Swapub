package com.johnny.swapub.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.johnny.swapub.data.Product
import com.johnny.swapub.data.remote.SwapubRepository
import com.johnny.swapub.product.ProductViewModel
import com.johnny.swapub.profile.ProfileViewModel

@Suppress("UNCHECKED_CAST")
class ProductViewModelFactory(
    private val swapubRepository: SwapubRepository,
    private val product: Product
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(ProductViewModel::class.java) ->
                    ProductViewModel(swapubRepository, product)


                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}

