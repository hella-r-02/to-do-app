package com.src.todo.presentation.listOfFolders.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.src.todo.domain.usecase.folder.DeleteFolderUseCase
import com.src.todo.domain.usecase.folder.GetListOfFoldersWithCountTasksUseCase
import com.src.todo.domain.usecase.task.GetCountOfTasksWithoutFolderUseCase
import javax.inject.Inject

class ListOfFoldersViewModelFactory @Inject constructor(
    private val getListOfFoldersWithCountTasksUseCase: GetListOfFoldersWithCountTasksUseCase,
    private val deleteFolderUseCase: DeleteFolderUseCase,
    private val getCountOfTasksWithoutFolderUseCase: GetCountOfTasksWithoutFolderUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ListOfFoldersViewModel(
            getListOfFoldersWithCountTasksUseCase = getListOfFoldersWithCountTasksUseCase,
            deleteFolderUseCase = deleteFolderUseCase,
            getCountOfTasksWithoutFolderUseCase = getCountOfTasksWithoutFolderUseCase
        ) as T
    }
}