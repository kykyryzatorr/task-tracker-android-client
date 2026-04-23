package com.example.task_tracker_android_client.data.models

import java.util.UUID

data class TaskListDto(
    val id: UUID? = null,
    val title: String,
    val description: String? = null,
    val count: Int? = null,
    val progress: Double? = null,
    val tasks: List<TaskDto>? = null
)