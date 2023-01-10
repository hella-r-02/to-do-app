package com.src.todo.data.local

import com.src.todo.data.local.entity.FolderEntity
import com.src.todo.data.local.entity.TaskEntity
import com.src.todo.data.local.entity.views.FolderWithCountOfTasksView
import com.src.todo.domain.model.FolderWithCountOfTasks
import com.src.todo.domain.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class LocalDataSourceImpl(private val database: AppRoomDatabase) : LocalDataSource {
    override fun insertFolder(name: String) {
        database.getFolderDao().insert(FolderEntity.fromNameOfFolder(name))
    }

    override fun getFoldersWithCountOfTasks(): Flow<List<FolderWithCountOfTasks>> {
        return database.getFolderDao().getFoldersWithCountOfTasks()
            .map { list -> list.map { mapFoldersWithCountOfTasksViewToModel(it) } }
    }

    override fun getTasksByFolderId(folderId: Long): Flow<List<Task>> {
        return database.getTaskDao().getTasksByFolderId(folderId)
            .map { list -> list.map { mapTaskEntityToModel(it) } }
    }

    override fun getTaskById(taskId: Long): Flow<Task> {
        return database.getTaskDao().getTaskById(taskId).map { mapTaskEntityToModel(it) }
    }

    override fun updateTask(task: Task) {
        val taskEntity = TaskEntity.fromTaskModel(task)
        database.getTaskDao().updateTask(taskEntity)
    }

    private suspend fun mapFoldersWithCountOfTasksViewToModel(folderWithCountOfTasksView: FolderWithCountOfTasksView): FolderWithCountOfTasks =
        withContext(Dispatchers.IO) {
            return@withContext FolderWithCountOfTasks(
                folder = folderWithCountOfTasksView.folder.toFolder(),
                count = folderWithCountOfTasksView.taskCount
            )
        }

    private suspend fun mapTaskEntityToModel(taskEntity: TaskEntity): Task =
        withContext(Dispatchers.IO) {
            return@withContext Task(
                id = taskEntity.id,
                name = taskEntity.name,
                date = taskEntity.date,
                note = taskEntity.note,
                folderId = taskEntity.folderId,
            )
        }
}