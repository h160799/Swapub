package com.johnny.swapub.data.local

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.johnny.swapub.data.*
import com.johnny.swapub.data.remote.SwapubDataSource
import com.johnny.swapub.util.UserManager

class SwapubLocalDataSource(val context: Context) : SwapubDataSource {
    override suspend fun getUserInfo(userId: String): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getProduct(): Result<List<Product>> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductWithPlace(): Result<List<Product>> {
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

    override suspend fun postInterestMessage(chatRoom: ChatRoom, user: User): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getAddedChatRoom(chatRoom: ChatRoom): Result<ChatRoom> {
        TODO("Not yet implemented")
    }

    override suspend fun postTradingType(chatRoomId: String,tradingType: TradingType): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun updateTradingSelect(chatRoomId: String, tradingSelect: Boolean): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getTradingType(chatRoomId: String): Result<TradingType> {
        TODO("Not yet implemented")
    }

    override suspend fun updateProductTradable(productId: String, tradable: Boolean): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTradingType(chatRoomId: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun postTradingInfo(product: Product): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getPostProduct(userId: String): Result<List<Product>> {
        TODO("Not yet implemented")
    }

    override suspend fun getWishContent(userId: String): Result<List<Product>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllWishContent(): Result<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun getLiveSearch(field: String, searchKey: String): MutableLiveData<List<Product>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserInfo(user: User): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun updateToClubList(clubList: MutableList<String>): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserClub(userL: String): Result<List<String>>{
        TODO("Not yet implemented")
    }

    override suspend fun getClub(clubIds: List<String>): Result<List<Club>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserClubList(userL: String): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteProduct(productId: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getMenClothesProduct(): Result<List<Product>> {
        TODO("Not yet implemented")
    }


}
