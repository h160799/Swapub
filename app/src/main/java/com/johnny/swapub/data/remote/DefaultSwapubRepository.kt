package com.johnny.swapub.data.remote

import com.johnny.swapub.data.ChatRoom
import com.johnny.swapub.data.Product
import com.johnny.swapub.data.Result
import com.johnny.swapub.data.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DefaultSwapubRepository(private val swapubRemoteDataSource: SwapubDataSource,
                              private val swapubLocalDataSource: SwapubDataSource,
                              private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SwapubRepository {
    override suspend fun getProduct(): Result<List<Product>> {
        return swapubRemoteDataSource.getProduct()
    }

    override suspend fun getMessage(): Result<List<ChatRoom>> {
        return swapubRemoteDataSource.getMessage()
    }

    override suspend fun getUserDetail(product: Product): Result<User>{
        return swapubRemoteDataSource.getUserDetail(product)
    }

    override suspend fun getUserFavor(userL: String): Result<List<String>> {
        return swapubRemoteDataSource.getUserFavor(userL)
    }


    override suspend fun getFavoriteList(userL: String): Result<User> {
        return swapubRemoteDataSource.getFavoriteList(userL)
    }

    override suspend fun getFavoriteProduct(productId: List<String>): Result<List<Product>> {
        return swapubRemoteDataSource.getFavoriteProduct(productId)
    }

    override suspend fun updateProductToFavorList(productId: String, favoriteList: MutableList<String>): Result<Boolean> {
        return swapubRemoteDataSource.updateProductToFavorList(productId, favoriteList)
    }
}
