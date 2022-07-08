package com.mvvm.mvvmkotlin.binding.adapter.viewgoup

import androidx.databinding.ViewDataBinding

interface IBindingItemViewModel<V : ViewDataBinding> {
    fun injecDataBinding(binding: V)
}
