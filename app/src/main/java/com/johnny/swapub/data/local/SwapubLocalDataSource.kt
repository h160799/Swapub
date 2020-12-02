package com.johnny.swapub.data.local

import android.content.Context
import com.johnny.swapub.data.ChatRoom
import com.johnny.swapub.data.Product
import com.johnny.swapub.data.Result
import com.johnny.swapub.data.User
import com.johnny.swapub.data.remote.SwapubDataSource

class SwapubLocalDataSource(val context: Context) : SwapubDataSource {
    override suspend fun getProduct(): Result<List<Product>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMessage(): Result<List<ChatRoom>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserDetail(product: Product): Result<User>{
        TODO("Not yet implemented")
    }


}
