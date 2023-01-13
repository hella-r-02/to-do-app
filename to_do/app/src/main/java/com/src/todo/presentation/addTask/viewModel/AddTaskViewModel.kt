package com.src.todo.presentation.addTask.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.src.todo.domain.usecase.task.InsertTaskUseCase
import kotlinx.coroutines.launch
import java.util.*

class AddTaskViewModel(
    private val insertTaskUseCase: InsertTaskUseCase
) : ViewModel() {
    private val _mutableLiveDataNote = MutableLiveData<String?>(null)
    private val _mutableLiveDataDate = MutableLiveData<Date?>(null)
    val liveDataNote get() = _mutableLiveDataNote
    val liveDataDate get() = _mutableLiveDataDate
    fun insertTask(name: String, folderId: Long) {
        viewModelScope.launch {
            insertTaskUseCase.execute(
                name = name,
                date = _mutableLiveDataDate.value,
                folderId = folderId,
                note = _mutableLiveDataNote.value
            )
            _mutableLiveDataNote.value = null
            _mutableLiveDataDate.value = null
        }
    }

    fun setNote(note: String?) {
        viewModelScope.launch {
            _mutableLiveDataNote.value = note
        }
    }

    fun setDate(date: Date?) {
        viewModelScope.launch {
            _mutableLiveDataDate.value = date
        }
    }

    fun getNote() = _mutableLiveDataNote.value
}