package com.src.todo.data.local

import com.src.todo.domain.model.FolderWithCountOfTasks
import com.src.todo.domain.model.Task
import kotlinx.coroutines.flow.Flow


interface LocalDataSource {
    fun insertFolder(name: String)
    fun getFoldersWithCountOfTasks(): Flow<List<FolderWithCountOfTasks>>
    fun getTasksByFolderId(folderId: Long): Flow<List<Task>>
    fun getTaskById(taskId: Long): Flow<Task>
    fun updateTask(task: Task)
}