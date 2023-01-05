package com.src.todo.data

import com.src.todo.data.local.LocalDataSource
import com.src.todo.domain.model.Task
import com.src.todo.domain.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class TaskRepositoryImpl(private val localDataSource: LocalDataSource) : TaskRepository {
    override suspend fun getTasksByFolderId(folderId: Long): Flow<List<Task>> =
        withContext(Dispatchers.IO) {
            return@withContext localDataSource.getTasksByFolderId(folderId)
        }
}