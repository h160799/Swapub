package com.johnny.swapub.data.remote

import androidx.lifecycle.MutableLiveData
import com.johnny.swapub.data.*
import com.johnny.swapub.util.UserManager

interface SwapubDataSource {
    suspend fun getUserInfo(userId: String): Result<User>

    suspend fun getProduct(): Result<List<Product>>

    suspend fun getProductWithPlace(): Result<List<Product>>

    suspend fun getOneProduct(productId: String): Result<Product>

    fun getMessage(documentId: String): MutableLiveData<List<Message>>

    suspend fun getUserDetail(product: Product): Result<User>

    suspend fun getUserFavor(userL: String): Result<List<String>>

    suspend fun getFavoriteList(userL: String): Result<User>

    suspend fun getFavoriteProduct(productId: List<String>): Result<List<Product>>

    suspend fun updateProductToFavorList(productId: String, favoriteList: MutableList<String>): Result<Boolean>

    suspend fun addUserToFirebase(user: User): Result<Boolean>

    fun getMessageHistory(): MutableLiveData<List<ChatRoom>>

    suspend fun postMessage(message: Message, document: String): Result<Boolean>

    suspend fun postInterestMessage(chatRoom: ChatRoom,user: User): Result<Boolean>

    suspend fun getAddedChatRoom(chatRoom: ChatRoom): Result<ChatRoom>

    suspend fun postTradingType(chatRoomId: String,tradingType: TradingType): Result<Boolean>

    suspend fun updateTradingSelect(chatRoomId: String, tradingSelect: Boolean ): Result<Boolean>

    suspend fun getTradingType(chatRoomId: String): Result<TradingType>

    suspend fun updateProductTradable(productId: String, tradable: Boolean ): Result<Boolean>

    suspend fun deleteTradingType(chatRoomId: String): Result<Boolean>

    suspend fun postTradingInfo(product: Product): Result<Boolean>

    suspend fun getPostProduct(userId: String): Result<List<Product>>

    suspend fun getWishContent(userId: String): Result<List<Product>>

    suspend fun getAllWishContent(): Result<List<Product>>

    fun getLiveSearch(field: String, searchKey: String): MutableLiveData<List<Product>>

    suspend fun updateUserInfo(user: User): Result<Boolean>

    suspend fun updateToClubList(clubList: MutableList<String>): Result<Boolean>

    suspend fun getUserClub(userL: String): Result<List<String>>

    suspend fun getClub(clubIds: List<String>): Result<List<Club>>

    suspend fun getUserClubList(userL: String): Result<User>

    suspend fun deleteProduct(productId: String): Result<Boolean>

    suspend fun getMenClothesProduct(): Result<List<Product>>

}