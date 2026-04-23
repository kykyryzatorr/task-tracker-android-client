package com.example.task_tracker_android_client.domain.models

import java.time.LocalDateTime
import java.util.UUID

data class Task(
    val id: UUID? = null,
    val title: String,
    val description: String? = null,
    val dueDate: LocalDateTime? = null,
    val priority: TaskPriority,
    val status: TaskStatus
)