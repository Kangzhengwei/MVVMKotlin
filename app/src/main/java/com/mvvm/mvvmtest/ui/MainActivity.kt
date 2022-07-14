package com.mvvm.mvvmtest.ui


import android.os.Bundle
import androidx.activity.viewModels
import com.mvvm.mvvmkotlin.base.BaseActivity
import com.mvvm.mvvmtest.BR
import com.mvvm.mvvmtest.viewmodel.MainViewModel
import com.mvvm.mvvmtest.R
import com.mvvm.mvvmtest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initViewModel(): MainViewModel {
        return mainViewModel
    }

}