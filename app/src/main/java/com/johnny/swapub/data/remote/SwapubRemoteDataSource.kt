package com.johnny.swapub.data.remote

import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.johnny.swapub.R
import com.johnny.swapub.SwapubApplication
import com.johnny.swapub.data.*
import com.johnny.swapub.data.remote.SwapubDataSource
import com.johnny.swapub.util.Logger
import com.johnny.swapub.util.UserManager
import com.johnny.swapub.util.UserManager.hasToken
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object SwapubRemoteDataSource: SwapubDataSource {

    private const val PATH_PRODUCT = "product"
    private const val PATH_MESSAGE = "message"
    private const val PATH_USER = "user"
    private const val PATH_CHAT_ROOM = "chatRoom"



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

    override fun getMessageHistory(): MutableLiveData<List<ChatRoom>> {
        val liveData = MutableLiveData<List<ChatRoom>>()
        FirebaseFirestore.getInstance()
            .collection(PATH_CHAT_ROOM)
            .orderBy("time", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->
                Logger.i("addSnapshotListener detect")

                exception?.let {
                    Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                }

                val list = mutableListOf<ChatRoom>()
                for (document in snapshot!!) {
                    Logger.d(document.id + " => " + document.data)
                    val chatRoom = document.toObject(ChatRoom::class.java)
                    list.add(chatRoom)
                    Logger.d("$list")
                }

                liveData.value = list
            }
        return liveData
    }


    override fun getMessage(documentId: String): MutableLiveData<List<Message>> {
        val liveData = MutableLiveData<List<Message>>()
        FirebaseFirestore.getInstance()
            .collection(PATH_CHAT_ROOM)
            .document(documentId)
            .collection(PATH_MESSAGE)
            .orderBy("time", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->
                Logger.i("addSnapshotListener detect")

                exception?.let {
                    Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                }

                val list = mutableListOf<Message>()
                for (document in snapshot!!) {
                    Logger.d(document.id + " => " + document.data)
                    val chatRoom = document.toObject(Message::class.java)
                    list.add(chatRoom)
                }

                liveData.value = list
                Logger.d("ggggg$list")
            }
        return liveData
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


    override suspend fun getUserFavor(userL: String): Result<List<String>> = suspendCoroutine{ continuation ->
        FirebaseFirestore.getInstance()
            .collection(PATH_USER).whereEqualTo("id", userL)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var userFavorList = listOf<String>()
                    for (document in task.result!!) {
                        Logger.d(document.id + " => " + document.data)

                        val user = document.toObject(User::class.java)
                        Logger.d("880088${user.name}")
                        if (user.favoriteList != null) {
                            userFavorList = user.favoriteList
                        }
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

    override suspend fun updateProductToFavorList(productId: String, favoriteList: MutableList<String>): Result<Boolean> = suspendCoroutine {
            continuation ->
        val addProductToFavorList = FirebaseFirestore.getInstance().collection(PATH_USER).document(UserManager.userId)
        addProductToFavorList
            .update("favoriteList", favoriteList)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Logger.i("Swapub: $productId")
                    continuation.resume(Result.Success(true))
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


    override suspend fun addUserToFirebase(user: User): Result<Boolean> =
        suspendCoroutine { continuation ->
            var userCollection = FirebaseFirestore.getInstance().collection(PATH_USER)
            var document = userCollection.document(user.id)


            userCollection
                .whereEqualTo("id", UserManager.userId)
                .get()
                .addOnSuccessListener {it ->
                    if(it.isEmpty){
                        if(document != null) {

                            document
                                .set(user)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Log.d("addUserToFirebase", "addUserToFirebase: $user")
                                        continuation.resume(Result.Success(true))
                                        if(hasToken()){

                                        }
                                    } else {
                                        task.exception?.let {
                                            Log.d(
                                                "add_user_exception",
                                                "[${this::class.simpleName}] Error getting documents. ${it.message}"
                                            )
                                            continuation.resume(Result.Error(it))
                                            return@addOnCompleteListener
                                        }
                                        continuation.resume(Result.Fail(SwapubApplication.instance.getString(R.string.error)))
                                    }
                                }
                        }
                    }
                }
        }

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun postMessage(message: Message, document: String): Result<Boolean> =
        suspendCoroutine { continuation ->

            val messages =
                FirebaseFirestore.getInstance()
                    .collection(PATH_CHAT_ROOM)
                    .document(document)
                    .collection(PATH_MESSAGE)

            val updateMessages = FirebaseFirestore.getInstance()
                .collection(PATH_CHAT_ROOM)
                .document(document)

            val document = messages
                .document()

            message.time = Calendar.getInstance().timeInMillis

            updateMessages
                .update("text", message.text , "time", message.time)

            document
                .set(message)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Logger.i("PhoneAuction: $message")
                        continuation.resume(Result.Success(true))
                    } else {
                        task.exception?.let {
                            Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(
                            Result.Fail(
                                SwapubApplication.instance.getString(
                                    R.string.you_know_nothing
                                )
                            )
                        )
                    }
                }
        }


}

