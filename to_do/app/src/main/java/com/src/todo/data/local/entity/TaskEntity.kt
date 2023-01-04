package com.src.todo.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.src.todo.domain.model.Task
import java.util.*

@Entity(
    tableName = "tasks",
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = FolderEntity::class,
            parentColumns = ["id"],
            childColumns = ["folder_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long,
    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "date")
    val date: Date?,

    @ColumnInfo(name = "note")
    val note: String?,

    @ColumnInfo(name = "repeating")
    val repeating: Long?,

    @ColumnInfo(name = "folder_id")
    val folderId: Long
) {
    companion object {
        fun fromTaskModel(task: Task) = TaskEntity(
            id = task.id,
            name = task.name,
            date = task.date,
            note = task.note,
            repeating = task.repeating,
            folderId = task.folder.id
        )
    }
}
