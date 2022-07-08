package com.mvvm.mvvmkotlin.binding.adapter.viewgoup

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableList
import androidx.databinding.ViewDataBinding
import me.tatarka.bindingcollectionadapter2.ItemBinding

object ViewAdapter {
    @BindingAdapter("itemView", "observableList")
    fun addViews(viewGroup: ViewGroup, itemBinding: ItemBinding<*>, viewModelList: ObservableList<IBindingItemViewModel<ViewDataBinding>>?) {
        if (viewModelList != null && !viewModelList.isEmpty()) {
            viewGroup.removeAllViews()
            for (viewModel in viewModelList) {
                val binding: ViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.context), itemBinding.layoutRes(), viewGroup, true)
                binding.setVariable(itemBinding.variableId(), viewModel)
                viewModel.injecDataBinding(binding)
            }
        }
    }
}