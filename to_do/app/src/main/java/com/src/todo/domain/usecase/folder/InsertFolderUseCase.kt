package com.src.todo.domain.usecase.folder

import com.src.todo.domain.repository.FolderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InsertFolderUseCase(private val folderRepository: FolderRepository) {
    suspend fun execute(name: String): Long = withContext(Dispatchers.IO) {
        return@withContext folderRepository.insertFolder(name)
    }
}