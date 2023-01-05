package com.src.todo.presentation.listOfTask.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.src.todo.domain.model.TaskWithDate
import com.src.todo.domain.usecase.GetTasksByFolderIdUseCase
import kotlinx.coroutines.launch

class ListOfTasksViewModel(private val getTasksByFolderIdUseCase: GetTasksByFolderIdUseCase) :
    ViewModel() {
    private val _mutableLiveDataTasks = MutableLiveData<List<TaskWithDate>>(emptyList())
    val liveDataTasks get() = _mutableLiveDataTasks

    fun getTasks(folderId: Long) {
        viewModelScope.launch {
            _mutableLiveDataTasks.value = getTasksByFolderIdUseCase.execute(folderId)
        }
    }
}