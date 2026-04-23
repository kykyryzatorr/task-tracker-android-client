package com.example.task_tracker_android_client.data.repositories

import com.example.task_tracker_android_client.data.datasource.remote.TaskRemoteDataSource
import com.example.task_tracker_android_client.data.mappers.TaskMapper
import com.example.task_tracker_android_client.domain.models.Task
import com.example.task_tracker_android_client.domain.repositories.ITaskRepository
import com.example.task_tracker_android_client.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID

class TaskRepositoryImpl(
    private val remoteDataSource: TaskRemoteDataSource,
    private val mapper: TaskMapper
) : ITaskRepository {

    override fun getTasks(taskListId: UUID): Flow<Resource<List<Task>>> = flow {
        emit(Resource.Loading)
        try {
            val dtos = remoteDataSource.getTasks(taskListId)
            val domains = mapper.toDomainList(dtos)
            emit(Resource.Success(domains))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }

    override fun getTaskById(taskListId: UUID, taskId: UUID): Flow<Resource<Task>> = flow {
        emit(Resource.Loading)
        try {
            val dto = remoteDataSource.getTaskById(taskListId, taskId)
            val domain = mapper.toDomain(dto)
            emit(Resource.Success(domain))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }

    override fun createTask(taskListId: UUID, task: Task): Flow<Resource<Task>> = flow {
        emit(Resource.Loading)
        try {
            val dto = mapper.toDto(task)
            val created = remoteDataSource.createTask(taskListId, dto)
            val domain = mapper.toDomain(created)
            emit(Resource.Success(domain))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }

    override fun updateTask(taskListId: UUID, taskId: UUID, task: Task): Flow<Resource<Task>> = flow {
        emit(Resource.Loading)
        try {
            val dto = mapper.toDto(task)
            val updated = remoteDataSource.updateTask(taskListId, taskId, dto)
            val domain = mapper.toDomain(updated)
            emit(Resource.Success(domain))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }

    override fun deleteTask(taskListId: UUID, taskId: UUID): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)
        try {
            remoteDataSource.deleteTask(taskListId, taskId)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
}