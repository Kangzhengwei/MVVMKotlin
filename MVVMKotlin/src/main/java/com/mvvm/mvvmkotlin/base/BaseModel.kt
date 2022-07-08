package com.mvvm.mvvmkotlin.base

import com.mvvm.mvvmkotlin.network.ApiException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

abstract class BaseModel {

    /**
     * 用流的方式进行网络请求
     */
    fun <T> launchFlow(block: suspend () -> T): Flow<T> {
        return flow {
            emit(block())
        }
    }

    /**
     * 过滤请求结果，其他全抛异常
     * @param block 请求体
     * @param success 成功回调
     * @param error 失败回调
     * @param complete  完成回调（无论成功失败都会调用）
     */
    suspend fun <T> launchQuery(block: suspend CoroutineScope.() -> Base<T>, success: (T) -> Unit, error: (ApiException) -> Unit = {}, complete: () -> Unit = {}) {
        handleResult({
            withContext(Dispatchers.IO) {
                block().let {
                    if (it.code == 200) it.data
                    else throw ApiException(it.code, it.msg)
                }
            }.also {
                success(it)
            }
        }, {
            withContext(Dispatchers.Main) {
                error(it)
            }
        }, {
            complete()
        })
    }


    /**
     * 处理返回结果
     */
    private suspend fun handleResult(block: suspend CoroutineScope.() -> Unit, error: suspend CoroutineScope.(ApiException) -> Unit, complete: suspend CoroutineScope.() -> Unit) {
        coroutineScope {
            try {
                block()
            } catch (e: ApiException) {
                error(ApiException(e.code, e.msg))
            } finally {
                complete()
            }
        }
    }
}