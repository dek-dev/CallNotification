package com.call.notification.dek.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.call.notification.dek.CallViewModel
import com.call.notification.dek.service.ServiceRepository

class ViewModelFactory constructor(private val repository: ServiceRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CallViewModel::class.java)) {
            CallViewModel(repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}