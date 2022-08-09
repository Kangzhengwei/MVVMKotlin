package com.mvvm.mvvmtest.ui

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.mvvm.mvvmkotlin.flowbus.postEvent
import com.mvvm.mvvmkotlin.permission.XPermission
import com.mvvm.mvvmtest.MessageEvent
import com.mvvm.mvvmtest.R


class TestActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        findViewById<Button>(R.id.btn).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        findViewById<Button>(R.id.btn2).setOnClickListener {
            postEvent(MessageEvent("你好"))
        }
        XPermission.request(this, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO) {
            if (it) {

            }
        }
    }

}