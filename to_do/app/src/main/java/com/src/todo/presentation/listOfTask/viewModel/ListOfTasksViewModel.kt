package com.src.todo.presentation.listOfTask.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.src.todo.domain.model.TaskWithDate
import com.src.todo.domain.usecase.folder.UpdateFolderNameByFolderIdUseCase
import com.src.todo.domain.usecase.task.DeleteTaskUseCase
import com.src.todo.domain.usecase.task.GetTasksByFolderIdUseCase
import com.src.todo.presentation.utils.State
import kotlinx.coroutines.launch

class ListOfTasksViewModel(
    private val getTasksByFolderIdUseCase: GetTasksByFolderIdUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val updateFolderNameByFolderIdUseCase: UpdateFolderNameByFolderIdUseCase
) :
    ViewModel() {
    private val _mutableLiveDataLoadTasksState =
        MutableLiveData<State<List<TaskWithDate>>>(State.EmptyState())
    val liveDataLoadTasksState get() = _mutableLiveDataLoadTasksState


    fun getTasks(folderId: Long) {
        viewModelScope.launch {
            _mutableLiveDataLoadTasksState.value = State.LoadingState()
            getTasksByFolderIdUseCase.execute(folderId)
                .collect { _mutableLiveDataLoadTasksState.value = State.SuccessState(it) }
        }
    }

    fun deleteTask(id: Long, folderId: Long) {
        viewModelScope.launch {
            deleteTaskUseCase.execute(id)
            getTasks(folderId)
        }
    }

    fun updateFolderName(name: String, folderId: Long) {
        viewModelScope.launch {
            updateFolderNameByFolderIdUseCase.execute(name, folderId)
        }
    }
}