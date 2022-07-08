package com.mvvm.mvvmkotlin.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings.Secure
import android.telephony.TelephonyManager
import java.io.UnsupportedEncodingException
import java.util.*

object DeviceUtil {
    private const val PREFS_FILE = "device_id.xml"
    private const val PREFS_DEVICE_ID = "device_id"

    @Volatile
    private var uuid: UUID? = null

    fun packageName(context: Context): String {
        val manager = context.packageManager
        var name = ""
        try {
            val info = manager.getPackageInfo(context.packageName, 0)
            name = info.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return name
    }

    fun getTimeZone(): String {
        return TimeZone.getDefault().id
    }

    @SuppressLint("HardwareIds")
    fun createUUid(context: Context): String {
        return if (uuid == null) {
            val prefs = context.getSharedPreferences(PREFS_FILE, 0)
            val id = prefs.getString(PREFS_DEVICE_ID, null)
            if (id != null) {
                // Use the ids previously computed and stored in the prefs file
                uuid = UUID.fromString(id)
                uuid.toString()
            } else {
                val androidId = Secure.getString(context.contentResolver, Secure.ANDROID_ID)
                // Use the Android ID unless it's broken, in which case fallback on deviceId,
                // unless it's not available, then fallback on a random number which we store
                // to a prefs file
                try {
                    if ("9774d56d682e549c" != androidId) {
                        uuid = UUID.nameUUIDFromBytes(androidId.toByteArray(charset("utf8")))
                    } else {
                        @SuppressLint("MissingPermission")
                        val deviceId = (context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).deviceId
                        uuid = if (deviceId != null) UUID.nameUUIDFromBytes(deviceId.toByteArray(charset("utf8"))) else UUID.randomUUID()
                    }
                    // Write the value out to the prefs file
                    prefs.edit().putString(PREFS_DEVICE_ID, uuid.toString()).commit()
                    uuid.toString()
                } catch (e: UnsupportedEncodingException) {
                    throw RuntimeException(e)
                }
            }
        } else {
            uuid.toString()
        }
    }


    fun deviceBrand(): String {
        return Build.BRAND
    }

    fun systemVersion(): String {
        return Build.VERSION.RELEASE
    }


    fun getLANGUAGE(context: Context): String {
        var systemLanguage = getSystemLanguage(context)
        if (systemLanguage == "Hant" || systemLanguage == "zh-CN") systemLanguage = "zh-Hans"
        if (systemLanguage == "Hans" || systemLanguage == "zh-TW" || systemLanguage == "zh-HK") systemLanguage = "zh-Hans"
        return systemLanguage
    }


    /**
     * 获取手机设置的语言国家
     *
     * @param context
     * @return
     */
    private fun getSystemLanguage(context: Context): String {
        var language: String
        val resources = context.resources
        //在7.0以上和7.0一下获取国家的方式有点不一样
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //  大于等于24即为7.0及以上执行内容
            language = resources.configuration.locales[0].language
            if (language == "zh") {
                language = resources.configuration.locales[0].script
                if ("" == language) {
                    language = resources.configuration.locales[0].toLanguageTag()
                }
            }
        } else {
            //  低于24即为7.0以下执行内容
            language = resources.configuration.locale.language
            if (language == "zh") {
                language = resources.configuration.locale.script
                if ("" == language) {
                    language = resources.configuration.locale.toLanguageTag()
                }
            }
        }
        return language
    }
}