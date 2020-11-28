package com.johnny.swapub.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import androidx.annotation.RequiresApi
import com.johnny.swapub.SwapubApplication

object Util {

    /**
     * Determine and monitor the connectivity status
     *
     * https://developer.android.com/training/monitoring-device-state/connectivity-monitoring
     */
    fun isInternetConnected(): Boolean {
        val cm = SwapubApplication.instance
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    fun getString(resourceId: Int): String {
        return SwapubApplication.instance.getString(resourceId)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getColor(resourceId: Int): Int {
        return SwapubApplication.instance.getColor(resourceId)
    }
}