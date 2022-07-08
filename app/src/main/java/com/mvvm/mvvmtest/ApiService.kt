package com.mvvm.mvvmtest

import com.mvvm.mvvmkotlin.base.Base
import retrofit2.http.POST

interface ApiService {

    @POST("api/user/login")
    suspend fun getToken(): Base<TokenBean>
}