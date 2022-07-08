package com.mvvm.mvvmtest

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TokenBean(@SerializedName("Authorization") var authorization: String, @SerializedName("uid") var uid: Int):Serializable
