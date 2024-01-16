package com.call.notification.dek.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.call.notification.dek.db.call_history.CallHistory
import com.call.notification.dek.db.call_history.CallHistoryDAO

@Database(entities = [CallHistory::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun callHistoryDAO(): CallHistoryDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "call_history_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}