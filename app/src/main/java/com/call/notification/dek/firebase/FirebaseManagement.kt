package com.call.notification.dek.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat

object FirebaseManagement {
    const val CHANNEL_ID = "Notification"
    const val CHANNEL_NAME = "Call Notification"

    fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
            val mainChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            mainChannel.lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
            notificationManager.createNotificationChannel(mainChannel)
        }
    }
}