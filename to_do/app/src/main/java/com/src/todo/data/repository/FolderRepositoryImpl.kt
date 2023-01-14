package com.src.todo.data.repository

import com.src.todo.data.local.LocalDataSource
import com.src.todo.domain.model.Folder
import com.src.todo.domain.model.FolderWithCountOfTasks
import com.src.todo.domain.repository.FolderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class FolderRepositoryImpl(private val localDataSource: LocalDataSource) : FolderRepository {
    override suspend fun insertFolder(name: String): Long = withContext(Dispatchers.IO) {
        return@withContext localDataSource.insertFolder(name = name)
    }

    override suspend fun getFoldersWithCountOfTasks(): Flow<List<FolderWithCountOfTasks>> =
        withContext(Dispatchers.IO) {
            return@withContext localDataSource.getFoldersWithCountOfTasks()
        }

    override suspend fun deleteFolder(folder: Folder) = withContext(Dispatchers.IO) {
        localDataSource.deleteFolder(folder)
    }

    override suspend fun updateFolderNameByFolderId(name: String, id: Long) =
        withContext(Dispatchers.IO) {
            localDataSource.updateFolderNameByFolderId(name, id)
        }
}