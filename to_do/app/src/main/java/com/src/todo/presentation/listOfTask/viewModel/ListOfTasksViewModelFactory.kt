package com.src.todo.presentation.listOfTask.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.src.todo.domain.usecase.task.GetTasksByFolderIdUseCase
import com.src.todo.domain.usecase.task.InsertTaskUseCase
import javax.inject.Inject

class ListOfTasksViewModelFactory @Inject constructor(
    private val getTasksByFolderIdUseCase: GetTasksByFolderIdUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ListOfTasksViewModel(
            getTasksByFolderIdUseCase = getTasksByFolderIdUseCase
        ) as T
    }
}