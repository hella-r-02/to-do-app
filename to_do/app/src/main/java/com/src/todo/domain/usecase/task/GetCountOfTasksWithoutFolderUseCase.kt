package com.src.todo.domain.usecase.task

import com.src.todo.domain.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetCountOfTasksWithoutFolderUseCase(private val taskRepository: TaskRepository) {
    suspend fun execute(): Flow<Long> = withContext(Dispatchers.IO) {
        return@withContext taskRepository.getCountOfTasksWithoutFolder()
    }
}