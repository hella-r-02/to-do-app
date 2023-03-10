package com.src.todo.domain.model

import java.util.*

data class Task(
    val id: Long,
    var name: String,
    var date: Date?,
    var note: String?,
    val folderId: Long?,
)