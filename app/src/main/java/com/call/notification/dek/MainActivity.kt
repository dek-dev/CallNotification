package com.call.notification.dek

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.call.notification.dek.databinding.ActivityMainBinding
import com.call.notification.dek.firebase.FirebaseManagement
import com.call.notification.dek.utils.PermissionUtils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    val TAG = "log-firebase-message"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        registerFirebase()
    }

    private fun registerFirebase() {
        Firebase.messaging.isAutoInitEnabled = true
        Firebase.messaging.token.addOnCompleteListener(OnCompleteListener { task ->
            val token = task.result
            Log.d(TAG, "is token => $token")
        })

        var permission = arrayOf(Manifest.permission.POST_NOTIFICATIONS)
        if (PermissionUtils.requestPermission(this, requestPermissionLauncher, permission)) {
            setUpFirebase()
        }
    }

    private fun setUpFirebase() {
        FirebaseManagement.createChannel(this)
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        setUpFirebase()
    }

}