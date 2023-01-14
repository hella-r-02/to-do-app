package com.src.todo.data.local.dao

import androidx.room.*
import com.src.todo.data.local.entity.FolderEntity
import com.src.todo.data.local.entity.views.FolderWithCountOfTasksView
import kotlinx.coroutines.flow.Flow

@Dao
interface FolderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(folderEntity: FolderEntity): Long

    @Query("select * from folders_with_count_of_tasks")
    fun getFoldersWithCountOfTasks(): Flow<List<FolderWithCountOfTasksView>>

    @Delete
    fun deleteFolder(folderEntity: FolderEntity)

    @Query("update folders set name=:name where id=:id")
    fun updateNameById(name: String, id: Long)
}