package com.johnny.swapub.data.remote

import com.johnny.swapub.data.ChatRoom
import com.johnny.swapub.data.Product
import com.johnny.swapub.data.Result

interface SwapubRepository {
    suspend fun getProduct(): Result<List<Product>>

    suspend fun getMessage(): Result<List<ChatRoom>>
}