package com.src.todo.domain.repository

import com.src.todo.domain.model.FolderWithCountOfTasks
import kotlinx.coroutines.flow.Flow

interface FolderRepository {
    suspend fun insertFolder(name:String)
    suspend fun getFoldersWithCountOfTasks(): Flow<List<FolderWithCountOfTasks>>
}