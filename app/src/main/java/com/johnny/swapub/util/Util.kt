package com.johnny.swapub.util

import com.johnny.swapub.SwapubApplication

object Util {

    fun getString(resourceId: Int): String {
        return SwapubApplication.instance.getString(resourceId)
    }
}