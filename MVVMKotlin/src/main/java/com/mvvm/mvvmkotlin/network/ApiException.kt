package com.mvvm.mvvmkotlin.network

class ApiException(var code: Int, var msg: String) : Exception(msg) {

}