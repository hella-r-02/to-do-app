package com.src.todo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.src.todo.data.local.entity.FolderEntity
import com.src.todo.data.local.entity.views.FolderWithCountOfTasksView
import kotlinx.coroutines.flow.Flow

@Dao
interface FolderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(folderEntity: FolderEntity)

    @Query("select * from folders_with_count_of_tasks")
    fun getFoldersWithCountOfTasks(): Flow<List<FolderWithCountOfTasksView>>
}