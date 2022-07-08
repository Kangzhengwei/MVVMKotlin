package com.mvvm.mvvmtest

import com.mvvm.mvvmkotlin.base.BaseApplication
import com.mvvm.mvvmkotlin.network.OkHttpHelper
import com.mvvm.mvvmkotlin.network.RetrofitHelper
import com.mvvm.mvvmkotlin.util.DataStore
import com.mvvm.mvvmkotlin.util.DeviceUtil

object Injection {
    private fun provideService(): ServiceHelper? {
        OkHttpHelper.instance.addHeader("version", DeviceUtil.packageName(BaseApplication.instance))
            .addHeader("uuid", DeviceUtil.createUUid(BaseApplication.instance))
            .addHeader("deviceType", "2").addHeader("deviceBrand", DeviceUtil.deviceBrand())
            .addHeader("deviceVersion", DeviceUtil.systemVersion())
            .addHeader("lange", DeviceUtil.getLANGUAGE(BaseApplication.instance))
            .addHeader("timeZone", DeviceUtil.getTimeZone())
            .addHeader("Authorization", DataStore.getData("authorization", ""))
        val apiService = RetrofitHelper.getInstance(Constant.BASEURL)!!
            .create(ApiService::class.java)
        return ServiceHelper.getInstance(apiService)
    }

    fun provideRepository(): Repository {
        return Repository.getInstance(provideService()!!)!!
    }
}