package com.src.todo.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.src.todo.domain.model.Folder

@Entity(
    tableName = "folders"
)
data class FolderEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long,
    @ColumnInfo(name = "name")
    val name: String
) {
    fun toFolder(): Folder = Folder(
        id = id,
        name = name
    )

    companion object {
        fun fromFolderModel(folder: Folder): FolderEntity = FolderEntity(
            id = folder.id,
            name = folder.name
        )

        fun fromNameOfFolder(name: String) = FolderEntity(
            id = 0,
            name = name
        )
    }
}