package com.mvvm.mvvmtest


import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.mvvm.mvvmkotlin.base.BaseActivity
import com.mvvm.mvvmtest.databinding.ActivityMainBinding


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }

    override fun initVariableId(): Int {
       return BR.viewModel
    }

    override fun initFactory(): ViewModelProvider.Factory {
        return AppViewModelFactory()
    }

}