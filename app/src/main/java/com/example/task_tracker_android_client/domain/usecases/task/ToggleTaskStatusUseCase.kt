package com.example.task_tracker_android_client.domain.usecases.task

import com.example.task_tracker_android_client.domain.models.Task
import com.example.task_tracker_android_client.domain.models.TaskStatus
import com.example.task_tracker_android_client.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import java.util.UUID

class ToggleTaskStatusUseCase(
    private val updateTaskUseCase: UpdateTaskUseCase
) {
    operator fun invoke(taskListId: UUID, task: Task): Flow<Resource<Task>> = flow {
        val updatedTask = task.copy(
            status = if (task.status == TaskStatus.OPEN) TaskStatus.CLOSED else TaskStatus.OPEN
        )
        emitAll(updateTaskUseCase(taskListId, task.id!!, updatedTask))
    }
}