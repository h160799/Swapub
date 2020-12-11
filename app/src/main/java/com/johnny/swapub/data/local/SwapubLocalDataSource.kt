package com.johnny.swapub.data.local

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.johnny.swapub.data.*
import com.johnny.swapub.data.remote.SwapubDataSource

class SwapubLocalDataSource(val context: Context) : SwapubDataSource {
    override suspend fun getProduct(): Result<List<Product>> {
        TODO("Not yet implemented")
    }

    override suspend fun getOneProduct(productId: String): Result<Product> {
        TODO("Not yet implemented")
    }

    override fun getMessage(documentId: String): MutableLiveData<List<Message>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserDetail(product: Product): Result<User>{
        TODO("Not yet implemented")
    }

    override suspend fun getUserFavor(userL: String): Result<List<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteList(userL: String): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteProduct(productId: List<String>): Result<List<Product>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateProductToFavorList(productId: String, favoriteList: MutableList<String>): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun addUserToFirebase(user: User): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override fun getMessageHistory(): MutableLiveData<List<ChatRoom>> {
        TODO("Not yet implemented")
    }

    override suspend fun postMessage(message: Message, document: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun postInterestMessage(chatRoom: ChatRoom): Result<Boolean> {
        TODO("Not yet implemented")
    }


}
