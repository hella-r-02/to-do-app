package com.src.todo.presentation.createFolder.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.src.todo.domain.usecase.folder.InsertFolderUseCase
import javax.inject.Inject

class CreateFolderViewModelFactory @Inject constructor(private val insertFolderUseCase: InsertFolderUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreateFolderViewModel(insertFolderUseCase = insertFolderUseCase) as T
    }
}