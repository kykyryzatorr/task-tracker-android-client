package com.example.task_tracker_android_client.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.task_tracker_android_client.presentation.ui.screens.tasklists.TaskListsScreen
import com.example.task_tracker_android_client.presentation.ui.screens.tasks.TasksScreen
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme()
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TaskManagerApp()
                }
            }
        }
    }
}

@Composable
fun TaskManagerApp() {
    var selectedTaskListId by remember { mutableStateOf<UUID?>(null) }

    if (selectedTaskListId == null) {
        TaskListsScreen(
            onTaskListSelected = { taskList ->
                selectedTaskListId = taskList.id
            }
        )
    } else {
        TasksScreen(
            taskListId = selectedTaskListId!!,
            onBackPressed = { selectedTaskListId = null }
        )
    }
}