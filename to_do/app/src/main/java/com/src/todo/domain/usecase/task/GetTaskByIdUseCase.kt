package com.src.todo.domain.usecase.task

import com.src.todo.domain.model.Task
import com.src.todo.domain.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetTaskByIdUseCase(private val taskRepository: TaskRepository) {
    suspend fun execute(taskId: Long): Flow<Task> = withContext(Dispatchers.IO) {
        return@withContext taskRepository.getTaskById(taskId)
    }
}