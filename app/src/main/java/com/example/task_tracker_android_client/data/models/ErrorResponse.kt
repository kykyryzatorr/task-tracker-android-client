package com.example.task_tracker_android_client.data.models

data class ErrorResponse(
    val status: Int,
    val message: String,
    val details: String? = null
)