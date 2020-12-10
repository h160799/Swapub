package com.johnny.swapub.data.remote

import androidx.lifecycle.MutableLiveData
import com.johnny.swapub.data.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DefaultSwapubRepository(private val swapubRemoteDataSource: SwapubDataSource,
                              private val swapubLocalDataSource: SwapubDataSource,
                              private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SwapubRepository {
    override suspend fun getProduct(): Result<List<Product>> {
        return swapubRemoteDataSource.getProduct()
    }

    override fun getMessage(documentId: String): MutableLiveData<List<Message>> {
        return swapubRemoteDataSource.getMessage(documentId)
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

    override suspend fun addUserToFirebase(user: User): Result<Boolean>  {
        return swapubRemoteDataSource.addUserToFirebase(user)
    }

    override fun getMessageHistory(): MutableLiveData<List<ChatRoom>> {
        return swapubRemoteDataSource.getMessageHistory()
    }
    override suspend fun postMessage(message: Message, document: String):Result<Boolean> {
        return swapubRemoteDataSource.postMessage(message, document)
    }
}
