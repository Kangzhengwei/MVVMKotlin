package com.mvvm.mvvmkotlin.binding.adapter.scrollview

import android.widget.ScrollView
import androidx.core.widget.NestedScrollView
import androidx.databinding.BindingAdapter
import com.mvvm.mvvmkotlin.binding.command.BindingConsumer

object ViewAdapter {
    @JvmStatic
    @BindingAdapter("onScrollChangeCommand")
    fun onScrollChangeCommand(nestedScrollView: NestedScrollView, onScrollChangeCommand: BindingConsumer<NestScrollDataWrapper>?) {
        nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            onScrollChangeCommand?.execute(NestScrollDataWrapper(scrollX, scrollY, oldScrollX, oldScrollY))
        })
    }

    @JvmStatic
    @BindingAdapter("onScrollChangeCommand")
    fun onScrollChangeCommand(scrollView: ScrollView, onScrollChangeCommand: BindingConsumer<ScrollDataWrapper>?) {
        scrollView.viewTreeObserver.addOnScrollChangedListener { onScrollChangeCommand?.execute(ScrollDataWrapper(scrollView.scrollX, scrollView.scrollY)) }
    }
    class ScrollDataWrapper(var scrollX: Int, var scrollY: Int)

    class NestScrollDataWrapper(var scrollX: Int, var scrollY: Int, var oldScrollX: Int, var oldScrollY: Int)
}