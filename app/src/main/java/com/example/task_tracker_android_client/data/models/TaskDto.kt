package com.example.task_tracker_android_client.data.models

import com.example.taskmanager.domain.models.TaskPriority
import com.example.taskmanager.domain.models.TaskStatus
import java.time.LocalDateTime
import java.util.UUID

data class TaskDto(
    val id: UUID? = null,
    val title: String,
    val description: String? = null,
    val dueDate: LocalDateTime? = null,
    val priority: TaskPriority,
    val status: TaskStatus
)