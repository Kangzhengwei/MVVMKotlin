package com.mvvm.mvvmkotlin.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.mvvm.mvvmkotlin.network.OkHttpHelper
import okhttp3.Interceptor

open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        resisterActivityLifecycle()
    }

    companion object {
        lateinit var instance: BaseApplication
        var isForeground = false
    }

    fun addInterceptor(interceptor: Interceptor) {
        OkHttpHelper.instance.addInterceptor(interceptor)
    }

    private fun resisterActivityLifecycle() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(p0: Activity, p1: Bundle?) {
                AppManager.instance.addActivity(p0)
            }

            override fun onActivityStarted(p0: Activity) {
            }

            override fun onActivityResumed(p0: Activity) {
                isForeground = true
            }

            override fun onActivityPaused(p0: Activity) {
                isForeground = false
            }

            override fun onActivityStopped(p0: Activity) {
            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
            }

            override fun onActivityDestroyed(p0: Activity) {
                AppManager.instance.removeActivity(p0)
            }
        })
    }

}