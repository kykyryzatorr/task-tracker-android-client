package com.example.task_tracker_android_client.domain.models

import java.util.UUID

data class TaskList(
    val id: UUID? = null,
    val title: String,
    val description: String? = null,
    val count: Int? = null,
    val progress: Double? = null,
    val tasks: List<Task>? = null
)