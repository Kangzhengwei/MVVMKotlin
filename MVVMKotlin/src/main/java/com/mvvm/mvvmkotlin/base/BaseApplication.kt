package com.mvvm.mvvmkotlin.base

import android.app.Application
import com.mvvm.mvvmkotlin.network.OkHttpHelper
import okhttp3.Interceptor

open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: BaseApplication
    }

    fun addInterceptor(interceptor: Interceptor) {
        OkHttpHelper.instance.addInterceptor(interceptor)
    }

}