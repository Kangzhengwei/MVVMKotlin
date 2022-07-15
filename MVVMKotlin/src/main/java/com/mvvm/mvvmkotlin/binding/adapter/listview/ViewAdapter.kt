package com.mvvm.mvvmkotlin.binding.adapter.listview

import android.widget.AbsListView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import androidx.databinding.BindingAdapter
import com.mvvm.mvvmkotlin.binding.command.BindingConsumer
import com.mvvm.mvvmkotlin.util.KotlinThrottle.throttle
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.debounce

object ViewAdapter {

    @JvmStatic
    @BindingAdapter(value = ["onScrollChangeCommand", "onScrollStateChangedCommand"], requireAll = false)
    fun onScrollChangeCommand(listView: ListView, onScrollChangeCommand: BindingConsumer<ListViewScrollDataWrapper>?, onScrollStateChangedCommand: BindingConsumer<Int>?) {
        listView.setOnScrollListener(object : AbsListView.OnScrollListener {
            private var scrollState = 0
            override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {
                this.scrollState = scrollState
                onScrollStateChangedCommand?.execute(scrollState)
            }

            override fun onScroll(view: AbsListView, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                onScrollChangeCommand?.execute(ListViewScrollDataWrapper(scrollState, firstVisibleItem, visibleItemCount, totalItemCount))
            }
        })
    }

    @JvmStatic
    @BindingAdapter(value = ["onItemClickCommand"], requireAll = false)
    fun onItemClickCommand(listView: ListView, onItemClickCommand: BindingConsumer<Int?>?) {
        listView.onItemClickListener = OnItemClickListener { parent, view, position, id -> onItemClickCommand?.execute(position) }
    }

    @JvmStatic
    @BindingAdapter("onLoadMoreCommand")
    fun onLoadMoreCommand(listView: ListView, onLoadMoreCommand: BindingConsumer<Int>) {
        listView.setOnScrollListener(OnScrollListener(listView, onLoadMoreCommand))
    }


    class OnScrollListener(listView: ListView, onLoadMoreCommand: BindingConsumer<Int>) : AbsListView.OnScrollListener {
        private val onLoadMoreCommand: BindingConsumer<Int>?
        private val listView: ListView
        private var scope: CoroutineScope? = null
        override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {}

        @OptIn(FlowPreview::class)
        override fun onScroll(view: AbsListView, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
            if (firstVisibleItem + visibleItemCount >= totalItemCount && totalItemCount != 0 && (totalItemCount != listView.headerViewsCount + listView.footerViewsCount)) {
                scope?.launch {
                    listView.throttle().debounce(1000).collect {
                        onLoadMoreCommand?.execute(totalItemCount)
                    }
                }
            }
        }

        init {
            scope = CoroutineScope(Job() + Dispatchers.Main)
            this.onLoadMoreCommand = onLoadMoreCommand
            this.listView = listView
        }
    }


    class ListViewScrollDataWrapper(var scrollState: Int, var firstVisibleItem: Int, var visibleItemCount: Int, var totalItemCount: Int)
}