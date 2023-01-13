package com.src.todo.presentation.addTask.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.src.todo.domain.usecase.task.InsertTaskUseCase
import javax.inject.Inject

class AddTaskViewModelFactory @Inject constructor(
    private val insertTaskUseCase: InsertTaskUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddTaskViewModel(
            insertTaskUseCase = insertTaskUseCase
        ) as T
    }
}