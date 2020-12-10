package com.johnny.swapub.data.remote

import androidx.lifecycle.MutableLiveData
import com.johnny.swapub.data.*

interface SwapubRepository {
    suspend fun getProduct(): Result<List<Product>>

    fun getMessage(documentId: String): MutableLiveData<List<Message>>

    suspend fun getUserDetail(product: Product): Result<User>

    suspend fun getUserFavor(userL: String): Result<List<String>>

    suspend fun getFavoriteList(userL: String): Result<User>

    suspend fun getFavoriteProduct(productId: List<String>): Result<List<Product>>

    suspend fun updateProductToFavorList(productId: String, favoriteList: MutableList<String>): Result<Boolean>

    suspend fun addUserToFirebase(user: User): Result<Boolean>

    fun getMessageHistory(): MutableLiveData<List<ChatRoom>>

    suspend fun postMessage(message: Message, document: String): Result<Boolean>


}