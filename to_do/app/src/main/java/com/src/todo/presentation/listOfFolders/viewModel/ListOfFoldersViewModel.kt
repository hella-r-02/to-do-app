package com.src.todo.presentation.listOfFolders.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.src.todo.domain.model.FolderWithCountOfTasks
import com.src.todo.domain.usecase.GetListOfFoldersWithCountTasksUseCase
import kotlinx.coroutines.launch

class ListOfFoldersViewModel(private val getListOfFoldersWithCountTasksUseCase: GetListOfFoldersWithCountTasksUseCase) :
    ViewModel() {
    private val _mutableLiveDataFolders = MutableLiveData<List<FolderWithCountOfTasks>>(emptyList())
    val liveDataMovies get() = _mutableLiveDataFolders

    fun getFolders() {
        viewModelScope.launch {
            getListOfFoldersWithCountTasksUseCase.execute()
                .collect { item -> _mutableLiveDataFolders.value = item }
        }
    }
}