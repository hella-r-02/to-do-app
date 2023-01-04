package com.src.todo.domain.model

data class FolderWithCountOfTasks(
    val folder: Folder,
    val count: Long
)