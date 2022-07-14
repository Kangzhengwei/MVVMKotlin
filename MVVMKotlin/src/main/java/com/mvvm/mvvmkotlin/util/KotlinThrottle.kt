package com.mvvm.mvvmkotlin.util

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

object KotlinThrottle {
    /***
     * 设置延迟时间的View扩展
     * @param delay Long 延迟时间，默认600毫秒
     * @return T
     */
    fun <T : View> T.withTrigger(delay: Long = 600): T {
        triggerDelay = delay
        return this
    }

    /***
     * 点击事件的View扩展
     * @param block: (T) -> Unit 函数
     * @return Unit
     */
    fun <T : View> T.click(block: (T) -> Unit) = setOnClickListener {
        block(it as T)
    }

    fun <T : View> T.longClick(block: (T) -> Unit) = setOnLongClickListener { p0 ->
        block(p0 as T)
        true
    }

    /***
     * 带延迟过滤的点击事件View扩展
     * @param delay Long 延迟时间，默认600毫秒
     * @param block: (T) -> Unit 函数
     * @return Unit
     */
    fun <T : View> T.clickWithTrigger(time: Long = 600, block: (T) -> Unit) {
        triggerDelay = time
        setOnClickListener {
            if (clickEnable()) {
                block(it as T)
            }
        }
    }

    private var <T : View> T.triggerLastTime: Long
        get() = if (getTag(1123460103) != null) getTag(1123460103) as Long else -601
        set(value) {
            setTag(1123460103, value)
        }

    private var <T : View> T.triggerDelay: Long
        get() = if (getTag(1123461123) != null) getTag(1123461123) as Long else 600
        set(value) {
            setTag(1123461123, value)
        }

    private fun <T : View> T.clickEnable(): Boolean {
        var flag = false
        val currentClickTime = System.currentTimeMillis()
        if (currentClickTime - triggerLastTime >= triggerDelay) {
            flag = true
            triggerLastTime = currentClickTime
        }
        return flag
    }

    /***
     * 带延迟过滤的点击事件监听，见[View.OnClickListener]
     * 延迟时间根据triggerDelay获取：600毫秒，不能动态设置
     */
    interface OnLazyClickListener : View.OnClickListener {

        override fun onClick(v: View?) {
            if (v?.clickEnable() == true) {
                onLazyClick(v)
            }
        }

        fun onLazyClick(v: View)
    }


    /**
     * Flow实现
     */
    fun View.clickEventFlow(): Flow<Unit> {
        var lastClickTime = System.currentTimeMillis()
        return callbackFlow {
            setOnClickListener {
                if (System.currentTimeMillis() - lastClickTime > 500) {
                    lastClickTime = System.currentTimeMillis()
                    trySend(Unit)
                }
            }
            awaitClose {
                setOnClickListener(null)
            }
        }
    }


    /**
     * Flow实现
     */
    fun TextView.textChangeFlow(): Flow<CharSequence> {
        return callbackFlow {
            val watcher = object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(content: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (content != null) {
                        trySend(content)
                    }
                }

                override fun afterTextChanged(p0: Editable?) {
                }

            }
            addTextChangedListener(watcher)
            awaitClose {
                removeTextChangedListener(watcher)
            }
        }
    }


    private var lastClickTime: Long = 0

    /**
     * Flow实现
     */
    fun View.throttle(): Flow<Unit> {
        return callbackFlow {
            if (System.currentTimeMillis() - lastClickTime > 1000) {
                trySend(Unit)
            }
            awaitClose {
                lastClickTime = System.currentTimeMillis()
            }
        }
    }
}