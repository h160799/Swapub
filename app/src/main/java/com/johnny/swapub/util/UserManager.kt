package com.johnny.swapub.util

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.johnny.swapub.SwapubApplication
import com.johnny.swapub.data.LoadApiStatus
import com.johnny.swapub.data.User

object UserManager {

    var user = User(
//        "TSobJG5UBPbgdz6PHxN8","","",mutableListOf(),null, mutableListOf(),mutableListOf(),mutableListOf()
    )
//    var userId = "TSobJG5UBPbgdz6PHxN8"

    private val sharedPreferences = SwapubApplication.instance.getSharedPreferences(
        "myToken", Context.MODE_PRIVATE)


    var userId: String
        get() {              //取得token
            return sharedPreferences.getString("myToken", null)!!
        }

        set(token) {         //給token
            sharedPreferences.edit().putString("myToken", token).apply()
        }

    fun hasToken(): Boolean {
        return userId != null   //不為空值
    }
}

