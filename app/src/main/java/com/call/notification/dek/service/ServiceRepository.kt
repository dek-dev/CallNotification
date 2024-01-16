package com.call.notification.dek.service

import android.content.Context
import com.call.notification.dek.db.AppDatabase

class ServiceRepository constructor(context: Context)  {

    val db = AppDatabase.getDatabase(context)
    suspend fun getCallHistory() = db.callHistoryDAO().getAll()
}