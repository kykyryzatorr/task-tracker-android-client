package com.example.task_tracker_android_client.data.repositories

import com.example.task_tracker_android_client.data.datasource.remote.TaskListRemoteDataSource
import com.example.task_tracker_android_client.data.mappers.TaskListMapper
import com.example.task_tracker_android_client.domain.models.TaskList
import com.example.task_tracker_android_client.domain.repositories.ITaskListRepository
import com.example.task_tracker_android_client.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID

class TaskListRepositoryImpl(
    private val remoteDataSource: TaskListRemoteDataSource,
    private val mapper: TaskListMapper
) : ITaskListRepository {

    override fun getTaskLists(): Flow<Resource<List<TaskList>>> = flow {
        emit(Resource.Loading)
        try {
            val dtos = remoteDataSource.getTaskLists()
            val domains = mapper.toDomainList(dtos)
            emit(Resource.Success(domains))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }

    override fun getTaskListById(id: UUID): Flow<Resource<TaskList>> = flow {
        emit(Resource.Loading)
        try {
            val dto = remoteDataSource.getTaskListById(id)
            val domain = mapper.toDomain(dto)
            emit(Resource.Success(domain))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }

    override fun createTaskList(taskList: TaskList): Flow<Resource<TaskList>> = flow {
        emit(Resource.Loading)
        try {
            val dto = mapper.toDto(taskList)
            val created = remoteDataSource.createTaskList(dto)
            val domain = mapper.toDomain(created)
            emit(Resource.Success(domain))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }

    override fun updateTaskList(id: UUID, taskList: TaskList): Flow<Resource<TaskList>> = flow {
        emit(Resource.Loading)
        try {
            val dto = mapper.toDto(taskList)
            val updated = remoteDataSource.updateTaskList(id, dto)
            val domain = mapper.toDomain(updated)
            emit(Resource.Success(domain))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }

    override fun deleteTaskList(id: UUID): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)
        try {
            remoteDataSource.deleteTaskList(id)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
}