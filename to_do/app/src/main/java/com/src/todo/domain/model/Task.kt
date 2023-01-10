package com.src.todo.domain.model

import java.util.*

data class Task(
    val id: Long,
    val name: String,
    val date: Date?,
    val note: String?,
    val folderId: Long,
)