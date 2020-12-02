package com.johnny.swapub.data.remote

import com.johnny.swapub.data.ChatRoom
import com.johnny.swapub.data.Product
import com.johnny.swapub.data.Result
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


}