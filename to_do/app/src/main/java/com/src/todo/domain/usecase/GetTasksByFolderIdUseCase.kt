package com.src.todo.domain.usecase

import android.annotation.SuppressLint
import com.src.todo.domain.model.TaskWithDate
import com.src.todo.domain.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.util.*

class GetTasksByFolderIdUseCase(private val taskRepository: TaskRepository) {
    @SuppressLint("SimpleDateFormat")
    suspend fun execute(folderId: Long): List<TaskWithDate> = withContext(Dispatchers.IO) {
        val listOfTaskWithDate = ArrayList<TaskWithDate>()
        val tasks = taskRepository.getTasksByFolderId(folderId).first()
        var date: Date? = tasks[0].date
        listOfTaskWithDate.add(TaskWithDate.DateTask(date))
        tasks.forEach {
            if (date == null && it.date == null || it.date?.time == date?.time) {
                listOfTaskWithDate.add(TaskWithDate.convertTaskToTaskWithDate(it))
            } else {
                listOfTaskWithDate.add(TaskWithDate.DateTask(it.date))
                listOfTaskWithDate.add(TaskWithDate.convertTaskToTaskWithDate(it))
                date = it.date
            }
        }
        return@withContext listOfTaskWithDate
    }
}