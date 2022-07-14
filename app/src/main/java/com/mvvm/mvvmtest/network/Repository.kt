package com.mvvm.mvvmtest.network

import com.mvvm.mvvmkotlin.base.BaseModel
import com.mvvm.mvvmkotlin.network.ApiException
import com.mvvm.mvvmtest.TokenBean

class Repository constructor(private val helper: ServiceHelper) : BaseModel() {

    suspend fun getToken(result: (TokenBean) -> Unit, error: (ApiException) -> Unit) {
        launchQuery({ helper.getToken() }, {
            result(it)
        }, {
            error(it)
        })
    }
}