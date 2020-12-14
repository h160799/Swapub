package com.johnny.swapub.util

import com.johnny.swapub.R
import com.johnny.swapub.util.Util.getString


enum class CurrentFragmentType(val value: String) {
    HOME(""),
    MESSAGEHISTORY(getString(R.string.message_history)),
    WISHNEWS(getString(R.string.wish_news)),
    PROFILE(getString(R.string.profile)),
    SEARCH(""),
    CONVERSATION(""),
    TRADINGSTYLE(""),
    MYTRADING(""),
    MYFAVORITE(""),
    MYCLUB(""),
    CLUB(""),
    PRODUCT("")

}