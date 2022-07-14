package com.mvvm.mvvmtest

import com.mvvm.mvvmkotlin.base.BaseApplication
import com.mvvm.mvvmtest.network.MyInterceptor
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        addInterceptor(MyInterceptor())//添加自定义拦截器
    }
}