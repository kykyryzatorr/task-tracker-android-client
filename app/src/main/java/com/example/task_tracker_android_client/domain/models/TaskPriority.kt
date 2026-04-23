package com.example.task_tracker_android_client.domain.models

enum class TaskPriority {
    HIGH, MEDIUM, LOW;

    companion object {
        fun fromString(value: String): TaskPriority {
            return when (value.uppercase()) {
                "HIGH" -> HIGH
                "MEDIUM" -> MEDIUM
                "LOW" -> LOW
                else -> MEDIUM
            }
        }

        fun getDisplayName(priority: TaskPriority): String {
            return when (priority) {
                HIGH -> "Высокий"
                MEDIUM -> "Средний"
                LOW -> "Низкий"
            }
        }
    }
}