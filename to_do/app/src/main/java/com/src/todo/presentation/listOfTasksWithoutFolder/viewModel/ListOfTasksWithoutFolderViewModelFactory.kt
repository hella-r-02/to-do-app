package com.src.todo.presentation.listOfTasksWithoutFolder.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.src.todo.domain.usecase.task.DeleteTaskUseCase
import com.src.todo.domain.usecase.task.GetTasksWithoutFolderUseCase
import javax.inject.Inject

class ListOfTasksWithoutFolderViewModelFactory @Inject constructor(
    private val getTasksWithoutFolderUseCase: GetTasksWithoutFolderUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ListOfTasksWithoutFolderViewModel(
            getTasksWithoutFolderUseCase = getTasksWithoutFolderUseCase,
            deleteTaskUseCase = deleteTaskUseCase
        ) as T
    }
}