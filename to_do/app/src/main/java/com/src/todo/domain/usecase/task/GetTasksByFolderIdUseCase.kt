package com.src.todo.domain.usecase.task

import android.annotation.SuppressLint
import com.src.todo.domain.model.TaskWithDate
import com.src.todo.domain.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import java.util.*

class GetTasksByFolderIdUseCase(private val taskRepository: TaskRepository) {
    @SuppressLint("SimpleDateFormat")
    suspend fun execute(folderId: Long): Flow<List<TaskWithDate>> = withContext(Dispatchers.IO) {
        val listOfTaskWithDate = ArrayList<TaskWithDate>()
        val tasks = taskRepository.getTasksByFolderId(folderId).first()
        if (tasks.isNotEmpty()) {
            var date: Date? = tasks[0].date
            listOfTaskWithDate.add(TaskWithDate.DateTask(date))
            tasks.forEach {
                val dateCalendar = Calendar.getInstance()
                val itemCalendar = Calendar.getInstance()
                if (date != null) {
                    dateCalendar.time = date!!
                }
                if (it.date != null) {
                    itemCalendar.time = it.date!!
                }
                if (date == null && it.date == null ||
                    (date != null && it.date != null &&
                            (dateCalendar.get(Calendar.DAY_OF_MONTH) == itemCalendar.get(Calendar.DAY_OF_MONTH) &&
                                    dateCalendar.get(Calendar.MONTH) == itemCalendar.get(Calendar.MONTH) &&
                                    dateCalendar.get(Calendar.YEAR) == itemCalendar.get(Calendar.YEAR)))
                ) {
                    listOfTaskWithDate.add(TaskWithDate.convertTaskModelToTaskWithDateTaskModel(it))
                } else {
                    listOfTaskWithDate.add(TaskWithDate.DateTask(it.date))
                    listOfTaskWithDate.add(TaskWithDate.convertTaskModelToTaskWithDateTaskModel(it))
                    date = it.date
                }
            }
            return@withContext flowOf(listOfTaskWithDate)
        }
        return@withContext flowOf(emptyList())
    }
}