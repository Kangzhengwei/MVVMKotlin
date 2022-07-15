package com.mvvm.mvvmkotlin.flowbus

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

//Application范围的事件
inline fun <reified T> postEvent(event: T, timeMillis: Long = 0L) {
    VMProvider.getApplicationScopeViewModel(FlowBusCore::class.java)
        .postEvent(T::class.java.name, event!!, timeMillis)
}

//限定范围的事件
inline fun <reified T> postEvent(scope: ViewModelStoreOwner, event: T, timeMillis: Long = 0L) {
    ViewModelProvider(scope)[FlowBusCore::class.java].postEvent(T::class.java.name, event!!, timeMillis)
}