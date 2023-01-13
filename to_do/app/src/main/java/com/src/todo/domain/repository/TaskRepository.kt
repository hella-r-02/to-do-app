package com.src.todo.domain.repository

import com.src.todo.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun getTasksByFolderId(folderId: Long): Flow<List<Task>>
    suspend fun getTaskById(taskId: Long): Flow<Task>
    suspend fun updateTask(task: Task)
    suspend fun insertTask(task: Task)
}