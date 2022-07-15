package com.mvvm.mvvmkotlin.flowbus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.mvvm.mvvmkotlin.base.BaseApplication

object VMProvider : ViewModelStoreOwner {

    private val eventViewModelStore: ViewModelStore = ViewModelStore()

    override fun getViewModelStore(): ViewModelStore {
        return eventViewModelStore
    }

    private val mApplicationProvider: ViewModelProvider by lazy {
        ViewModelProvider(VMProvider, ViewModelProvider.AndroidViewModelFactory.getInstance(BaseApplication.instance))
    }

    fun <T : ViewModel> getApplicationScopeViewModel(modelClass: Class<T>): T {
        return mApplicationProvider[modelClass]
    }
}