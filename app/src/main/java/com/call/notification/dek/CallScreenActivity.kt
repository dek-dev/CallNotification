package com.call.notification.dek

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.call.notification.dek.databinding.ActivityCallScreenBinding
import com.call.notification.dek.databinding.ActivityMainBinding
import com.call.notification.dek.utils.CallNotification
import com.google.gson.Gson

class CallScreenActivity : AppCompatActivity() {
    private val binding: ActivityCallScreenBinding by lazy {
        ActivityCallScreenBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val imgUrl = intent.getStringExtra("img_url");
        Log.d("log-data", "CallScreenActivity")
        Log.d("log-data", "dataNotification is $imgUrl")

        Glide.with(this)
            .load(imgUrl)
            .into(binding.imgCall)

        binding.imgDecline.setOnClickListener {
            CallNotification.dismissNotification(this)
            finish()
        }

        binding.imgAnswer.setOnClickListener {
            CallNotification.dismissNotification(this)
            finish()
        }
    }
}