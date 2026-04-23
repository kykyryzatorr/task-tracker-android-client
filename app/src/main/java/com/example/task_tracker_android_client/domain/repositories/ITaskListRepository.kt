package com.example.task_tracker_android_client.domain.repositories

import com.example.task_tracker_android_client.domain.models.TaskList
import com.example.task_tracker_android_client.domain.utils.Resource
import com.example.taskmanager.domain.models.TaskList
import com.example.taskmanager.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface ITaskListRepository {
    fun getTaskLists(): Flow<Resource<List<TaskList>>>
    fun getTaskListById(id: UUID): Flow<Resource<TaskList>>
    fun createTaskList(taskList: TaskList): Flow<Resource<TaskList>>
    fun updateTaskList(id: UUID, taskList: TaskList): Flow<Resource<TaskList>>
    fun deleteTaskList(id: UUID): Flow<Resource<Unit>>
}