package com.mvvm.mvvmkotlin.network

import com.mvvm.mvvmkotlin.util.GsonUtil
import com.mvvm.mvvmkotlin.util.SSLSocketClient
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

class OkHttpHelper private constructor() {

    private val DEFAULT_READ_TIMEOUT_MILLIS: Long = 5 * 1000
    private val DEFAULT_WRITE_TIMEOUT_MILLIS: Long = 5 * 1000
    private val DEFAULT_CONNECT_TIMEOUT_MILLIS: Long = 5 * 1000

    @Volatile
    private var mOkHttpClientBuilder = OkHttpClient.Builder()
        .readTimeout(DEFAULT_READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
        .writeTimeout(DEFAULT_WRITE_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
        .connectTimeout(DEFAULT_CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
        .retryOnConnectionFailure(true)
        .sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), SSLSocketClient.createTrustAllManager()!!)
        .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
        .protocols(Collections.singletonList(Protocol.HTTP_1_1))
        .addInterceptor(HeaderInterceptor())
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))


    companion object {
        val instance: OkHttpHelper by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            OkHttpHelper()
        }
    }

    fun getOkHttpClient(): OkHttpClient {
        return mOkHttpClientBuilder.build()
    }

    /**
     * 把头信息添加到拦截器
     */
    private inner class HeaderInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val builder = chain.request().newBuilder()
            builder.addHeader("connection", "Keep-Alive")
            return chain.proceed(builder.build())
        }
    }

    fun addInterceptor(interceptor: Interceptor) {
        mOkHttpClientBuilder.addInterceptor(interceptor)
    }

    fun convertMapToBody(map: Map<String, Any>): RequestBody {
        return GsonUtil.instance.toJson(map)
            .toRequestBody("application/json;charset=UTF-8".toMediaType())
    }

    fun convertFileToBody(file: File): MultipartBody {
        val fileBody = file.asRequestBody("text/x-markdown; charset=utf-8".toMediaType())
        return MultipartBody.Builder().addFormDataPart("file", file.name, fileBody).build()
    }
}