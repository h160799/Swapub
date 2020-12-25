package com.johnny.swapub.data.remote

import androidx.lifecycle.MutableLiveData
import com.johnny.swapub.data.*
import com.johnny.swapub.util.UserManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DefaultSwapubRepository(private val swapubRemoteDataSource: SwapubDataSource,
                              private val swapubLocalDataSource: SwapubDataSource,
                              private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SwapubRepository {
    override suspend fun getUserInfo(userId: String): Result<User>{
        return swapubRemoteDataSource.getUserInfo(userId)
    }

    override suspend fun getProduct(): Result<List<Product>> {
        return swapubRemoteDataSource.getProduct()
    }

    override suspend fun getProductWithPlace(): Result<List<Product>>{
        return swapubRemoteDataSource.getProductWithPlace()
    }

    override suspend fun getOneProduct(productId: String): Result<Product> {
        return swapubRemoteDataSource.getOneProduct(productId)
    }

    override fun getMessage(documentId: String): MutableLiveData<List<Message>> {
        return swapubRemoteDataSource.getMessage(documentId)
    }

    override suspend fun getUserDetail(product: Product): Result<User>{
        return swapubRemoteDataSource.getUserDetail(product)
    }

    override suspend fun getUserFavor(userL: String): Result<List<String>> {
        return swapubRemoteDataSource.getUserFavor(userL)
    }


    override suspend fun getFavoriteList(userL: String): Result<User> {
        return swapubRemoteDataSource.getFavoriteList(userL)
    }

    override suspend fun getFavoriteProduct(productId: List<String>): Result<List<Product>> {
        return swapubRemoteDataSource.getFavoriteProduct(productId)
    }

    override suspend fun updateProductToFavorList(productId: String, favoriteList: MutableList<String>): Result<Boolean> {
        return swapubRemoteDataSource.updateProductToFavorList(productId, favoriteList)
    }

    override suspend fun addUserToFirebase(user: User): Result<Boolean>  {
        return swapubRemoteDataSource.addUserToFirebase(user)
    }

    override fun getMessageHistory(): MutableLiveData<List<ChatRoom>> {
        return swapubRemoteDataSource.getMessageHistory()
    }
    override suspend fun postMessage(message: Message, document: String):Result<Boolean> {
        return swapubRemoteDataSource.postMessage(message, document)
    }

    override suspend fun postInterestMessage(chatRoom: ChatRoom,user: User): Result<Boolean> {
        return swapubRemoteDataSource.postInterestMessage(chatRoom,user)
    }

    override suspend fun getAddedChatRoom(chatRoom: ChatRoom): Result<ChatRoom>{
        return swapubRemoteDataSource.getAddedChatRoom(chatRoom)
    }

    override suspend fun postTradingType(chatRoomId: String,tradingType: TradingType): Result<Boolean>{
        return swapubRemoteDataSource.postTradingType(chatRoomId,tradingType)
    }

    override suspend fun updateTradingSelect(chatRoomId: String, tradingSelect: Boolean ): Result<Boolean>{
        return swapubRemoteDataSource.updateTradingSelect(chatRoomId, tradingSelect)
    }

    override suspend fun getTradingType(chatRoomId: String): Result<TradingType>{
        return swapubRemoteDataSource.getTradingType(chatRoomId)
    }

    override suspend fun updateProductTradable(productId: String, tradable: Boolean ): Result<Boolean> {
        return swapubRemoteDataSource.updateProductTradable(productId, tradable)
    }

    override suspend fun deleteTradingType(chatRoomId: String): Result<Boolean> {
        return swapubRemoteDataSource.deleteTradingType(chatRoomId)
    }

    override suspend fun postTradingInfo(product: Product): Result<Boolean> {
        return swapubRemoteDataSource.postTradingInfo(product)
    }

    override suspend fun getPostProduct(userId: String): Result<List<Product>>{
        return swapubRemoteDataSource.getPostProduct(userId)
    }

    override suspend fun getWishContent(userId: String): Result<List<Product>>{
        return swapubRemoteDataSource.getWishContent(userId)
    }

    override suspend fun getAllWishContent(): Result<List<Product>>{
        return swapubRemoteDataSource.getAllWishContent()
    }

    override fun getLiveSearch(field: String, searchKey: String): MutableLiveData<List<Product>>{
        return swapubRemoteDataSource.getLiveSearch(field, searchKey)
    }

    override suspend fun updateUserInfo(user: User): Result<Boolean>{
        return swapubRemoteDataSource.updateUserInfo(user)
    }

    override suspend fun updateToClubList(clubList: MutableList<String>): Result<Boolean>{
    return swapubRemoteDataSource.updateToClubList(clubList)
    }

    override suspend fun getUserClub(userL: String): Result<List<String>>{
        return swapubRemoteDataSource.getUserClub(userL)
    }

    override suspend fun getClub(clubIds: List<String>): Result<List<Club>>{
        return swapubRemoteDataSource.getClub(clubIds)
    }

    override suspend fun getUserClubList(userL: String): Result<User>{
        return swapubRemoteDataSource.getUserClubList(userL)
    }
}
