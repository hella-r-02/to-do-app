package com.src.todo.presentation.listOfTask.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.src.todo.domain.model.TaskWithDate
import com.src.todo.domain.usecase.task.GetTasksByFolderIdUseCase
import com.src.todo.presentation.utils.State
import kotlinx.coroutines.launch

class ListOfTasksViewModel(private val getTasksByFolderIdUseCase: GetTasksByFolderIdUseCase) :
    ViewModel() {
    private val _mutableLiveDataLoadTasksState =
        MutableLiveData<State<List<TaskWithDate>>>(State.EmptyState())
    val liveDataLoadTasksState get() = _mutableLiveDataLoadTasksState

    fun getTasks(folderId: Long) {
        viewModelScope.launch {
            _mutableLiveDataLoadTasksState.value = State.LoadingState()
            val tasks = getTasksByFolderIdUseCase.execute(folderId)
            if (tasks != null) {
                _mutableLiveDataLoadTasksState.value =
                    State.SuccessState(tasks)
            } else {
                _mutableLiveDataLoadTasksState.value = State.EmptyState()
            }
        }
    }
}