package com.example.task_tracker_android_client.domain.usecases.task

import com.example.taskmanager.domain.models.Task
import com.example.taskmanager.domain.repositories.ITaskRepository
import com.example.taskmanager.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class GetTasksUseCase(
    private val repository: ITaskRepository
) {
    operator fun invoke(taskListId: UUID): Flow<Resource<List<Task>>> = repository.getTasks(taskListId)
}