package com.example.task_tracker_android_client.domain.models

enum class TaskStatus {
    OPEN, CLOSED;

    companion object {
        fun fromString(value: String): TaskStatus {
            return when (value.uppercase()) {
                "OPEN" -> OPEN
                "CLOSED" -> CLOSED
                else -> OPEN
            }
        }

        fun getDisplayName(status: TaskStatus): String {
            return when (status) {
                OPEN -> "Открыта"
                CLOSED -> "Закрыта"
            }
        }
    }
}