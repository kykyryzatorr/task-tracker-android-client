package com.example.task_tracker_android_client.data.datasource.remote

import com.example.task_tracker_android_client.data.models.TaskDto
import java.util.UUID

class TaskRemoteDataSource(
    private val apiService: ApiService = NetworkModule.apiService
) {
    suspend fun getTasks(taskListId: UUID): List<TaskDto> = apiService.getTasks(taskListId)

    suspend fun getTaskById(taskListId: UUID, taskId: UUID): TaskDto =
        apiService.getTaskById(taskListId, taskId)

    suspend fun createTask(taskListId: UUID, task: TaskDto): TaskDto =
        apiService.createTask(taskListId, task)

    suspend fun updateTask(taskListId: UUID, taskId: UUID, task: TaskDto): TaskDto =
        apiService.updateTask(taskListId, taskId, task)

    suspend fun deleteTask(taskListId: UUID, taskId: UUID): Unit =
        apiService.deleteTask(taskListId, taskId)
}