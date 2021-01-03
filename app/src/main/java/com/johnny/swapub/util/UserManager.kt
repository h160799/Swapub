package com.johnny.swapub.util

import android.content.Context
import com.johnny.swapub.SwapubApplication
import com.johnny.swapub.data.User

object UserManager {

    var user = User()

    private val sharedPreferences =
            SwapubApplication.instance.getSharedPreferences("myToken", Context.MODE_PRIVATE)

    var userId: String
        get() {
            return sharedPreferences.getString("myToken", null)!!
        }
        set(token) {
            sharedPreferences.edit().putString("myToken", token).apply()
        }

    var userName: String
        get() {
            return sharedPreferences.getString("nameToken", null)!!
        }
        set(token) {
            sharedPreferences.edit().putString("nameToken", token).apply()
        }

    var userImage: String
        get() {
            return sharedPreferences.getString("imageToken", null)!!
        }
        set(token) {
            sharedPreferences.edit().putString("imageToken", token).apply()
        }

    fun hasToken(): Boolean {
        return userId != null   //不為空值
    }
}

