package com.mvvm.mvvmkotlin.network

import com.mvvm.mvvmkotlin.flowretrofitadapter.FlowCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper constructor(baseUrl: String) {

    @Volatile
    private var retrofit = Retrofit.Builder()
        .client(OkHttpHelper.instance.getOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(FlowCallAdapterFactory.create())
        .baseUrl(baseUrl)
        .build()

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