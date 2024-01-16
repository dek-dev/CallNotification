package com.call.notification.dek.db.call_history

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CallHistoryDAO {
    @Query("SELECT * FROM call_history")
    fun getAll(): List<CallHistory>

    @Query("SELECT * FROM call_history WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<CallHistory>

    @Query(
        "SELECT * FROM call_history WHERE user_name LIKE :userName LIMIT 1"
    )
    fun findByName(userName: String): CallHistory

    @Insert
    fun insertAll(vararg users: CallHistory)

    @Delete
    fun delete(user: CallHistory)
}