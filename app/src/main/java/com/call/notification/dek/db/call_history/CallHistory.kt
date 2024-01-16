package com.call.notification.dek.db.call_history

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "call_history")
data class CallHistory(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "body") val body: String?,
    @ColumnInfo(name = "user_img") val userImg: String?,
    @ColumnInfo(name = "user_name") val userName: String?,
)