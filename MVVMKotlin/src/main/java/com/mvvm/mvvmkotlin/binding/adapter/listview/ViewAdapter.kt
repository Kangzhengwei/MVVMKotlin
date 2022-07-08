package com.mvvm.mvvmkotlin.binding.adapter.listview

import android.widget.AbsListView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import androidx.databinding.BindingAdapter
import com.mvvm.mvvmkotlin.binding.command.BindingCommand
import com.mvvm.mvvmkotlin.util.KotlinThrottle.throttle
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce

object ViewAdapter {
    @BindingAdapter(value = ["onScrollChangeCommand", "onScrollStateChangedCommand"], requireAll = false)
    fun onScrollChangeCommand(listView: ListView, onScrollChangeCommand: BindingCommand<ListViewScrollDataWrapper>?, onScrollStateChangedCommand: BindingCommand<Int>?) {
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

    @BindingAdapter(value = ["onItemClickCommand"], requireAll = false)
    fun onItemClickCommand(listView: ListView, onItemClickCommand: BindingCommand<Int?>?) {
        listView.onItemClickListener = OnItemClickListener { parent, view, position, id -> onItemClickCommand?.execute(position) }
    }

    @BindingAdapter("onLoadMoreCommand")
    fun onLoadMoreCommand(listView: ListView, onLoadMoreCommand: BindingCommand<Int>) {
        listView.setOnScrollListener(OnScrollListener(listView, onLoadMoreCommand))
    }


    class OnScrollListener(listView: ListView, onLoadMoreCommand: BindingCommand<Int>) : AbsListView.OnScrollListener {
        private val onLoadMoreCommand: BindingCommand<Int>?
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