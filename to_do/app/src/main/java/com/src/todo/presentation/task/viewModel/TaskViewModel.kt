package com.src.todo.presentation.task.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.src.todo.domain.model.Task
import com.src.todo.domain.usecase.task.GetTaskByIdUseCase
import com.src.todo.domain.usecase.task.UpdateTaskUseCase
import com.src.todo.presentation.utils.State
import kotlinx.coroutines.launch

class TaskViewModel(
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase
) : ViewModel() {
    private val _mutableLiveDataLoadTaskState = MutableLiveData<State<Task>>(State.EmptyState())
    val liveDataLoadTaskState get() = _mutableLiveDataLoadTaskState

    fun getTask(taskId: Long) {
        viewModelScope.launch {
            _mutableLiveDataLoadTaskState.value = State.LoadingState()
            getTaskByIdUseCase.execute(taskId)
                .collect { item -> _mutableLiveDataLoadTaskState.value = State.SuccessState(item) }

        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            updateTaskUseCase.execute(task)
        }
    }

    fun updateNote(note: String?) {
        viewModelScope.launch {
            if (_mutableLiveDataLoadTaskState.value is State.SuccessState) {
                val task = (_mutableLiveDataLoadTaskState.value as State.SuccessState<Task>).data
                task.note = note
                updateTaskUseCase.execute(task)
                _mutableLiveDataLoadTaskState.value = State.SuccessState(task)
            }
        }
    }
}