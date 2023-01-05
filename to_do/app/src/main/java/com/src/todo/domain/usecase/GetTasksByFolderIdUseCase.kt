package com.src.todo.domain.usecase

import android.annotation.SuppressLint
import com.src.todo.domain.model.TaskWithDate
import com.src.todo.domain.repository.TaskRepository
import com.src.todo.presentation.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class GetTasksByFolderIdUseCase(private val taskRepository: TaskRepository) {
    @SuppressLint("SimpleDateFormat")
    suspend fun execute(folderId: Long): List<TaskWithDate> = withContext(Dispatchers.IO) {
        val listOfTaskWithDate = ArrayList<TaskWithDate>()
        val tasks = taskRepository.getTasksByFolderId(folderId).first()
        var date: Date? = tasks[0].date
        listOfTaskWithDate.add(TaskWithDate.DateTask(convertDateToString(date)))
        tasks.forEach {
            if (date == null && it.date == null || it.date?.time == date?.time) {
                listOfTaskWithDate.add(TaskWithDate.convertTaskToTaskWithDate(it))
            } else {
                listOfTaskWithDate.add(TaskWithDate.DateTask(convertDateToString(it.date)))
                listOfTaskWithDate.add(TaskWithDate.convertTaskToTaskWithDate(it))
                date = it.date
            }
        }
        return@withContext listOfTaskWithDate
    }


    @SuppressLint("SimpleDateFormat")
    suspend fun convertDateToString(date: Date?): String = withContext(Dispatchers.IO) {
        if (date == null) {
            return@withContext INDEFINITELY
        }
        val now = Calendar.getInstance(TimeZone.getDefault())
        val calendar = Calendar.getInstance(TimeZone.getDefault())
        calendar.time = date
        if (calendar.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
            if (calendar.get(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH) && calendar.get(
                    Calendar.MONTH
                ) == now.get(Calendar.MONTH)
            ) {
                val dateString = SimpleDateFormat(DATE_FORMAT).format(date)

                return@withContext "$TODAY, $dateString"
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            if (calendar.get(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH) && calendar.get(
                    Calendar.MONTH
                ) == now.get(Calendar.MONTH)
            ) {
                val dateString = SimpleDateFormat(DATE_FORMAT).format(date)

                return@withContext "$TOMORROW, $dateString"
            }
            return@withContext SimpleDateFormat(DATE_FORMAT).format(date)
        }
        return@withContext SimpleDateFormat(DATE_FORMAT_WITH_YEAR).format(date)
    }
}