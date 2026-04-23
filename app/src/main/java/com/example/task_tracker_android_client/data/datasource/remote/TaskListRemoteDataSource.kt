package com.example.task_tracker_android_client.data.datasource.remote

import com.example.task_tracker_android_client.data.models.TaskListDto
import java.util.UUID

class TaskListRemoteDataSource(
    private val apiService: ApiService = NetworkModule.apiService
) {
    suspend fun getTaskLists(): List<TaskListDto> = apiService.getTaskLists()

    suspend fun getTaskListById(id: UUID): TaskListDto = apiService.getTaskListById(id)

    suspend fun createTaskList(taskList: TaskListDto): TaskListDto = apiService.createTaskList(taskList)

    suspend fun updateTaskList(id: UUID, taskList: TaskListDto): TaskListDto =
        apiService.updateTaskList(id, taskList)

    suspend fun deleteTaskList(id: UUID): Unit = apiService.deleteTaskList(id)
}