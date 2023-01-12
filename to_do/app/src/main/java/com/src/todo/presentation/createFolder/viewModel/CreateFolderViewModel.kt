package com.src.todo.presentation.createFolder.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.src.todo.domain.usecase.folder.InsertFolderUseCase
import com.src.todo.presentation.utils.State
import kotlinx.coroutines.launch

class CreateFolderViewModel(private val insertFolderUseCase: InsertFolderUseCase) : ViewModel() {
    private val _mutableLiveDataInsertFoldersState =
        MutableLiveData<State<Long>>(State.EmptyState())
    val liveDataInsertFoldersState get() = _mutableLiveDataInsertFoldersState
    fun createFolder(name: String) {
        viewModelScope.launch {
            _mutableLiveDataInsertFoldersState.value = State.LoadingState()
            _mutableLiveDataInsertFoldersState.value =
                State.SuccessState(insertFolderUseCase.execute(name))
        }
    }

    fun setDefaultValue() {
        _mutableLiveDataInsertFoldersState.value = State.EmptyState()
    }
}