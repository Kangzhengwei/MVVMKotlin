package com.mvvm.mvvmkotlin.base

open class MultiItemViewModel<VM : BaseViewModel>(viewModel: VM) : ItemViewModel<VM>(viewModel) {
    protected var multiType: Any? = null

    open fun getItemType(): Any? {
        return multiType
    }

    open fun multiItemType(multiType: Any) {
        this.multiType = multiType
    }

}