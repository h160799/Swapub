package com.johnny.swapub.util

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.johnny.swapub.data.SwapubRepository

object ServiceLocator {

    @Volatile
    var swapubRepository: SwapubRepository? = null
        @VisibleForTesting set

    fun provideTasksRepository(context: Context): SwapubRepository? {
        synchronized(this) {
            return swapubRepository
//                ?: swapubRepository
//                ?: createStylishRepository(context)
        }
    }

//    private fun createStylishRepository(context: Context): SwapubRepository {
//        return DefaultSwapubRepository(SwapubRemoteDataSource,
//            createLocalDataSource(context)
//        )
//    }
//
//    private fun createLocalDataSource(context: Context): SwapubDataSource {
//        return SwapubLocalDataSource(context)
//    }
}