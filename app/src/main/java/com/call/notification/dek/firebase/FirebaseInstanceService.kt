package com.call.notification.dek.firebase

import android.util.Log
import com.call.notification.dek.utils.CallNotification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson

class FirebaseInstanceService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val TAG = "log-firebase-message"

        val data = remoteMessage.data;
        val dataNotification = Gson().fromJson(data["notification_data"], NotificationModel::class.java)

        if (data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data} dataNotification is ${Gson().toJson(dataNotification)}")
        }

        // Check if message contains a notification payload.
//        remoteMessage.notification?.let {
//            Log.d(TAG, "Message Notification Body: ${it.body}")
//        }

        CallNotification.startCall(this,dataNotification,data["title"],data["body"])
    }
}