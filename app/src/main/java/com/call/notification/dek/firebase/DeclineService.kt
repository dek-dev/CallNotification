package com.call.notification.dek.firebase

import android.app.IntentService
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.call.notification.dek.utils.CallNotification

class DeclineService : Service() {

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        CallNotification.dismissNotification(this)
        return super.onStartCommand(intent, flags, startId)
    }
}