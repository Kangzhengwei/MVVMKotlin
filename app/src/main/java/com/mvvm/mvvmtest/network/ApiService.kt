package com.mvvm.mvvmtest.network

import com.mvvm.mvvmkotlin.base.Base
import com.mvvm.mvvmtest.TokenBean
import kotlinx.coroutines.flow.Flow
import retrofit2.http.POST

interface ApiService {

    @POST("api/user/login")
    suspend fun getToken(): Base<TokenBean>

    @POST("api/user/login")
    suspend fun getAuthor(): Base<TokenBean>

    @POST("api/user/login")
    fun getKey(): Flow<Base<TokenBean>>
}