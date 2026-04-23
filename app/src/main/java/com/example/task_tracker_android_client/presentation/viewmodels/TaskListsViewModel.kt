package com.example.task_tracker_android_client.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task_tracker_android_client.domain.models.TaskList
import com.example.task_tracker_android_client.domain.usecases.tasklist.CreateTaskListUseCase
import com.example.task_tracker_android_client.domain.usecases.tasklist.DeleteTaskListUseCase
import com.example.task_tracker_android_client.domain.usecases.tasklist.GetTaskListsUseCase
import com.example.task_tracker_android_client.domain.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class TaskListsViewModel(
    private val getTaskListsUseCase: GetTaskListsUseCase,
    private val createTaskListUseCase: CreateTaskListUseCase,
    private val deleteTaskListUseCase: DeleteTaskListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TaskListsState())
    val state: StateFlow<TaskListsState> = _state.asStateFlow()

    fun loadTaskLists() {
        viewModelScope.launch {
            getTaskListsUseCase().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            taskLists = resource.data,
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

    fun createTaskList(title: String, description: String?) {
        viewModelScope.launch {
            val newTaskList = TaskList(
                title = title,
                description = description
            )
            createTaskListUseCase(newTaskList).collect { resource ->
                if (resource is Resource.Success) {
                    loadTaskLists()
                }
            }
        }
    }

    fun deleteTaskList(id: UUID) {
        viewModelScope.launch {
            deleteTaskListUseCase(id).collect { resource ->
                if (resource is Resource.Success) {
                    loadTaskLists()
                }
            }
        }
    }
}

data class TaskListsState(
    val taskLists: List<TaskList> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)