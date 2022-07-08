package com.mvvm.mvvmkotlin.binding.adapter.recyclerview

import androidx.annotation.IntDef
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

class LayoutManagers {

    interface LayoutManagerFactory {
        fun create(recyclerView: RecyclerView): RecyclerView.LayoutManager
    }
    companion object{
        /**
         * A [LinearLayoutManager]
         */
        fun linear(): LayoutManagerFactory {
            return object : LayoutManagerFactory {
                override fun create(recyclerView: RecyclerView): RecyclerView.LayoutManager {
                    return LinearLayoutManager(recyclerView.context)
                }
            }
        }

        /**
         * A [LinearLayoutManager] with the given orientation and reverseLayout.
         */
        fun linear(@Orientation orientation: Int, reverseLayout: Boolean): LayoutManagerFactory {
            return object : LayoutManagerFactory {
                override fun create(recyclerView: RecyclerView): RecyclerView.LayoutManager {
                    return LinearLayoutManager(recyclerView.context, orientation, reverseLayout)
                }
            }
        }

        /**
         * A [GridLayoutManager] with the given spanCount.
         */
        fun grid(spanCount: Int): LayoutManagerFactory {
            return object : LayoutManagerFactory {
                override fun create(recyclerView: RecyclerView): RecyclerView.LayoutManager {
                    return GridLayoutManager(recyclerView.context, spanCount)
                }
            }
        }

        /**
         * A [GridLayoutManager] with the given spanCount, orientation and reverseLayout.
         */
        fun grid(spanCount: Int, @Orientation orientation: Int, reverseLayout: Boolean): LayoutManagerFactory {
            return object : LayoutManagerFactory {
                override fun create(recyclerView: RecyclerView): RecyclerView.LayoutManager {
                    return GridLayoutManager(recyclerView.context, spanCount, orientation, reverseLayout)
                }
            }
        }

        /**
         * A [StaggeredGridLayoutManager] with the given spanCount and orientation.
         */
        fun staggeredGrid(spanCount: Int, @Orientation orientation: Int): LayoutManagerFactory{
            return object : LayoutManagerFactory {
                override fun create(recyclerView: RecyclerView): RecyclerView.LayoutManager {
                    return StaggeredGridLayoutManager(spanCount, orientation)
                }
            }
        }

    }

    @IntDef(LinearLayoutManager.HORIZONTAL, LinearLayoutManager.VERTICAL)
    @Retention(RetentionPolicy.SOURCE)
    annotation class Orientation
}