package com.example.task_tracker_android_client.data.mappers

import com.example.taskmanager.data.models.TaskListDto
import com.example.taskmanager.domain.models.TaskList

class TaskListMapper(
    private val taskMapper: TaskMapper
) {
    fun toDomain(dto: TaskListDto): TaskList {
        return TaskList(
            id = dto.id,
            title = dto.title,
            description = dto.description,
            count = dto.count,
            progress = dto.progress,
            tasks = dto.tasks?.let { taskMapper.toDomainList(it) }
        )
    }

    fun toDto(domain: TaskList): TaskListDto {
        return TaskListDto(
            id = domain.id,
            title = domain.title,
            description = domain.description,
            count = domain.count,
            progress = domain.progress,
            tasks = domain.tasks?.let { taskMapper.toDtoList(it) }
        )
    }

    fun toDomainList(dtos: List<TaskListDto>): List<TaskList> = dtos.map { toDomain(it) }
    fun toDtoList(domains: List<TaskList>): List<TaskListDto> = domains.map { toDto(it) }
}