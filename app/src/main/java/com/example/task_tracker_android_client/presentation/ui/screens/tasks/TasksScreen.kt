package com.example.task_tracker_android_client.presentation.ui.screens.tasks

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
import com.example.task_tracker_android_client.domain.models.Task
import com.example.task_tracker_android_client.domain.models.TaskPriority
import com.example.task_tracker_android_client.domain.models.TaskStatus
import com.example.task_tracker_android_client.presentation.ui.components.CreateTaskDialog
import com.example.task_tracker_android_client.presentation.ui.components.PriorityChip
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(
    taskListId: UUID,
    onBackPressed: () -> Unit,
    viewModel: TasksViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var showCreateDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(taskListId) {
        viewModel.loadTasks(taskListId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Задачи") },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Text("←")
                    }
                }
            )
        },
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
                        Button(onClick = { viewModel.loadTasks(taskListId) }) {
                            Text("Повторить")
                        }
                    }
                }
                state.tasks.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Нет задач")
                            Button(onClick = { showCreateDialog = true }) {
                                Text("Создать первую задачу")
                            }
                        }
                    }
                }
                else -> {
                    LazyColumn {
                        items(state.tasks) { task ->
                            TaskItem(
                                task = task,
                                onToggleStatus = {
                                    viewModel.toggleTaskStatus(taskListId, task)
                                },
                                onDelete = {
                                    task.id?.let { taskId ->
                                        viewModel.deleteTask(taskListId, taskId)
                                    }
                                },
                                onPriorityChange = { newPriority ->
                                    viewModel.updateTaskPriority(taskListId, task, newPriority)
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    if (showCreateDialog) {
        CreateTaskDialog(
            onDismiss = { showCreateDialog = false },
            onCreate = { title, description, dueDate, priority ->
                scope.launch {
                    viewModel.createTask(taskListId, title, description, dueDate, priority)
                    showCreateDialog = false
                }
            }
        )
    }
}

@Composable
fun TaskItem(
    task: Task,
    onToggleStatus: () -> Unit,
    onDelete: () -> Unit,
    onPriorityChange: (TaskPriority) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = if (task.status == TaskStatus.CLOSED)
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        else
                            MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    PriorityChip(priority = task.priority)
                }

                task.description?.let {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (task.status == TaskStatus.CLOSED)
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        else
                            MaterialTheme.colorScheme.onSurface
                    )
                }

                task.dueDate?.let {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Срок: ${formatDate(it)}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Row {
                Checkbox(
                    checked = task.status == TaskStatus.CLOSED,
                    onCheckedChange = { onToggleStatus() }
                )
                IconButton(onClick = onDelete) {
                    Text("🗑️")
                }
            }
        }
    }
}

private fun formatDate(dateTime: LocalDateTime): String {
    return dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
}