package com.src.todo.domain.usecase.task

import com.src.todo.domain.model.Task
import com.src.todo.domain.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class InsertTaskUseCase(private val taskRepository: TaskRepository) {
    suspend fun execute(name: String, date: Date?, note: String?, folderId: Long?) =
        withContext(Dispatchers.IO) {
            val task = Task(id = 0, name = name, date = date, folderId = folderId, note = note)
            taskRepository.insertTask(task)
        }
}