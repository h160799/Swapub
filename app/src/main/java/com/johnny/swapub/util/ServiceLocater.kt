package com.johnny.swapub.util

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.johnny.swapub.data.local.SwapubLocalDataSource
import com.johnny.swapub.data.remote.DefaultSwapubRepository
import com.johnny.swapub.data.remote.SwapubDataSource
import com.johnny.swapub.data.remote.SwapubRemoteDataSource
import com.johnny.swapub.data.remote.SwapubRepository

object ServiceLocator {

    @Volatile
    var swapubRepository: SwapubRepository? = null
        @VisibleForTesting set

    fun provideTasksRepository(context: Context): SwapubRepository {
        synchronized(this) {
            return swapubRepository
                    ?: swapubRepository
                    ?: createSwapubRepository(context)
        }
    }

    private fun createSwapubRepository(context: Context): SwapubRepository {
        return DefaultSwapubRepository(
                SwapubRemoteDataSource,
                createLocalDataSource(context)
        )
    }

    private fun createLocalDataSource(context: Context): SwapubDataSource {
        return SwapubLocalDataSource(context)
    }
}