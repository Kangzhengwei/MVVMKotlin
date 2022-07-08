package com.mvvm.mvvmkotlin.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper(baseUrl: String) {

    companion object {
        @Volatile
        private var sInstance: RetrofitHelper? = null
        private lateinit var retrofit: Retrofit

        @JvmStatic
        fun getInstance(baseUrl: String): RetrofitHelper? {
            if (sInstance == null) {
                synchronized(RetrofitHelper::class.java) {
                    if (sInstance == null) {
                        sInstance = RetrofitHelper(baseUrl)
                    }
                }
            }
            return sInstance
        }
    }

    init {
        retrofit = Retrofit.Builder()
            .client(OkHttpHelper.instance.getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }


    /**
     * create you ApiService
     * Create an implementation of the API endpoints defined by the `service` interface.
     */
    fun <T> create(service: Class<T>?): T {
        if (service == null) {
            throw RuntimeException("Api service is null!")
        }
        return retrofit.create(service)
    }
}