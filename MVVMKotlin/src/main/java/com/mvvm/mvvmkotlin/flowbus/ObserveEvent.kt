package com.mvvm.mvvmkotlin.flowbus


import androidx.activity.ComponentActivity
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import kotlinx.coroutines.*

@MainThread
inline fun <reified T> LifecycleOwner.observeEvent(dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate, minActiveState: Lifecycle.State = Lifecycle.State.STARTED, isSticky: Boolean = false, noinline onReceived: (T) -> Unit): Job {
    return VMProvider.getApplicationScopeViewModel(FlowBusCore::class.java)
        .observeEvent(this, T::class.java.name, minActiveState, dispatcher, isSticky, onReceived)
}

//监听Fragment Scope 事件
@MainThread
inline fun <reified T> observeEvent(scope: Fragment, dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate, minActiveState: Lifecycle.State = Lifecycle.State.STARTED, isSticky: Boolean = false, noinline onReceived: (T) -> Unit): Job {
    return ViewModelProvider(scope)[FlowBusCore::class.java].observeEvent(scope, T::class.java.name, minActiveState, dispatcher, isSticky, onReceived)
}

// 监听Activity Scope 事件
@MainThread
inline fun <reified T> observeEvent(scope: ComponentActivity, dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate, minActiveState: Lifecycle.State = Lifecycle.State.STARTED, isSticky: Boolean = false, noinline onReceived: (T) -> Unit): Job {
    return ViewModelProvider(scope)[FlowBusCore::class.java].observeEvent(scope, T::class.java.name, minActiveState, dispatcher, isSticky, onReceived)
}

@MainThread
inline fun <reified T> observeEvent(coroutineScope: CoroutineScope, isSticky: Boolean = false, noinline onReceived: (T) -> Unit): Job {
    return coroutineScope.launch {
        VMProvider.getApplicationScopeViewModel(FlowBusCore::class.java)
            .observeWithoutLifecycle(T::class.java.name, isSticky, onReceived)
    }
}

@MainThread
inline fun <reified T> observeEvent(scope: ViewModelStoreOwner, coroutineScope: CoroutineScope, isSticky: Boolean = false, noinline onReceived: (T) -> Unit): Job {
    return coroutineScope.launch {
        ViewModelProvider(scope)[FlowBusCore::class.java].observeWithoutLifecycle(T::class.java.name, isSticky, onReceived)
    }
}