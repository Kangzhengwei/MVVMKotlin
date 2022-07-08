package com.mvvm.mvvmkotlin.binding.adapter.switchview

import android.widget.CompoundButton
import androidx.appcompat.widget.SwitchCompat
import androidx.databinding.BindingAdapter
import com.mvvm.mvvmkotlin.binding.command.BindingCommand

object ViewAdapter {
    /**
     * 设置开关状态
     *
     * @param mSwitch Switch控件
     */
    @BindingAdapter("switchState")
    fun setSwitchState(mSwitch: SwitchCompat, isChecked: Boolean) {
        mSwitch.isChecked = isChecked
    }

    /**
     * Switch的状态改变监听
     *
     * @param mSwitch        Switch控件
     * @param changeListener 事件绑定命令
     */
    @BindingAdapter("onCheckedChangeCommand")
    fun onCheckedChangeCommand(mSwitch: SwitchCompat, changeListener: BindingCommand<Boolean?>?) {
        if (changeListener != null) {
            mSwitch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                if (!buttonView.isPressed) {
                    return@OnCheckedChangeListener
                }
                changeListener.execute(isChecked)
            })
        }
    }
}