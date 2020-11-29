package com.johnny.swapub

import android.app.Application
import com.johnny.swapub.data.remote.SwapubRepository
import com.johnny.swapub.util.ServiceLocator
import kotlin.properties.Delegates

class SwapubApplication : Application() {

    // Depends on the flavor,
    val swapubRepository: SwapubRepository
        get() = ServiceLocator.provideTasksRepository(this)

    companion object {
        var instance: SwapubApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}


