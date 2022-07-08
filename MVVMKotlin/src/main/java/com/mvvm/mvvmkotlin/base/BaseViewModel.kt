package com.mvvm.mvvmkotlin.base

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.viewModelScope
import com.mvvm.mvvmkotlin.util.ToastUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class BaseViewModel : AndroidViewModel(BaseApplication.instance), LifecycleObserver {
    /**
     * 所有网络请求都在 viewModelScope 域中启动，当页面销毁时会自动
     * 调用ViewModel的  #onCleared 方法取消所有协程
     */
    fun launchUI(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch { block() }
    /**
     * toast
     */
    fun showToast(msg: String) {
        ToastUtil.showToast(BaseApplication.instance, msg)
    }
}