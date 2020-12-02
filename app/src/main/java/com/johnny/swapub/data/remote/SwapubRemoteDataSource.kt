package com.johnny.swapub.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.johnny.swapub.R
import com.johnny.swapub.SwapubApplication
import com.johnny.swapub.data.ChatRoom
import com.johnny.swapub.data.Product
import com.johnny.swapub.data.Result
import com.johnny.swapub.data.remote.SwapubDataSource
import com.johnny.swapub.util.Logger
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object SwapubRemoteDataSource: SwapubDataSource {

    private const val PATH_PORDUCT = "product"
    private const val PATH_CHATROOM = "chatRoom"

    override suspend fun getProduct(): Result<List<Product>> = suspendCoroutine{ continuation ->
        FirebaseFirestore.getInstance()
            .collection(PATH_PORDUCT)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<Product>()
                    for (document in task.result!!) {
                        Logger.d(document.id + " => " + document.data)

                        val product = document.toObject(Product::class.java)
                        list.add(product)
                    }
                    continuation.resume(Result.Success(list))
                } else {
                    task.exception?.let {
                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(SwapubApplication.instance.getString(R.string.error)))
                }
            }
    }

    override suspend fun getMessage(): Result<List<ChatRoom>> = suspendCoroutine{ continuation ->
        FirebaseFirestore.getInstance()
            .collection(PATH_CHATROOM)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<ChatRoom>()
                    for (document in task.result!!) {
                        Logger.d(document.id + " => " + document.data)

                        val chatRoom = document.toObject(ChatRoom::class.java)
                        list.add(chatRoom)
                    }
                    continuation.resume(Result.Success(list))
                } else {
                    task.exception?.let {
                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(SwapubApplication.instance.getString(R.string.error)))
                }
            }
    }
}
