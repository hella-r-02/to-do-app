package com.src.todo.presentation.listOfTasksWithoutFolder.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.src.todo.domain.model.TaskWithDate
import com.src.todo.domain.usecase.task.DeleteTaskUseCase
import com.src.todo.domain.usecase.task.GetTasksWithoutFolderUseCase
import com.src.todo.presentation.utils.State
import kotlinx.coroutines.launch

class ListOfTasksWithoutFolderViewModel(
    private val getTasksWithoutFolderUseCase: GetTasksWithoutFolderUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) :
    ViewModel() {
    private val _mutableLiveDataLoadTasksState =
        MutableLiveData<State<List<TaskWithDate>>>(State.EmptyState())
    val liveDataLoadTasksState get() = _mutableLiveDataLoadTasksState


    fun getTasks() {
        viewModelScope.launch {
            _mutableLiveDataLoadTasksState.value = State.LoadingState()
            getTasksWithoutFolderUseCase.execute()
                .collect { _mutableLiveDataLoadTasksState.value = State.SuccessState(it) }
        }
    }

    fun deleteTask(id: Long) {
        viewModelScope.launch {
            deleteTaskUseCase.execute(id)
        }
    }
}