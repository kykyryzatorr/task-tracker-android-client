package com.example.task_tracker_android_client.presentation.ui.screens.tasks

import com.example.task_tracker_android_client.domain.models.Task

data class TasksState(
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)