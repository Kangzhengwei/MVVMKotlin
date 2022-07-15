package com.mvvm.mvvmkotlin.binding.command

class BindingConsumer<T>(execute: ((T) -> Unit)) {

    private var consumer: ((T) -> Unit)? = execute


    /**
     * 执行带泛型参数的命令
     *
     * @param parameter 泛型参数
     */
    fun execute(parameter: T) {
        consumer?.invoke(parameter)
    }


}