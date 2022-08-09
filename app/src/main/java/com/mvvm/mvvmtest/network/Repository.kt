package com.mvvm.mvvmtest.network

import com.mvvm.mvvmkotlin.base.BaseModel
import com.mvvm.mvvmkotlin.network.ApiException
import com.mvvm.mvvmtest.TokenBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class Repository constructor(private val helper: ServiceHelper) : BaseModel() {

    suspend fun getToken(result: (TokenBean) -> Unit, error: (ApiException) -> Unit) {
        launchFlow {
            helper.getToken()
        }.handleResult()
            .flowOnMain()
            .catchException {
                error(it)
            }
            .collect {
                result(it)
            }
    }

    suspend fun getAuthor(result: (TokenBean) -> Unit, error: (ApiException) -> Unit) {
        launchQuery({ helper.getAuthor() }, {
            result(it)
        }, {
            error(it)
        })
    }

    suspend fun getKey(result: (TokenBean) -> Unit, error: (ApiException) -> Unit) {
        helper.getKey()
            .handleResult()
            .flowOnMain()
            .catchException {
                error(it)
            }
            .collect {
                result(it)
            }
    }
}