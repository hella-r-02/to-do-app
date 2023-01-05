package com.src.todo.domain.repository

import com.src.todo.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun getTasksByFolderId(folderId: Long): Flow<List<Task>>
}