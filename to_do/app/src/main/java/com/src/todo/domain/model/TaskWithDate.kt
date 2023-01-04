package com.src.todo.domain.model

import java.util.*

sealed class TaskWithDate {
    class Task(
        val id: Long,
        val name: String,
        val date: Date?,
        val note: String?,
        val repeating: Long?,
        val folder: Folder?
    ) : TaskWithDate()

    class DateTask(
        val date: String
    ) : TaskWithDate()
}