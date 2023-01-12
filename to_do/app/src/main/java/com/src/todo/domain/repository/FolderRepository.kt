package com.src.todo.domain.repository

import com.src.todo.domain.model.Folder
import com.src.todo.domain.model.FolderWithCountOfTasks
import kotlinx.coroutines.flow.Flow

interface FolderRepository {
    suspend fun insertFolder(name: String): Long
    suspend fun getFoldersWithCountOfTasks(): Flow<List<FolderWithCountOfTasks>>
    suspend fun deleteFolder(folder: Folder)
}