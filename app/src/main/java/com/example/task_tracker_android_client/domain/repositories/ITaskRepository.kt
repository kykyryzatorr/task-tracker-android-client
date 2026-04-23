package com.example.task_tracker_android_client.domain.repositories

import com.example.task_tracker_android_client.domain.models.Task
import com.example.task_tracker_android_client.domain.utils.Resource
import com.example.taskmanager.domain.models.Task
import com.example.taskmanager.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface ITaskRepository {
    fun getTasks(taskListId: UUID): Flow<Resource<List<Task>>>
    fun getTaskById(taskListId: UUID, taskId: UUID): Flow<Resource<Task>>
    fun createTask(taskListId: UUID, task: Task): Flow<Resource<Task>>
    fun updateTask(taskListId: UUID, taskId: UUID, task: Task): Flow<Resource<Task>>
    fun deleteTask(taskListId: UUID, taskId: UUID): Flow<Resource<Unit>>
}