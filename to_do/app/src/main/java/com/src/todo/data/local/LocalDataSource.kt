package com.src.todo.data.local

import com.src.todo.domain.model.Folder
import com.src.todo.domain.model.FolderWithCountOfTasks
import com.src.todo.domain.model.Task
import kotlinx.coroutines.flow.Flow


interface LocalDataSource {
    fun insertFolder(name: String): Long
    fun getFoldersWithCountOfTasks(): Flow<List<FolderWithCountOfTasks>>
    fun getTasksByFolderId(folderId: Long): Flow<List<Task>>
    fun getTaskById(taskId: Long): Flow<Task>
    fun updateTask(task: Task)
    fun deleteFolder(folder: Folder)
    fun insertTask(task: Task)
    fun deleteTask(id: Long)
    fun updateFolderNameByFolderId(name: String, folderId: Long)
    fun getAllTasksWithoutFolder(): Flow<List<Task>>
    fun getCountOfTasksWithoutFolder(): Flow<Long>
}