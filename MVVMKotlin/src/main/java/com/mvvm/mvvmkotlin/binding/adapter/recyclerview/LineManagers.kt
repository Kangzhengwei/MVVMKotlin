package com.mvvm.mvvmkotlin.binding.adapter.recyclerview

import androidx.recyclerview.widget.RecyclerView

class LineManagers {
    interface LineManagerFactory {
        fun create(recyclerView: RecyclerView): RecyclerView.ItemDecoration
    }
    companion object{
        fun both(): LineManagerFactory {
            return object : LineManagerFactory {
                override fun create(recyclerView: RecyclerView): RecyclerView.ItemDecoration {
                    return DividerLine(recyclerView.context, DividerLine.LineDrawMode.BOTH)
                }
            }
        }

        fun horizontal(): LineManagerFactory {
            return object : LineManagerFactory {
                override fun create(recyclerView: RecyclerView): RecyclerView.ItemDecoration {
                    return DividerLine(recyclerView.context, DividerLine.LineDrawMode.HORIZONTAL)
                }
            }
        }

        fun vertical(): LineManagerFactory {
            return object : LineManagerFactory {
                override fun create(recyclerView: RecyclerView): RecyclerView.ItemDecoration {
                    return DividerLine(recyclerView.context, DividerLine.LineDrawMode.VERTICAL)
                }
            }
        }
    }
}