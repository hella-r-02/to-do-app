package com.src.todo.domain.model

import java.util.*

sealed class TaskWithDate {
    class Task(
        val id: Long,
        val name: String,
        val date: Date?,
        val note: String?,
        val repeating: Long?,
        val folderId: Long
    ) : TaskWithDate()

    class DateTask(
        val date: String
    ) : TaskWithDate()

    companion object {
        fun convertTaskToTaskWithDate(task: com.src.todo.domain.model.Task): TaskWithDate =
            TaskWithDate.Task(
                id = task.id,
                name = task.name,
                note = task.note,
                date = task.date,
                repeating = task.repeating,
                folderId = task.folderId
            )
    }
}