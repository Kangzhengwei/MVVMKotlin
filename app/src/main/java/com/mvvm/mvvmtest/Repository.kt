package com.mvvm.mvvmtest

import com.mvvm.mvvmkotlin.base.BaseModel
import com.mvvm.mvvmkotlin.network.ApiException

class Repository(private val helper: ServiceHelper) : BaseModel() {


    suspend fun getToken(result: (TokenBean) -> Unit, error: (ApiException) -> Unit) {
        launchQuery({ helper.getToken() }, {
            result(it)
        }, {
            error(it)
        })
    }


    companion object {
        @Volatile
        private var instance: Repository? = null

        @JvmStatic
        fun getInstance(helper: ServiceHelper): Repository? {
            if (instance == null) {
                synchronized(Repository::class.java) {
                    if (instance == null) {
                        instance = Repository(helper)
                    }
                }
            }
            return instance
        }
    }
}