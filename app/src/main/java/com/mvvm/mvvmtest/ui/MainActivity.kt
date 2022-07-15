package com.mvvm.mvvmtest.ui


import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.mvvm.mvvmkotlin.base.BaseActivity
import com.mvvm.mvvmkotlin.flowbus.observeEvent
import com.mvvm.mvvmkotlin.util.LogUtils
import com.mvvm.mvvmtest.BR
import com.mvvm.mvvmtest.MessageEvent
import com.mvvm.mvvmtest.R
import com.mvvm.mvvmtest.databinding.ActivityMainBinding
import com.mvvm.mvvmtest.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {


    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initViewModel(): MainViewModel {
        val mainViewModel by viewModels<MainViewModel>()
        return mainViewModel
    }

    override fun initViewObservable() {
        super.initViewObservable()
        launchCreate {
            viewModel.stateFlow.collect {
                showToast(it)
            }
        }
        observeEvent<MessageEvent> {
            LogUtils.d("gloable1====" + it.name)
        }
        observeEvent<MessageEvent>(isSticky = true, minActiveState = Lifecycle.State.DESTROYED) {
            LogUtils.d("gloable3====" + it.name)
        }
        observeEvent<MessageEvent>(lifecycleScope) {
            LogUtils.d("gloable4====" + it.name)
        }
    }

}