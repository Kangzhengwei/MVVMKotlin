package com.mvvm.mvvmkotlin.binding.command

interface BindingFunction<T> {
    fun call(): T
}