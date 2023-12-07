package com.call.notification.dek.utils

import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat

object PermissionUtils {
    fun requestPermission(
        activity: Activity,
        requestPermissionLauncher: ActivityResultLauncher<Array<String>>,
        permission: Array<String>
    ): Boolean {

        var allow = true
        permission.forEach { it ->
            if (ContextCompat.checkSelfPermission(
                    activity,
                    it
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                allow = false
//                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, it)) {
//                    allow = false
//                }
            }
        }

        requestPermissionLauncher.launch(permission);
        return allow
    }

}