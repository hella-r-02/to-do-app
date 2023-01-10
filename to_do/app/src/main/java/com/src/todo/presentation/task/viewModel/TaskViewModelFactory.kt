package com.src.todo.presentation.task.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.src.todo.domain.usecase.task.GetTaskByIdUseCase
import com.src.todo.domain.usecase.task.UpdateTaskUseCase
import javax.inject.Inject

class TaskViewModelFactory @Inject constructor(
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TaskViewModel(
            getTaskByIdUseCase = getTaskByIdUseCase,
            updateTaskUseCase = updateTaskUseCase
        ) as T
    }
}