package com.src.todo.domain.usecase.task

import com.src.todo.domain.model.TaskDateMapper
import com.src.todo.domain.model.TaskWithDate
import com.src.todo.domain.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class GetTasksWithoutFolderUseCase(private val taskRepository: TaskRepository) {
    suspend fun execute(): Flow<List<TaskWithDate>> = withContext(Dispatchers.IO) {
        val tasks = taskRepository.getAllTasksWithoutFolder().first()
        return@withContext TaskDateMapper().mapTaskListTOTaskDateList(tasks)
    }
}
