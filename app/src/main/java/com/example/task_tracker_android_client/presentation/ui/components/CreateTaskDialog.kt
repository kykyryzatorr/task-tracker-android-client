package com.example.task_tracker_android_client.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.task_tracker_android_client.domain.models.TaskPriority
import java.time.LocalDateTime

@Composable
fun CreateTaskDialog(
    onDismiss: () -> Unit,
    onCreate: (String, String?, LocalDateTime?, TaskPriority) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var hasDueDate by remember { mutableStateOf(false) }
    var dueDate by remember { mutableStateOf(LocalDateTime.now().plusDays(1)) }
    var selectedPriority by remember { mutableStateOf(TaskPriority.MEDIUM) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Создать задачу") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Название") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Описание (необязательно)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text("Приоритет:")
                Spacer(modifier = Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TaskPriority.values().forEach { priority ->
                        FilterChip(
                            selected = selectedPriority == priority,
                            onClick = { selectedPriority = priority },
                            label = { Text(TaskPriority.getDisplayName(priority)) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = hasDueDate,
                        onCheckedChange = { hasDueDate = it }
                    )
                    Text("Установить срок")
                }

                if (hasDueDate) {
                    Spacer(modifier = Modifier.height(8.dp))
                    SimpleDatePicker(
                        date = dueDate,
                        onDateChange = { dueDate = it }
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onCreate(
                        title,
                        description.ifEmpty { null },
                        if (hasDueDate) dueDate else null,
                        selectedPriority
                    )
                },
                enabled = title.isNotBlank()
            ) {
                Text("Создать")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}

@Composable
fun SimpleDatePicker(
    date: LocalDateTime,
    onDateChange: (LocalDateTime) -> Unit
) {
    var selectedDate by remember { mutableStateOf(date) }

    Column {
        Text("Дата и время:")
        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            value = selectedDate.toString(),
            onValueChange = {},
            readOnly = true,
            label = { Text("Выберите дату и время") },
            trailingIcon = {
                IconButton(onClick = {
                    // В реальном приложении здесь был бы DatePicker
                    // Для простоты оставим текущую дату + 1 день
                    onDateChange(LocalDateTime.now().plusDays(1))
                }) {
                    Text("📅")
                }
            }
        )
    }
}