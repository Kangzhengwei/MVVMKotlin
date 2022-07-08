package com.mvvm.mvvmkotlin.binding.command


class ResponseCommand<T,R> {
    private var execute: BindingFunction<R>? = null
    //private var function: Function<T,R>? = null
    private var canExecute: BindingFunction<Boolean>? = null
}