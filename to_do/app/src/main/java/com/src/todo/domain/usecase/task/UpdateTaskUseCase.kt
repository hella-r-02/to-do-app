package com.src.todo.domain.usecase.task

import com.src.todo.domain.model.Task
import com.src.todo.domain.repository.TaskRepository

class UpdateTaskUseCase(private val taskRepository: TaskRepository) {
    suspend fun execute(task: Task) {
        taskRepository.updateTask(task)
    }
}