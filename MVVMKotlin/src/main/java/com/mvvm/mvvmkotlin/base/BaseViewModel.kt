package com.mvvm.mvvmkotlin.base

import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.mvvm.mvvmkotlin.livedata.SingleLiveData
import com.mvvm.mvvmkotlin.util.ToastUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class BaseViewModel : AndroidViewModel(BaseApplication.instance), DefaultLifecycleObserver {

    val liveEvent by lazy { LiveEvent() }

    /**
     * 所有网络请求都在 viewModelScope 域中启动，当页面销毁时会自动
     * 调用ViewModel的  #onCleared 方法取消所有协程
     */
    fun launchUI(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch { block() }

    override fun onCleared() {
        super.onCleared()
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
    }


    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    open fun startActivity(clz: Class<*>) {
        startActivity(clz, null)
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    open fun startActivity(clz: Class<*>, bundle: Bundle?) {
        val params: MutableMap<String, Any> = HashMap()
        params[ParameterField.CLASS] = clz
        bundle?.let {
            params[ParameterField.BUNDLE] = it
        }
        liveEvent.startActivityEvent.postValue(params)
    }

    /**
     * Toast
     */
    fun showToast(msg: String) {
        ToastUtil.showToast(BaseApplication.instance, msg)
    }


    fun finish() {
        liveEvent.finishEvent.call()
    }

    fun onBackPressed() {
        liveEvent.onBackPressedEvent.call()
    }

    open class LiveEvent : SingleLiveData<Any>() {
        val finishEvent by lazy { SingleLiveData<Void>() }
        val onBackPressedEvent by lazy { SingleLiveData<Void>() }
        val startActivityEvent by lazy { SingleLiveData<Map<String, Any>>() }
    }


    object ParameterField {
        const val CLASS = "CLASS"
        const val CANONICAL_NAME = "CANONICAL_NAME"
        const val BUNDLE = "BUNDLE"
    }


}