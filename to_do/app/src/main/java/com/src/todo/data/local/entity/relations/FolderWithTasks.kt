package com.src.todo.data.local.entity.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.src.todo.data.local.entity.FolderEntity
import com.src.todo.data.local.entity.TaskEntity

data class FolderWithTasks(
    @Embedded
    val folderEntity: FolderEntity,
    @Relation(parentColumn = "id", entityColumn = "id")
    val tasks: List<TaskEntity>
)