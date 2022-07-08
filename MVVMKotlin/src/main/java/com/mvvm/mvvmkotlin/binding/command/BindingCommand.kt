package com.mvvm.mvvmkotlin.binding.command

class BindingCommand<T> {
    private var execute: BindingAction? = null
    private var consumer: BindingConsumer<T>? = null
    private var canExecute0: BindingFunction<Boolean>? = null

    constructor(execute: BindingAction) {
        this.execute = execute
    }

    constructor(execute: BindingConsumer<T>) {
        this.consumer = execute
    }

    constructor(execute: BindingAction, canExecute0: BindingFunction<Boolean>) {
        this.execute = execute
        this.canExecute0 = canExecute0
    }

    constructor(execute: BindingConsumer<T>, canExecute0: BindingFunction<Boolean>) {
        this.consumer = execute
        this.canExecute0 = canExecute0
    }


    /**
     * 执行BindingAction命令
     */
    fun execute() {
        if (execute != null && canExecute0()) {
            execute!!.call()
        }
    }

    /**
     * 执行带泛型参数的命令
     *
     * @param parameter 泛型参数
     */
    fun execute(parameter: T) {
        if (consumer != null && canExecute0()) {
            consumer!!.call(parameter)
        }
    }

    /**
     * 是否需要执行
     *
     * @return true则执行, 反之不执行
     */
    private fun canExecute0(): Boolean {
        return if (canExecute0 == null) {
            true
        } else canExecute0!!.call()
    }
}