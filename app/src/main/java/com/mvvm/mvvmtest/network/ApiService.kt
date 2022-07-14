package com.mvvm.mvvmtest.network

import com.mvvm.mvvmkotlin.base.Base
import com.mvvm.mvvmtest.TokenBean
import retrofit2.http.POST

interface ApiService {

    @POST("api/user/login")
    suspend fun getToken(): Base<TokenBean>
}