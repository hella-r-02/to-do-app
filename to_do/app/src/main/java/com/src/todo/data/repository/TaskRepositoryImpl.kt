package com.src.todo.data.repository

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

    override suspend fun getTaskById(taskId: Long): Flow<Task> = withContext(Dispatchers.IO) {
        return@withContext localDataSource.getTaskById(taskId)
    }

    override suspend fun updateTask(task: Task) = withContext(Dispatchers.IO) {
        localDataSource.updateTask(task)
    }

    override suspend fun insertTask(task: Task) = withContext(Dispatchers.IO) {
        localDataSource.insertTask(task)
    }

    override suspend fun deleteTask(id: Long) = withContext(Dispatchers.IO) {
        localDataSource.deleteTask(id)
    }

    override suspend fun getAllTasksWithoutFolder(): Flow<List<Task>> =
        withContext(Dispatchers.IO) {
            return@withContext localDataSource.getAllTasksWithoutFolder()
        }

    override suspend fun getCountOfTasksWithoutFolder(): Flow<Long> = withContext(Dispatchers.IO) {
        return@withContext localDataSource.getCountOfTasksWithoutFolder()
    }
}