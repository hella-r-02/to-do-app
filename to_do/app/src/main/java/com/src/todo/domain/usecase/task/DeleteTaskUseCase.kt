package com.src.todo.domain.usecase.task

import com.src.todo.domain.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteTaskUseCase(private val taskRepository: TaskRepository) {
    suspend fun execute(id: Long) = withContext(Dispatchers.IO) {
        taskRepository.deleteTask(id)
    }
}