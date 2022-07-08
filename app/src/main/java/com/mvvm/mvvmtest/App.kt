package com.mvvm.mvvmtest

import com.mvvm.mvvmkotlin.base.BaseApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
    }
}