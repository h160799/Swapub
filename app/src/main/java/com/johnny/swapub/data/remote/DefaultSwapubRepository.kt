package com.johnny.swapub.data.remote

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DefaultSwapubRepository(private val swapubRemoteDataSource: SwapubDataSource,
                              private val swapubLocalDataSource: SwapubDataSource,
                              private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SwapubRepository {



}