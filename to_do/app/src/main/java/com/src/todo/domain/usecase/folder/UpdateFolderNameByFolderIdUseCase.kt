package com.src.todo.domain.usecase.folder

import com.src.todo.domain.repository.FolderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateFolderNameByFolderIdUseCase(private val folderRepository: FolderRepository) {
    suspend fun execute(name: String, id: Long) = withContext(Dispatchers.IO) {
        folderRepository.updateFolderNameByFolderId(name, id)
    }
}