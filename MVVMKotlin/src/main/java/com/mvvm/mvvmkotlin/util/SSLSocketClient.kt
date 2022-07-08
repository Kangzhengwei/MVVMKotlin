package com.mvvm.mvvmkotlin.util

import android.annotation.SuppressLint
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*

object SSLSocketClient {
    fun getSSLSocketFactory(): SSLSocketFactory {
        return try {
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, arrayOf(createTrustAllManager()), SecureRandom())
            sslContext.socketFactory
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    fun createTrustAllManager(): X509TrustManager? {
        var tm: X509TrustManager? = null
        try {
            tm = @SuppressLint("CustomX509TrustManager")
            object : X509TrustManager {
                @SuppressLint("TrustAllX509TrustManager")
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                    //do nothing，接受任意客户端证书
                }

                @SuppressLint("TrustAllX509TrustManager")
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                    //do nothing，接受任意服务端证书
                }

                override fun getAcceptedIssuers(): Array<X509Certificate?> {
                    return arrayOfNulls(0)
                }
            }
        } catch (ignored: Exception) {
            ignored.printStackTrace()
        }
        return tm
    }

    //获取HostnameVerifier
    fun getHostnameVerifier(): HostnameVerifier {
        return HostnameVerifier { _: String, _: SSLSession -> true }
    }
}