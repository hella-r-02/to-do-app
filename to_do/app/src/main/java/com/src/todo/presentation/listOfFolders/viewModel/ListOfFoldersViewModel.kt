package com.src.todo.presentation.listOfFolders.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.src.todo.domain.model.FolderWithCountOfTasks
import com.src.todo.domain.usecase.folder.GetListOfFoldersWithCountTasksUseCase
import com.src.todo.presentation.utils.State
import kotlinx.coroutines.launch

class ListOfFoldersViewModel(private val getListOfFoldersWithCountTasksUseCase: GetListOfFoldersWithCountTasksUseCase) :
    ViewModel() {
    private val _mutableLiveDataLoadFoldersState = MutableLiveData<State<List<FolderWithCountOfTasks>>>(State.EmptyState())
    val liveDataLoadFoldersState get() = _mutableLiveDataLoadFoldersState

    fun getFolders() {
        viewModelScope.launch {
            getListOfFoldersWithCountTasksUseCase.execute()
                .collect { item -> _mutableLiveDataLoadFoldersState.value = State.SuccessState(item) }
        }
    }
}