package com.src.todo.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.src.todo.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query(
        "select * from tasks " +
                "join folders on folders.id=tasks.folder_id " +
                "where folders.id=:id " +
                "order by tasks.date"
    )
    fun getTasksByFolderId(id: Long): Flow<List<TaskEntity>>
}