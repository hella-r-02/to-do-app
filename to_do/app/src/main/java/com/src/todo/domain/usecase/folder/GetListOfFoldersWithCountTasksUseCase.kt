package com.src.todo.domain.usecase.folder

import com.src.todo.domain.repository.FolderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetListOfFoldersWithCountTasksUseCase(private val folderRepository: FolderRepository) {
    suspend fun execute() = withContext(Dispatchers.IO) {
        return@withContext folderRepository.getFoldersWithCountOfTasks()
    }
}