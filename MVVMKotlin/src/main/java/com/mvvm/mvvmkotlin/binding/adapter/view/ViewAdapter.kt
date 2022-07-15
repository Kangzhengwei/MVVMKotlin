package com.mvvm.mvvmkotlin.binding.adapter.view

import android.view.View
import android.view.View.OnFocusChangeListener
import androidx.databinding.BindingAdapter
import com.mvvm.mvvmkotlin.binding.command.BindingCommand
import com.mvvm.mvvmkotlin.binding.command.BindingConsumer
import com.mvvm.mvvmkotlin.util.KotlinThrottle.click
import com.mvvm.mvvmkotlin.util.KotlinThrottle.longClick
import com.mvvm.mvvmkotlin.util.KotlinThrottle.withTrigger

object ViewAdapter {
    //防重复点击间隔(秒)
    private const val CLICK_INTERVAL = 500

    /**
     * requireAll 是意思是是否需要绑定全部参数, false为否
     * View的onClick事件绑定
     * onClickCommand 绑定的命令,
     * isThrottleFirst 是否开启防止过快点击
     */
    @JvmStatic
    @BindingAdapter(value = ["onClickCommand", "isThrottleFirst"], requireAll = false)
    fun onClickCommand(view: View, clickCommand: BindingCommand?, isThrottleFirst: Boolean) {
        if (isThrottleFirst) {
            view.withTrigger().click {
                clickCommand?.execute()
            }
        } else {
            view.click {
                clickCommand?.execute()
            }
        }
    }

    /**
     * view的onLongClick事件绑定
     */
    @JvmStatic
    @BindingAdapter(value = ["onLongClickCommand"], requireAll = false)
    fun onLongClickCommand(view: View, clickCommand: BindingCommand?) {
        view.longClick {
            clickCommand?.execute()
        }
    }

    /**
     * 回调控件本身
     *
     * @param currentView
     * @param bindingCommand
     */
    @JvmStatic
    @BindingAdapter(value = ["currentView"], requireAll = false)
    fun replyCurrentView(currentView: View, bindingCommand: BindingConsumer<View>) {
        bindingCommand.execute(currentView)
    }


    /**
     * view是否需要获取焦点
     */
    @JvmStatic
    @BindingAdapter("requestFocus")
    fun requestFocusCommand(view: View, needRequestFocus: Boolean) {
        if (needRequestFocus) {
            view.isFocusableInTouchMode = true
            view.requestFocus()
        } else {
            view.clearFocus()
        }
    }

    /**
     * view的焦点发生变化的事件绑定
     */
    @JvmStatic
    @BindingAdapter("onFocusChangeCommand")
    fun onFocusChangeCommand(view: View, onFocusChangeCommand: BindingConsumer<Boolean>?) {
        view.onFocusChangeListener = OnFocusChangeListener { v, hasFocus -> onFocusChangeCommand?.execute(hasFocus) }
    }

    /**
     * view的显示隐藏
     */
    @JvmStatic
    @BindingAdapter(value = ["isVisible"], requireAll = false)
    fun isVisible(view: View, visibility: Boolean) {
        if (visibility) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }
}