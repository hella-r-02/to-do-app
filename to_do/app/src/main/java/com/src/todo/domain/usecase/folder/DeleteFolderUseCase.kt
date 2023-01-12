package com.src.todo.domain.usecase.folder

import com.src.todo.domain.model.FolderWithCountOfTasks
import com.src.todo.domain.repository.FolderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteFolderUseCase(private val folderRepository: FolderRepository) {
    suspend fun execute(folderWithCountOfTasks: FolderWithCountOfTasks) =
        withContext(Dispatchers.IO) {
            folderRepository.deleteFolder(folderWithCountOfTasks.folder)
        }
}