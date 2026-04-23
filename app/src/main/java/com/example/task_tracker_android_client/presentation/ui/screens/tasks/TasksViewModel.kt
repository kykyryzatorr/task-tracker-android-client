package com.example.task_tracker_android_client.presentation.ui.screens.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task_tracker_android_client.domain.models.Task
import com.example.task_tracker_android_client.domain.models.TaskPriority
import com.example.task_tracker_android_client.domain.models.TaskStatus
import com.example.task_tracker_android_client.domain.usecases.task.CreateTaskUseCase
import com.example.task_tracker_android_client.domain.usecases.task.DeleteTaskUseCase
import com.example.task_tracker_android_client.domain.usecases.task.GetTasksUseCase
import com.example.task_tracker_android_client.domain.usecases.task.ToggleTaskStatusUseCase
import com.example.task_tracker_android_client.domain.usecases.task.UpdateTaskUseCase
import com.example.task_tracker_android_client.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val createTaskUseCase: CreateTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val toggleTaskStatusUseCase: ToggleTaskStatusUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TasksState())
    val state: StateFlow<TasksState> = _state.asStateFlow()

    fun loadTasks(taskListId: UUID) {
        viewModelScope.launch {
            getTasksUseCase.invoke(taskListId).collect { resource: Resource<List<Task>> ->                when (resource) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                    is Resource.Success<*> -> {
                        _state.value = _state.value.copy(
                            tasks = resource.data as List<Task>,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = resource.message
                        )
                    }
                }
            }
        }
    }

    fun createTask(
        taskListId: UUID,
        title: String,
        description: String?,
        dueDate: LocalDateTime?,
        priority: TaskPriority
    ) {
        viewModelScope.launch {
            val newTask = Task(
                title = title,
                description = description,
                dueDate = dueDate,
                priority = priority,
                status = TaskStatus.OPEN
            )
            createTaskUseCase.invoke(taskListId, newTask).collect { resource: Resource<*> ->
                if (resource is Resource.Success<*>) {
                    loadTasks(taskListId)
                }
            }
        }
    }

    fun updateTask(taskListId: UUID, task: Task) {
        viewModelScope.launch {
            task.id?.let { taskId ->
                updateTaskUseCase.invoke(taskListId, taskId, task).collect { resource: Resource<*> ->
                    if (resource is Resource.Success<*>) {
                        loadTasks(taskListId)
                    }
                }
            }
        }
    }

    fun deleteTask(taskListId: UUID, taskId: UUID) {
        viewModelScope.launch {
            deleteTaskUseCase(taskListId, taskId).collect { resource: Resource<*> ->
                if (resource is Resource.Success) {
                    loadTasks(taskListId)
                }
            }
        }
    }

    fun toggleTaskStatus(taskListId: UUID, task: Task) {
        viewModelScope.launch {
            toggleTaskStatusUseCase(taskListId, task).collect { resource ->
                if (resource is Resource.Success) {
                    loadTasks(taskListId)
                }
            }
        }
    }

    fun updateTaskPriority(taskListId: UUID, task: Task, newPriority: TaskPriority) {
        val updatedTask = task.copy(priority = newPriority)
        updateTask(taskListId, updatedTask)
    }
}