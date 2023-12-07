package com.call.notification.dek.firebase

import com.google.gson.annotations.SerializedName

data class NotificationModel(

	@field:SerializedName("user_name")
	val userName: String? = null,

	@field:SerializedName("title_calling")
	val titleCalling: String? = null,

	@field:SerializedName("user_img")
	val userImg: String? = null
)
