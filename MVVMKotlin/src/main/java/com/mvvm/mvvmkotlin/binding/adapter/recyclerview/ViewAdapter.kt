package com.mvvm.mvvmkotlin.binding.adapter.recyclerview

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mvvm.mvvmkotlin.binding.command.BindingCommand
import com.mvvm.mvvmkotlin.util.KotlinThrottle.throttle
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce

object ViewAdapter {

    @BindingAdapter("lineManager")
    fun setLineManager(recyclerView: RecyclerView, lineManagerFactory: LineManagers.LineManagerFactory) {
        recyclerView.addItemDecoration(lineManagerFactory.create(recyclerView))
    }

    @BindingAdapter("layoutManager")
    fun setLayoutManager(recyclerView: RecyclerView, layoutManagerFactory: LayoutManagers.LayoutManagerFactory) {
        recyclerView.layoutManager = layoutManagerFactory.create(recyclerView)
    }

    @BindingAdapter(value = ["onScrollChangeCommand", "onScrollStateChangedCommand"], requireAll = false)
    fun onScrollChangeCommand(recyclerView: RecyclerView, onScrollChangeCommand: BindingCommand<ScrollDataWrapper>, onScrollStateChangedCommand: BindingCommand<Int>) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private var state = 0
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                onScrollChangeCommand.execute(ScrollDataWrapper(dx, dy, state))
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                state = newState
                onScrollStateChangedCommand.execute(newState)
            }
        })
    }

    @BindingAdapter("onLoadMoreCommand")
    fun onLoadMoreCommand(recyclerView: RecyclerView, onLoadMoreCommand: BindingCommand<Int>) {
        val listener: RecyclerView.OnScrollListener = OnScrollListener(onLoadMoreCommand)
        recyclerView.addOnScrollListener(listener)
    }

    @BindingAdapter("itemAnimator")
    fun setItemAnimator(recyclerView: RecyclerView, animator: RecyclerView.ItemAnimator) {
        recyclerView.itemAnimator = animator
    }


    class OnScrollListener(onLoadMoreCommand: BindingCommand<Int>) : RecyclerView.OnScrollListener() {
        private val onLoadMoreCommand: BindingCommand<Int>?
        private var scope: CoroutineScope? = null

        @OptIn(FlowPreview::class)
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
            val visibleItemCount = layoutManager!!.childCount
            val totalItemCount = layoutManager.itemCount
            val pastVisiblesItems = layoutManager.findFirstVisibleItemPosition()
            if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                scope?.launch {
                    recyclerView.throttle().debounce(1000).collect {
                        onLoadMoreCommand?.execute(recyclerView.adapter!!.itemCount)
                    }
                }
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
        }

        init {
            scope = CoroutineScope(Job() + Dispatchers.Main)
            this.onLoadMoreCommand = onLoadMoreCommand
        }
    }

    class ScrollDataWrapper(var scrollX: Int, var scrollY: Int, var state: Int)
}