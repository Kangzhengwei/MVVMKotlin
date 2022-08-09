package com.mvvm.mvvmkotlin.permission

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment


typealias PermissionCallback = (Boolean) -> Unit

class InvisibleFragment : Fragment() {

    private var callback: PermissionCallback? = null

    fun requestNow(cb: PermissionCallback, vararg permission: String) {
        callback = cb
        activityResultLauncher.launch(permission.toMutableList().toTypedArray())
    }


    private val activityResultLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        var granted = true
        permissions.entries.forEach {
            if (!it.value) granted = false
        }
        callback?.let { it(granted) }
    }

}