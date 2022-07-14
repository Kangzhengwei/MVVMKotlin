package com.mvvm.mvvmtest.viewmodel


import com.mvvm.mvvmkotlin.base.BaseViewModel
import com.mvvm.mvvmtest.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {


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