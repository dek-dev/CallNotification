package com.call.notification.dek.utils

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.AppWidgetTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.call.notification.dek.AnswerCallActivity
import com.call.notification.dek.CallActivity
import com.call.notification.dek.firebase.DeclineService
import com.call.notification.dek.firebase.FirebaseManagement
import com.call.notification.dek.R
import com.call.notification.dek.db.AppDatabase
import com.call.notification.dek.db.call_history.CallHistory
import com.call.notification.dek.firebase.NotificationModel
import com.google.gson.Gson
import java.net.URI
import kotlin.random.Random

object CallNotification {
    fun startCall(context: Context, notificationModel: NotificationModel,title : String?,body:String?) {
        val db = AppDatabase.getDatabase(context)
        val data = CallHistory(
            title = title,
            body = body,
            userImg = notificationModel.userImg,
            userName = notificationModel.userName
        )

        db.callHistoryDAO().insertAll(data)
        startFullScreenCall(context, notificationModel)
    }

    private fun startFullScreenCall(context: Context, notificationModel: NotificationModel) {
        val declineIntent = PendingIntent.getService(
            context,
            668,
            Intent(context, DeclineService::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )

        val answerIntent = PendingIntent.getActivity(
            context,
            0,
            toAnswer(context),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )

        //full Screen
        val callIntent = PendingIntent.getActivity(
            context,
            0,
            toCallScreen(context,notificationModel),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )

//        val  uri = URI(notificationModel.userImg);

        val remoteView = RemoteViews(context.packageName, R.layout.custom_call)
        remoteView.setImageViewUri(R.id.ivLogo, Uri.parse(notificationModel.userImg))

        val actionAnswer = NotificationCompat.Action(R.drawable.ic_call, "Answer", answerIntent)
        val actionDecline =
            NotificationCompat.Action(R.drawable.ic_hangup, "Decline", declineIntent)

        val notificationBuilder =
            NotificationCompat.Builder(context, FirebaseManagement.CHANNEL_ID).apply {
                setSmallIcon(R.mipmap.ic_launcher_round)
                setTicker("Call_STATUS")
                setContentTitle("Calling")
                setContentText("IncomingCall")
                setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                priority = NotificationCompat.PRIORITY_HIGH
                setOngoing(true)
                setCategory(NotificationCompat.CATEGORY_CALL)
                setStyle(NotificationCompat.DecoratedCustomViewStyle())
                setCustomContentView(remoteView)
                setCustomBigContentView(remoteView)
                setContentIntent(callIntent)
                setFullScreenIntent(callIntent, true)
                setAutoCancel(true)
                addAction(actionDecline)
                addAction(actionAnswer)
            }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(668, notificationBuilder.build())
    }

    private fun toAnswer(context: Context): Intent {
        val intent = Intent(context, AnswerCallActivity::class.java)
//        intent.putExtra("server_url", data?.serverUrl)

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        return intent
    }

    private fun toCallScreen(context: Context, notificationModel: NotificationModel): Intent {
        val intent = Intent(context, CallActivity::class.java)
        intent.putExtra("img_url", notificationModel.userImg.toString())
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        return intent
    }

    @JvmStatic
    fun dismissNotification(context: Context) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(668)
    }
}