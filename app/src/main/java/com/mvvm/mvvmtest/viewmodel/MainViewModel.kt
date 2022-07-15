package com.mvvm.mvvmtest.viewmodel


import com.mvvm.mvvmkotlin.base.BaseViewModel
import com.mvvm.mvvmkotlin.binding.command.BindingCommand
import com.mvvm.mvvmkotlin.flowbus.postEvent
import com.mvvm.mvvmtest.MessageEvent
import com.mvvm.mvvmtest.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {

    val stateFlow = MutableStateFlow("error")

    init {
        launchUI {
            repository.getToken({
                stateFlow.value = it.authorization
                postEvent(MessageEvent(it.authorization))
            }, {
                showToast(it.msg)
            })
        }
    }

    val finish = BindingCommand {
        finish()
    }
}