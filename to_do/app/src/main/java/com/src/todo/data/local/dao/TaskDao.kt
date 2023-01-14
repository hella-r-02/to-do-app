package com.src.todo.data.local.dao

import androidx.room.*
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

    @Query("select * from tasks where tasks.id=:id")
    fun getTaskById(id: Long): Flow<TaskEntity>

    @Update
    fun updateTask(taskEntity: TaskEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertTask(taskEntity: TaskEntity)

    @Query("delete from tasks where id=:id")
    fun deleteTaskById(id: Long)
}