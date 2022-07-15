package com.mvvm.mvvmkotlin.binding.adapter.swiperefresh

import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mvvm.mvvmkotlin.binding.command.BindingCommand

object ViewAdapter {
    @JvmStatic
    @BindingAdapter("onRefreshCommand")
    fun onRefreshCommand(swipeRefreshLayout: SwipeRefreshLayout, onRefreshCommand: BindingCommand?) {
        swipeRefreshLayout.setOnRefreshListener {
            onRefreshCommand?.execute()
        }
    }

    @JvmStatic
    @BindingAdapter("refreshing")
    fun setRefreshing(swipeRefreshLayout: SwipeRefreshLayout, refreshing: Boolean) {
        swipeRefreshLayout.isRefreshing = refreshing
    }
}