package com.src.todo.data.local.entity.views

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Embedded
import com.src.todo.data.local.entity.FolderEntity

@DatabaseView(
    viewName = "folders_with_count_of_tasks",
    value = "select folders.id, folders.name, count(distinct tasks.id) as task_count from folders\n" +
            "left join tasks on tasks.folder_id=folders.id\n" +
            "group by folders.id"
)
data class FolderWithCountOfTasksView(
    @Embedded val folder: FolderEntity,
    @ColumnInfo(name = "task_count") val taskCount: Long

)
