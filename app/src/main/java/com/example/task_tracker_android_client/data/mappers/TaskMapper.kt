package com.example.task_tracker_android_client.data.mappers

import com.example.taskmanager.data.models.TaskDto
import com.example.taskmanager.domain.models.Task

class TaskMapper {
    fun toDomain(dto: TaskDto): Task {
        return Task(
            id = dto.id,
            title = dto.title,
            description = dto.description,
            dueDate = dto.dueDate,
            priority = dto.priority,
            status = dto.status
        )
    }

    fun toDto(domain: Task): TaskDto {
        return TaskDto(
            id = domain.id,
            title = domain.title,
            description = domain.description,
            dueDate = domain.dueDate,
            priority = domain.priority,
            status = domain.status
        )
    }

    fun toDomainList(dtos: List<TaskDto>): List<Task> = dtos.map { toDomain(it) }
    fun toDtoList(domains: List<Task>): List<TaskDto> = domains.map { toDto(it) }
}