package com.example.task_tracker_android_client.data.di

import com.example.task_tracker_android_client.data.datasource.remote.TaskListRemoteDataSource
import com.example.task_tracker_android_client.data.datasource.remote.TaskRemoteDataSource
import com.example.task_tracker_android_client.data.mappers.TaskListMapper
import com.example.task_tracker_android_client.data.mappers.TaskMapper
import com.example.task_tracker_android_client.data.repositories.TaskListRepositoryImpl
import com.example.task_tracker_android_client.data.repositories.TaskRepositoryImpl
import com.example.task_tracker_android_client.domain.repositories.ITaskListRepository
import com.example.task_tracker_android_client.domain.repositories.ITaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideTaskMapper(): TaskMapper = TaskMapper()

    @Provides
    @Singleton
    fun provideTaskListMapper(taskMapper: TaskMapper): TaskListMapper = TaskListMapper(taskMapper)

    @Provides
    @Singleton
    fun provideTaskListRemoteDataSource(): TaskListRemoteDataSource = TaskListRemoteDataSource()

    @Provides
    @Singleton
    fun provideTaskRemoteDataSource(): TaskRemoteDataSource = TaskRemoteDataSource()

    @Provides
    @Singleton
    fun provideTaskListRepository(
        remoteDataSource: TaskListRemoteDataSource,
        mapper: TaskListMapper
    ): ITaskListRepository = TaskListRepositoryImpl(remoteDataSource, mapper)

    @Provides
    @Singleton
    fun provideTaskRepository(
        remoteDataSource: TaskRemoteDataSource,
        mapper: TaskMapper
    ): ITaskRepository = TaskRepositoryImpl(remoteDataSource, mapper)
}