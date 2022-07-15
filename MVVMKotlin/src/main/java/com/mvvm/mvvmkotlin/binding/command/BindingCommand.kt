package com.mvvm.mvvmkotlin.binding.command

open class BindingCommand(execute: (() -> Unit)) {

    private var execute: (() -> Unit)? = execute


    /**
     * 执行BindingAction命令
     */
    fun execute() {
        execute?.invoke()
    }


}