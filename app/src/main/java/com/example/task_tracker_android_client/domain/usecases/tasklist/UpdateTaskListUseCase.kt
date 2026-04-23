package com.example.task_tracker_android_client.domain.usecases.tasklist

import com.example.taskmanager.domain.models.TaskList
import com.example.taskmanager.domain.repositories.ITaskListRepository
import com.example.taskmanager.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class UpdateTaskListUseCase(
    private val repository: ITaskListRepository
) {
    operator fun invoke(id: UUID, taskList: TaskList): Flow<Resource<TaskList>> {
        require(taskList.title.isNotBlank()) { "Title cannot be blank" }
        return repository.updateTaskList(id, taskList)
    }
}