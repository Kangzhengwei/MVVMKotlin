package com.mvvm.mvvmkotlin.util

import com.google.gson.Gson


class GsonUtil {

    companion object {
        val instance: Gson by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Gson()
        }
    }
}