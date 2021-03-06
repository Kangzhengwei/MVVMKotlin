package com.mvvm.mvvmkotlin.binding.adapter.edittext

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.mvvm.mvvmkotlin.binding.command.BindingConsumer

object ViewAdapter {

    @JvmStatic
    @BindingAdapter(value = ["requestFocus"], requireAll = false)
    fun requestFocusCommand(editText: EditText, needRequestFocus: Boolean) {
        if (needRequestFocus) {
            editText.setSelection(editText.text.length)
            editText.requestFocus()
            val imm = editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        }
        editText.isFocusableInTouchMode = needRequestFocus
    }

    @JvmStatic
    @BindingAdapter(value = ["textChanged"], requireAll = false)
    fun addTextChangedListener(editText: EditText, textChanged: BindingConsumer<String>?) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(text: CharSequence, i: Int, i1: Int, i2: Int) {
                textChanged?.execute(text.toString())
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }
}