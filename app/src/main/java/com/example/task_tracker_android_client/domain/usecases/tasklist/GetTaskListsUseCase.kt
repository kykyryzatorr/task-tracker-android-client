package com.example.task_tracker_android_client.domain.usecases.tasklist

import com.example.taskmanager.domain.models.TaskList
import com.example.taskmanager.domain.repositories.ITaskListRepository
import com.example.taskmanager.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

class GetTaskListsUseCase(
    private val repository: ITaskListRepository
) {
    operator fun invoke(): Flow<Resource<List<TaskList>>> = repository.getTaskLists()
}