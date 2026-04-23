package com.example.task_tracker_android_client.presentation.ui.screens.tasklists

import com.example.task_tracker_android_client.domain.models.TaskList

data class TaskListsState(
    val taskLists: List<TaskList> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)