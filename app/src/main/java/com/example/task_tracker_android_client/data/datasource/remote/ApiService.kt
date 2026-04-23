package com.example.task_tracker_android_client.data.datasource.remote

import com.example.task_tracker_android_client.data.models.TaskDto
import com.example.task_tracker_android_client.data.models.TaskListDto
import retrofit2.http.*
import java.util.UUID

interface ApiService {
    @GET("task-lists")
    suspend fun getTaskLists(): List<TaskListDto>

    @POST("task-lists")
    suspend fun createTaskList(@Body taskList: TaskListDto): TaskListDto

    @GET("task-lists/{task_list_id}")
    suspend fun getTaskListById(@Path("task_list_id") taskListId: UUID): TaskListDto

    @PUT("task-lists/{task_list_id}")
    suspend fun updateTaskList(
        @Path("task_list_id") taskListId: UUID,
        @Body taskList: TaskListDto
    ): TaskListDto

    @DELETE("task-lists/{task_list_id}")
    suspend fun deleteTaskList(@Path("task_list_id") taskListId: UUID): Unit

    @GET("task-lists/{task_list_id}/tasks")
    suspend fun getTasks(@Path("task_list_id") taskListId: UUID): List<TaskDto>

    @POST("task-lists/{task_list_id}/tasks")
    suspend fun createTask(
        @Path("task_list_id") taskListId: UUID,
        @Body task: TaskDto
    ): TaskDto

    @GET("task-lists/{task_list_id}/tasks/{task_id}")
    suspend fun getTaskById(
        @Path("task_list_id") taskListId: UUID,
        @Path("task_id") taskId: UUID
    ): TaskDto

    @PUT("task-lists/{task_list_id}/tasks/{task_id}")
    suspend fun updateTask(
        @Path("task_list_id") taskListId: UUID,
        @Path("task_id") taskId: UUID,
        @Body task: TaskDto
    ): TaskDto

    @DELETE("task-lists/{task_list_id}/tasks/{task_id}")
    suspend fun deleteTask(
        @Path("task_list_id") taskListId: UUID,
        @Path("task_id") taskId: UUID
    ): Unit
}