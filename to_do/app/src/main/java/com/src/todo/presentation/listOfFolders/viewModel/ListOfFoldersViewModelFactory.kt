package com.src.todo.presentation.listOfFolders.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.src.todo.domain.usecase.folder.GetListOfFoldersWithCountTasksUseCase
import javax.inject.Inject

class ListOfFoldersViewModelFactory @Inject constructor(private val getListOfFoldersWithCountTasksUseCase: GetListOfFoldersWithCountTasksUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ListOfFoldersViewModel(getListOfFoldersWithCountTasksUseCase) as T
    }
}