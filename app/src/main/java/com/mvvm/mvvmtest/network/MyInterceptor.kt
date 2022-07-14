package com.mvvm.mvvmtest.network

import android.text.TextUtils
import com.google.gson.reflect.TypeToken
import com.mvvm.mvvmkotlin.base.BaseApplication
import com.mvvm.mvvmkotlin.util.DataStore
import com.mvvm.mvvmkotlin.util.DeviceUtil
import com.mvvm.mvvmkotlin.util.GsonUtil
import com.mvvm.mvvmkotlin.util.SinUtil
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.nio.charset.Charset

class MyInterceptor : Interceptor {

    private val header: MutableMap<String, String> = HashMap()

    init {
        header["version"] = DeviceUtil.packageName(BaseApplication.instance)
        header["uuid"] = DeviceUtil.createUUid(BaseApplication.instance)
        header["deviceType"] = "2"
        header["deviceBrand"] = DeviceUtil.deviceBrand()
        header["deviceVersion"] = DeviceUtil.systemVersion()
        header["lange"] = DeviceUtil.getLANGUAGE(BaseApplication.instance)
        header["timeZone"] = DeviceUtil.getTimeZone()
        header["Authorization"] = DataStore.getData("authorization", "")
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        chain.request().body?.let {
            val buffer = Buffer()
            val type = it.contentType()
            var json = ""
            if (type != null && !TextUtils.isEmpty(type.subtype) && type.subtype != "form-data") {
                it.writeTo(buffer)
                json = buffer.readString(Charset.defaultCharset())
            }
            if (!TextUtils.isEmpty(json)) {
                val map: MutableMap<String, String> = GsonUtil.instance.fromJson(json, object : TypeToken<Map<String, String>>() {}.type)
                map.putAll(header)
                builder.addHeader("sign", SinUtil.sign(map))
            } else {
                builder.addHeader("sign", SinUtil.sign(header))
            }
        }
        header.forEach {
            builder.addHeader(it.key, it.value)
        }
        return chain.proceed(builder.build())
    }
}