package com.example.task_tracker_android_client.domain.usecases.task

import com.example.taskmanager.domain.models.Task
import com.example.taskmanager.domain.models.TaskStatus
import com.example.taskmanager.domain.repositories.ITaskRepository
import com.example.taskmanager.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class CreateTaskUseCase(
    private val repository: ITaskRepository
) {
    operator fun invoke(taskListId: UUID, task: Task): Flow<Resource<Task>> {
        require(task.title.isNotBlank()) { "Task title cannot be blank" }
        val taskWithOpenStatus = task.copy(status = TaskStatus.OPEN)
        return repository.createTask(taskListId, taskWithOpenStatus)
    }
}