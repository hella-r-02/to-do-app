package com.src.todo.domain.usecase.task

import android.annotation.SuppressLint
import com.src.todo.domain.model.TaskDateMapper
import com.src.todo.domain.model.TaskWithDate
import com.src.todo.domain.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class GetTasksByFolderIdUseCase(private val taskRepository: TaskRepository) {
    @SuppressLint("SimpleDateFormat")
    suspend fun execute(folderId: Long): Flow<List<TaskWithDate>> = withContext(Dispatchers.IO) {
        val tasks = taskRepository.getTasksByFolderId(folderId).first()
        return@withContext TaskDateMapper().mapTaskListTOTaskDateList(tasks)
    }
}