package com.mvvm.mvvmtest.network


class ServiceHelper constructor(private val apiService: ApiService) {

    suspend fun getToken() = apiService.getToken()

}