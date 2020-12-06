package com.johnny.swapub.data.remote

import android.util.Log
import androidx.annotation.NonNull
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.johnny.swapub.R
import com.johnny.swapub.SwapubApplication
import com.johnny.swapub.data.*
import com.johnny.swapub.data.remote.SwapubDataSource
import com.johnny.swapub.util.Logger
import com.johnny.swapub.util.UserManager
import com.johnny.swapub.util.UserManager.user
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object SwapubRemoteDataSource: SwapubDataSource {

    private const val PATH_PRODUCT = "product"
    private const val PATH_CHATROOM = "chatRoom"
    private const val PATH_USER = "user"


    override suspend fun getProduct(): Result<List<Product>> = suspendCoroutine{ continuation ->
        FirebaseFirestore.getInstance()
            .collection(PATH_PRODUCT)
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

    override suspend fun getUserDetail(product: Product): Result<User> = suspendCoroutine{ continuation ->
        Log.d("jjj", " ${product.user}")
        FirebaseFirestore.getInstance()
            .collection(PATH_USER).whereEqualTo("id", product.user)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var user = User()

                    for (document in task.result!!) {
                        Logger.d(document.id + " => " + document.data)
                        Log.d("sss","${document.data}")

//                        user = document.toObject(User::class.java)
                        user = task.result!!.documents[0].toObject(User::class.java)!!
                    }
                    continuation.resume(Result.Success(data = user))
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


    override suspend fun getUserFavor(userL: User): Result<List<String>> = suspendCoroutine{ continuation ->
        FirebaseFirestore.getInstance()
            .collection(PATH_USER).whereEqualTo("id", userL.id)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
        var userFavorList = mutableListOf<String>()
                    for (document in task.result!!) {
                        Logger.d(document.id + " => " + document.data)

                        val user = document.toObject(User::class.java)
                        userFavorList.add(user.toString())
                    }
                    continuation.resume(Result.Success(userFavorList))
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



    override suspend fun getFavoriteList(userL: String): Result<User>  = suspendCoroutine{ continuation ->
        FirebaseFirestore.getInstance()
            .collection(PATH_USER)
            .whereEqualTo("id", userL)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var getUser = User()
                    for (document in task.result!!) {
                        Logger.d(document.id + " => " + document.data)

                        val user = document.toObject(User::class.java)
                        getUser = user
                    }
                    continuation.resume(Result.Success(getUser))
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

    override suspend fun getFavoriteProduct(productIds: List<String>): Result<List<Product>> = suspendCoroutine{ continuation ->
        FirebaseFirestore.getInstance()
            .collection(PATH_PRODUCT)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<Product>()
                    for (document in task.result!!) {
                        Logger.d(document.id + " => " + document.data)

                        val product = document.toObject(Product::class.java)
                        for (productId in productIds) {
                            if (product.id == productId)
                                list.add(product)
                        }
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
