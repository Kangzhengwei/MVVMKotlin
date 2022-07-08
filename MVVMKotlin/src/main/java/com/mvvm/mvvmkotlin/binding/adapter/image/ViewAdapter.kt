package com.mvvm.mvvmkotlin.binding.adapter.image

import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object ViewAdapter {
    @JvmStatic
    @BindingAdapter(value = ["url", "placeholderRes"], requireAll = false)
     fun setImageUri(imageView: ImageView, url: String?, placeholderRes: Int) {
        if (!TextUtils.isEmpty(url)) {
            //使用Glide框架加载图片
            Glide.with(imageView.context).load(url)
                .apply(RequestOptions().placeholder(placeholderRes)).into(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["gifUrl"], requireAll = false)
    fun setGifImageUri(imageView: ImageView, id: Int) {
        //使用Glide加载GIF图片
        Glide.with(imageView.context).asGif().load(id).into(imageView)
    }
}