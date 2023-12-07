package com.call.notification.dek

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.call.notification.dek.utils.CallNotification

class AnswerCallActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer_call)
        CallNotification.dismissNotification(this)

    }
}