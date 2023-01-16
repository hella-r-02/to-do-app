package com.src.todo.domain.model

import java.util.*

sealed class TaskWithDate {
    class Task(
        val id: Long,
        val name: String,
        val date: Date?,
        val note: String?,
        val folderId: Long?
    ) : TaskWithDate()

    class DateTask(
        val date: Date?
    ) : TaskWithDate()

    companion object {
        fun convertTaskModelToTaskWithDateTaskModel(task: com.src.todo.domain.model.Task): TaskWithDate =
            Task(
                id = task.id,
                name = task.name,
                note = task.note,
                date = task.date,
                folderId = task.folderId
            )
    }
}