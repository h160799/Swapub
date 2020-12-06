package com.johnny.swapub.data.remote

import com.johnny.swapub.data.ChatRoom
import com.johnny.swapub.data.Product
import com.johnny.swapub.data.Result
import com.johnny.swapub.data.User

interface SwapubDataSource {
    suspend fun getProduct(): Result<List<Product>>

    suspend fun getMessage(): Result<List<ChatRoom>>

    suspend fun getUserDetail(product: Product): Result<User>

    suspend fun getUserFavor(userL: String): Result<List<String>>

    suspend fun getFavoriteList(userL: String): Result<User>

    suspend fun getFavoriteProduct(productId: List<String>): Result<List<Product>>

    suspend fun updateProductToFavorList(productId: String, favoriteList: MutableList<String>): Result<Boolean>



}