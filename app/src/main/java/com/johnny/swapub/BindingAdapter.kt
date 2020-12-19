package com.johnny.swapub

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.johnny.swapub.util.TimeUtil


@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.bg_round_button)
                    .error(R.drawable.bg_round_button))
                    .into(imgView)

    }
}

@BindingAdapter("timeToDisplayFormat")
fun bindDisplayFormatTime(textView: TextView, time: Long?) {
    textView.text = time?.let { TimeUtil.StampToDate(it) }
}

@BindingAdapter("timeToDisplayFormatx")  //只有時、分
fun bindDisplayFormatTimex(textView: TextView, time: Long?) {
    textView.text = time?.let { TimeUtil.StampToDatex(it) }
}




