package com.mvvm.mvvmtest

import com.mvvm.mvvmkotlin.base.BaseViewModel

class MainViewModel : BaseViewModel() {

    private val repository by lazy { Injection.provideRepository() }

    init {
        launchUI {
            repository.getToken({
                showToast(it.authorization)
            }, {
                showToast(it.msg)
            })
        }
    }
}