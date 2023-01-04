package com.src.todo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.src.todo.data.local.converters.DateConverter
import com.src.todo.data.local.dao.FolderDao
import com.src.todo.data.local.dao.TaskDao
import com.src.todo.data.local.entity.FolderEntity
import com.src.todo.data.local.entity.TaskEntity
import com.src.todo.data.local.entity.views.FolderWithCountOfTasksView

@Database(
    entities = [FolderEntity::class, TaskEntity::class],
    version = 1,
    views = [FolderWithCountOfTasksView::class]
)
@TypeConverters(DateConverter::class)
abstract class AppRoomDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "tasks.db"
    }

    abstract fun getFolderDao(): FolderDao
    abstract fun getTaskDao(): TaskDao
}