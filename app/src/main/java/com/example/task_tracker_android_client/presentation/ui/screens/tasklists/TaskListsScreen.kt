package com.example.task_tracker_android_client.presentation.ui.screens.tasklists

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.task_tracker_android_client.domain.models.TaskList
import com.example.task_tracker_android_client.presentation.ui.components.CreateTaskListDialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListsScreen(
    onTaskListSelected: (TaskList) -> Unit,
    viewModel: TaskListsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var showCreateDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.loadTaskLists()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showCreateDialog = true }) {
                Text("+")
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                state.error != null -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = state.error ?: "Unknown error")
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.loadTaskLists() }) {
                            Text("Повторить")
                        }
                    }
                }
                state.taskLists.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Нет списков задач")
                            Button(onClick = { showCreateDialog = true }) {
                                Text("Создать первый список")
                            }
                        }
                    }
                }
                else -> {
                    LazyColumn {
                        items(state.taskLists) { taskList ->
                            TaskListCard(
                                taskList = taskList,
                                onClick = { onTaskListSelected(taskList) },
                                onDelete = {
                                    taskList.id?.let { id ->
                                        viewModel.deleteTaskList(id)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    if (showCreateDialog) {
        CreateTaskListDialog(
            onDismiss = { showCreateDialog = false },
            onCreate = { title, description ->
                scope.launch {
                    viewModel.createTaskList(title, description)
                    showCreateDialog = false
                }
            }
        )
    }
}

@Composable
fun TaskListCard(
    taskList: TaskList,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = taskList.title,
                        style = MaterialTheme.typography.titleLarge
                    )
                    taskList.description?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    if (taskList.count != null) {
                        Text(
                            text = "Задач: ${taskList.count}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    if (taskList.progress != null) {
                        LinearProgressIndicator(
                            progress = (taskList.progress / 100).toFloat(),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = "Прогресс: ${taskList.progress}%",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                IconButton(onClick = onDelete) {
                    Text("🗑️")
                }
            }
        }
    }
}