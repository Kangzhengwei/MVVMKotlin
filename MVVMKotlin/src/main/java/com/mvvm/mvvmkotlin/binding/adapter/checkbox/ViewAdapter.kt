package com.mvvm.mvvmkotlin.binding.adapter.checkbox

import android.widget.CheckBox
import androidx.databinding.BindingAdapter
import com.mvvm.mvvmkotlin.binding.command.BindingConsumer

object ViewAdapter {

    @JvmStatic
    @BindingAdapter(value = ["onCheckedChangedCommand"], requireAll = false)
    fun setCheckedChanged(checkBox: CheckBox, bindingCommand: BindingConsumer<Boolean>) {
        checkBox.setOnCheckedChangeListener { _, p1 -> bindingCommand.execute(p1) }
    }
}