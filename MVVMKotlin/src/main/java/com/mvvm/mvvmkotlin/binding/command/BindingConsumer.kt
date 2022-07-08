package com.mvvm.mvvmkotlin.binding.command

interface BindingConsumer<T> {
    fun call(t:T)
}