package com.mvvm.mvvmkotlin.binding.adapter.radiogroup

import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.databinding.BindingAdapter
import com.mvvm.mvvmkotlin.binding.command.BindingConsumer

object ViewAdapter {

    @JvmStatic
    @BindingAdapter(value = ["onCheckedChangedCommand"], requireAll = false)
    fun onCheckedChangedCommand(radioGroup: RadioGroup, bindingCommand: BindingConsumer<String>) {
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = group.findViewById<View>(checkedId) as RadioButton
            bindingCommand.execute(radioButton.text.toString())
        }
    }
}