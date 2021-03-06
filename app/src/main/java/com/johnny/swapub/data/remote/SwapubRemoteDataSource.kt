package com.johnny.swapub.data.remote

import android.icu.util.Calendar
import android.media.Image
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.johnny.swapub.R
import com.johnny.swapub.SwapubApplication
import com.johnny.swapub.data.*
import com.johnny.swapub.util.Logger
import com.johnny.swapub.util.UserManager
import com.johnny.swapub.util.UserManager.hasToken
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object SwapubRemoteDataSource : SwapubDataSource {

    private const val PATH_PRODUCT = "product"
    private const val PATH_MESSAGE = "message"
    private const val PATH_USER = "user"
    private const val PATH_CHAT_ROOM = "chatRoom"
    private const val PATH_TRADING_TYPE = "tradingType"
    private const val PATH_CLUB = "club"

    override suspend fun getUserInfo(userId: String): Result<User> =
            suspendCoroutine { continuation ->
                FirebaseFirestore.getInstance()
                        .collection(PATH_USER)
                        .whereEqualTo("id", userId)
                        .get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                var user = User()
                                for (document in task.result!!) {
                                    Logger.d(document.id + " => " + document.data)

                                    user = document.toObject(User::class.java)
                                }
                                continuation.resume(Result.Success(user))

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

    override suspend fun getProduct(): Result<List<Product>> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
                .collection(PATH_PRODUCT)
                .whereEqualTo("tradable", false)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<Product>()
                        for (document in task.result!!) {
                            Logger.d(document.id + " => " + document.data)

                            val product = document.toObject(Product::class.java)
                            list.add(product)
                            list.sortByDescending { it.time }
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

    override suspend fun getProductWithPlace(): Result<List<Product>> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
                .collection(PATH_PRODUCT)
                .whereEqualTo("tradable", false)
                .whereEqualTo("location", UserManager.user.place)
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

    override suspend fun getOneProduct(productId: String): Result<Product> =
            suspendCoroutine { continuation ->
                FirebaseFirestore.getInstance()
                        .collection(PATH_PRODUCT)
                        .whereEqualTo("id", productId)
                        .get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                var product = Product()
                                for (document in task.result!!) {
                                    Logger.d(document.id + " => " + document.data)

                                    product = document.toObject(Product::class.java)
                                }
                                continuation.resume(Result.Success(product))
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
                }
        return liveData
    }

    override suspend fun getUserDetail(product: Product): Result<User> =
            suspendCoroutine { continuation ->
                FirebaseFirestore.getInstance()
                        .collection(PATH_USER)
                        .whereEqualTo("id", product.user)
                        .get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                var user = User()

                                for (document in task.result!!) {
                                    Logger.d(document.id + " => " + document.data)

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

    override suspend fun getUserFavor(userL: String): Result<List<String>> =
            suspendCoroutine { continuation ->
                FirebaseFirestore.getInstance()
                        .collection(PATH_USER)
                        .whereEqualTo("id", userL)
                        .get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                var userFavorList = listOf<String>()
                                for (document in task.result!!) {
                                    Logger.d(document.id + " => " + document.data)

                                    val user = document.toObject(User::class.java)
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


    override suspend fun getFavoriteList(userL: String): Result<User> =
            suspendCoroutine { continuation ->
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

    override suspend fun getFavoriteProduct(productIds: List<String>): Result<List<Product>> =
            suspendCoroutine { continuation ->
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

    override suspend fun updateProductToFavorList(
            productId: String,
            favoriteList: MutableList<String>
    ): Result<Boolean> =
            suspendCoroutine { continuation ->
                val addProductToFavorList = FirebaseFirestore.getInstance()
                        .collection(PATH_USER)
                        .document(UserManager.userId)
                addProductToFavorList
                        .update("favoriteList", favoriteList)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
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
                        .addOnSuccessListener { it ->
                            if (it.isEmpty) {
                                if (document != null) {

                                    document
                                            .set(user)
                                            .addOnCompleteListener { task ->
                                                if (task.isSuccessful) {
                                                    Log.d("addUserToFirebase", "addUserToFirebase: $user")
                                                    continuation.resume(Result.Success(true))
                                                    if (hasToken()) {

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
                                                    continuation.resume(
                                                            Result.Fail(
                                                                    SwapubApplication.instance.getString(
                                                                            R.string.error
                                                                    )
                                                            )
                                                    )
                                                }
                                            }
                                }
                            }
                        }
            }

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun postInterestMessage(chatRoom: ChatRoom, user: User): Result<Boolean> =
            suspendCoroutine { continuation ->
                val chatRooms = FirebaseFirestore.getInstance()
                        .collection(PATH_CHAT_ROOM)

                chatRoom.time = Calendar.getInstance().timeInMillis

                chatRooms
                        .whereEqualTo("senderId", UserManager.userId)
                        .whereEqualTo("senderName", user.name)
                        .whereEqualTo("senderImage", user.image)
                        .whereEqualTo("productId", chatRoom.productId)
                        .get()
                        .addOnSuccessListener {
                            if (it.isEmpty) {
                                chatRooms
                                        .add(chatRoom)
                                        .addOnSuccessListener { documentReference ->
                                            documentReference.update("id", documentReference.id)
                                        }
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
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

                            } else {
                            }
                        }
            }

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun postMessage(message: Message, chatRoomId: String): Result<Boolean> =
            suspendCoroutine { continuation ->
                val messages =
                        FirebaseFirestore.getInstance()
                                .collection(PATH_CHAT_ROOM)
                                .document(chatRoomId)
                                .collection(PATH_MESSAGE)

                val updateMessages = FirebaseFirestore.getInstance()
                        .collection(PATH_CHAT_ROOM)
                        .document(chatRoomId)

                val document = messages
                        .document()

                message.time = Calendar.getInstance().timeInMillis

                updateMessages
                        .update("text", message.text, "time", message.time)

                document
                        .set(message)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
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

    override suspend fun getAddedChatRoom(chatRoom: ChatRoom): Result<ChatRoom> =
            suspendCoroutine { continuation ->
                FirebaseFirestore.getInstance()
                        .collection(PATH_CHAT_ROOM)
                        .whereEqualTo("senderId", chatRoom.senderId)
                        .whereEqualTo("productId", chatRoom.productId)
                        .get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {

                                for (document in task.result!!) {
                                    Logger.d(document.id + " => " + document.data)

                                    val chatRoom = document.toObject(ChatRoom::class.java)
                                    continuation.resume(Result.Success(chatRoom))
                                }
                            } else {
                                task.exception?.let {
                                    Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                                    continuation.resume(Result.Error(it))
                                    return@addOnCompleteListener
                                }
                                continuation.resume(
                                        Result.Fail(
                                                SwapubApplication.instance.getString(
                                                        R.string.error
                                                )
                                        )
                                )
                            }
                        }
            }

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun postTradingType(
            chatRoomId: String,
            tradingType: TradingType
    ): Result<Boolean> =
            suspendCoroutine { continuation ->

                FirebaseFirestore.getInstance()
                        .collection(PATH_TRADING_TYPE)
                        .document(chatRoomId)
                        .set(tradingType)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
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

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun updateTradingSelect(
            chatRoomId: String,
            tradingSelect: Boolean
    ): Result<Boolean> =
            suspendCoroutine { continuation ->

                FirebaseFirestore.getInstance()
                        .collection(PATH_CHAT_ROOM)
                        .document(chatRoomId)
                        .update("tradingSelect", tradingSelect)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
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

    override suspend fun getTradingType(chatRoomId: String): Result<TradingType> =
            suspendCoroutine { continuation ->
                FirebaseFirestore.getInstance()
                        .collection(PATH_TRADING_TYPE)
                        .whereEqualTo("id", chatRoomId)
                        .get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                for (document in task.result!!) {
                                    val tradingType = document.toObject(TradingType::class.java)
                                    continuation.resume(Result.Success(tradingType))
                                }
                            } else {
                                task.exception?.let {
                                    Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                                    continuation.resume(Result.Error(it))
                                    return@addOnCompleteListener
                                }
                                continuation.resume(
                                        Result.Fail(
                                                SwapubApplication.instance.getString(
                                                        R.string.error
                                                )
                                        )
                                )
                            }
                        }
            }

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun updateProductTradable(
            productId: String,
            tradable: Boolean
    ): Result<Boolean> = suspendCoroutine { continuation ->

        FirebaseFirestore.getInstance()
                .collection(PATH_PRODUCT)
                .document(productId)
                .update("tradable", tradable)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
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

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun deleteTradingType(chatRoomId: String): Result<Boolean> =
            suspendCoroutine { continuation ->
                FirebaseFirestore.getInstance()
                        .collection(PATH_TRADING_TYPE)
                        .document(chatRoomId)
                        .delete()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
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

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun postTradingInfo(product: Product): Result<Boolean> =
            suspendCoroutine { continuation ->
                FirebaseFirestore.getInstance()
                        .collection(PATH_PRODUCT)
                        .add(product)
                        .addOnSuccessListener { documentReference ->
                            documentReference.update("id", documentReference.id)
                        }
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
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

    override suspend fun getPostProduct(userId: String): Result<List<Product>> =
            suspendCoroutine { continuation ->
                FirebaseFirestore.getInstance()
                        .collection(PATH_PRODUCT)
                        .whereEqualTo("user", userId)
                        .get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val list = mutableListOf<Product>()
                                for (document in task.result!!) {
                                    val product = document.toObject(Product::class.java)
                                    list.add(product)
                                    list.sortByDescending { it.time }
                                }
                                continuation.resume(Result.Success(list))

                            } else {
                                task.exception?.let {
                                    Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                                    continuation.resume(Result.Error(it))
                                    return@addOnCompleteListener
                                }
                                continuation.resume(
                                        Result.Fail(
                                                SwapubApplication.instance.getString(
                                                        R.string.error
                                                )
                                        )
                                )
                            }
                        }
            }

    override suspend fun getWishContent(userId: String): Result<List<Product>> =
            suspendCoroutine { continuation ->
                FirebaseFirestore.getInstance()
                        .collection(PATH_PRODUCT)
                        .whereEqualTo("user", userId)
                        .whereEqualTo("wishable", true)
                        .get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val list = mutableListOf<Product>()
                                for (document in task.result!!) {
                                    val product = document.toObject(Product::class.java)
                                    list.add(product)
                                    list.sortByDescending { it.time }
                                }
                                continuation.resume(Result.Success(list))
                            } else {
                                task.exception?.let {
                                    Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                                    continuation.resume(Result.Error(it))
                                    return@addOnCompleteListener
                                }
                                continuation.resume(
                                        Result.Fail(
                                                SwapubApplication.instance.getString(
                                                        R.string.error
                                                )
                                        )
                                )
                            }
                        }
            }

    override suspend fun getAllWishContent(): Result<List<Product>> =
            suspendCoroutine { continuation ->
                FirebaseFirestore.getInstance()
                        .collection(PATH_PRODUCT)
                        .whereEqualTo("wishable", true)
                        .get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val list = mutableListOf<Product>()
                                for (document in task.result!!) {
                                    val product = document.toObject(Product::class.java)
                                    list.add(product)
                                    list.sortByDescending { it.time }
                                }
                                continuation.resume(Result.Success(list))
                            } else {
                                task.exception?.let {
                                    Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                                    continuation.resume(Result.Error(it))
                                    return@addOnCompleteListener
                                }
                                continuation.resume(
                                        Result.Fail(
                                                SwapubApplication.instance.getString(
                                                        R.string.error
                                                )
                                        )
                                )
                            }
                        }
            }

    override fun getLiveSearch(field: String, searchKey: String): MutableLiveData<List<Product>> {
        val liveData = MutableLiveData<List<Product>>()

        FirebaseFirestore.getInstance()
                .collection(PATH_PRODUCT)
                .whereEqualTo("tradable", false)
                .orderBy(field)
                .startAt(searchKey.toUpperCase())
                .endAt(searchKey.toLowerCase() + "\uf8ff")
                .addSnapshotListener { snapshot, exception ->

                    exception?.let {
                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                    }

                    val list = mutableListOf<Product>()
                    for (document in snapshot!!) {
                        Logger.d(document.id + " => " + document.data)
                        val event = document.toObject(Product::class.java)
                        list.add(event)
                    }

                    liveData.value = list
                }
        return liveData
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun updateUserInfo(user: User): Result<Boolean> =
            suspendCoroutine { continuation ->
                FirebaseFirestore.getInstance()
                        .collection(PATH_USER)
                        .document(user.id)
                        .update("image", user.image, "name", user.name, "place", user.place)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
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
                        }.addOnSuccessListener {

                        }
            }


    override suspend fun updateToClubList(clubList: MutableList<String>): Result<Boolean> =
            suspendCoroutine { continuation ->
                val addToClubList = FirebaseFirestore.getInstance()
                        .collection(PATH_USER)
                        .document(UserManager.userId)
                addToClubList
                        .update("clubList", clubList)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
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

    override suspend fun getUserClub(userL: String): Result<List<String>> =
            suspendCoroutine { continuation ->
                FirebaseFirestore.getInstance()
                        .collection(PATH_USER)
                        .whereEqualTo("id", userL)
                        .get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                var userClubList = listOf<String>()
                                for (document in task.result!!) {
                                    Logger.d(document.id + " => " + document.data)

                                    val user = document.toObject(User::class.java)
                                    if (user.clubList != null) {
                                        userClubList = user.clubList
                                    }
                                }
                                continuation.resume(Result.Success(userClubList))
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

    override suspend fun getClub(clubIds: List<String>): Result<List<Club>> =
            suspendCoroutine { continuation ->
                FirebaseFirestore.getInstance()
                        .collection(PATH_CLUB)
                        .get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val list = mutableListOf<Club>()
                                for (document in task.result!!) {
                                    Logger.d(document.id + " => " + document.data)

                                    val club = document.toObject(Club::class.java)
                                    for (clubId in clubIds) {
                                        if (club.id == clubId)
                                            list.add(club)
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


    override suspend fun getUserClubList(userL: String): Result<User> =
            suspendCoroutine { continuation ->
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

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun deleteProduct(productId: String): Result<Boolean> =
            suspendCoroutine { continuation ->

                FirebaseFirestore.getInstance()
                        .collection(PATH_PRODUCT)
                        .document(productId)
                        .delete()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
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

    override suspend fun getMenClothesProduct(): Result<List<Product>> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
                .collection(PATH_PRODUCT)
                .whereEqualTo("category", "男裝")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<Product>()
                        for (document in task.result!!) {
                            Logger.d(document.id + " => " + document.data)

                            val product = document.toObject(Product::class.java)
                            list.add(product)
                            list.sortByDescending { it.time }
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

