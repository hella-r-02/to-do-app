package com.src.todo.presentation.listOfFolders.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.src.todo.domain.model.FolderWithCountOfTasks
import com.src.todo.domain.usecase.folder.DeleteFolderUseCase
import com.src.todo.domain.usecase.folder.GetListOfFoldersWithCountTasksUseCase
import com.src.todo.domain.usecase.task.GetCountOfTasksWithoutFolderUseCase
import com.src.todo.presentation.utils.State
import kotlinx.coroutines.launch

class ListOfFoldersViewModel(
    private val getListOfFoldersWithCountTasksUseCase: GetListOfFoldersWithCountTasksUseCase,
    private val deleteFolderUseCase: DeleteFolderUseCase,
    private val getCountOfTasksWithoutFolderUseCase: GetCountOfTasksWithoutFolderUseCase
) :
    ViewModel() {
    private val _mutableLiveDataLoadFoldersState =
        MutableLiveData<State<List<FolderWithCountOfTasks>>>(State.EmptyState())
    private val _mutableLiveDataCountOfTasksWithoutFolderState =
        MutableLiveData<State<Long>>(State.EmptyState())
    val liveDataLoadFoldersState get() = _mutableLiveDataLoadFoldersState
    val liveDataCountOfTasksWithoutFolderState get() = _mutableLiveDataCountOfTasksWithoutFolderState

    fun getFolders() {
        viewModelScope.launch {
            getListOfFoldersWithCountTasksUseCase.execute()
                .collect { item ->
                    _mutableLiveDataLoadFoldersState.value = State.SuccessState(item)
                }
        }
    }

    fun getCountOfTasksWithoutFolders() {
        viewModelScope.launch {
            _mutableLiveDataCountOfTasksWithoutFolderState.value = State.LoadingState()
            getCountOfTasksWithoutFolderUseCase.execute()
                .collect { item ->
                    _mutableLiveDataCountOfTasksWithoutFolderState.value = State.SuccessState(item)
                }
        }
    }

    fun deleteFolder(folder: FolderWithCountOfTasks) {
        viewModelScope.launch {
            deleteFolderUseCase.execute(folder)
        }
    }
}