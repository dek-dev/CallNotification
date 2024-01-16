package com.call.notification.dek

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.call.notification.dek.db.call_history.CallHistory
import com.call.notification.dek.service.ServiceRepository
import com.call.notification.dek.utils.CallNotification
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CallViewModel constructor(private val repository: ServiceRepository) : ViewModel() {

    val onFinished = MutableLiveData<Boolean>()

    //    private val _callHistory = MutableLiveData<List<CallHistory>>()
//    val callHistory: LiveData<List<CallHistory>> get() = _callHistory
    val callHistory = MutableLiveData<List<CallHistory>>(emptyList())

    var job: Job? = null

    fun feedCallHistory() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getCallHistory()
            withContext(Dispatchers.Main) {
                Log.d("log-call-history", Gson().toJson(response))
                callHistory.value = response
            }
        }
    }

    fun answer(activity: Activity) {
        CallNotification.dismissNotification(activity)
        onFinished.value = true
        activity.finish()
    }

    fun decline(activity: Activity) {
        CallNotification.dismissNotification(activity)
        onFinished.value = true
        activity.finish()
    }
}