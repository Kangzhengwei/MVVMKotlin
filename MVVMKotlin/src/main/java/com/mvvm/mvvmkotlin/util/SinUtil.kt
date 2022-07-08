package com.mvvm.mvvmkotlin.util

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

object SinUtil {


    /**
     * 加签
     *
     * @param map
     * @return
     */
    fun sign(map: Map<String, String>): String {
        val sortMap = sortMapByKey(map)
        val keyList: List<String> = ArrayList(sortMap.keys)
        Collections.sort(keyList)
        val sb = StringBuffer()
        for (i in keyList.indices) {
            val key = keyList[i]
            val value: Any? = sortMap[key]
            sb.append("$key=$value&")
        }
        val signStr = sb.substring(0, sb.length - 1)
        val pamMd5Str: String = encode(signStr)
        return encode(pamMd5Str + "5092wxwx1l05yuma")
    }


    /**
     * 根据map的key进行字典升序排序
     *
     * @param map
     * @return map
     */
    private fun sortMapByKey(map: Map<String, String>): Map<String, String> {
        val treemap: Map<String, String> = TreeMap(map)
        val list: List<Map.Entry<String, String>> = ArrayList(treemap.entries)
        Collections.sort(list) { o1, o2 -> o1.key.compareTo(o2.key) }
        return treemap
    }

    private fun encode(text: String): String {
        try {
            //获取md5加密对象
            val instance: MessageDigest = MessageDigest.getInstance("MD5")
            //对字符串加密，返回字节数组
            val digest: ByteArray = instance.digest(text.toByteArray())
            val sb = StringBuffer()
            for (b in digest) {
                //获取低八位有效值
                val i: Int = b.toInt() and 0xff
                //将整数转化为16进制
                var hexString = Integer.toHexString(i)
                if (hexString.length < 2) {
                    //如果是一位的话，补0
                    hexString = "0$hexString"
                }
                sb.append(hexString)
            }
            return sb.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return ""
    }
}