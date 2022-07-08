package com.mvvm.mvvmkotlin.binding.adapter.checkbox

import android.widget.CheckBox
import androidx.databinding.BindingAdapter
import com.mvvm.mvvmkotlin.binding.command.BindingCommand

object ViewAdapter {

    @JvmStatic
    @BindingAdapter(value = ["onCheckedChangedCommand"], requireAll = false)
    fun setCheckedChanged(checkBox: CheckBox, bindingCommand: BindingCommand<Boolean>) {
        checkBox.setOnCheckedChangeListener { _, p1 -> bindingCommand.execute(p1) }
    }
}