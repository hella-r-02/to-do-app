package com.src.todo.data.local

import com.src.todo.data.local.entity.FolderEntity
import com.src.todo.data.local.entity.TaskEntity
import com.src.todo.data.local.entity.views.FolderWithCountOfTasksView
import com.src.todo.domain.model.Folder
import com.src.todo.domain.model.FolderWithCountOfTasks
import com.src.todo.domain.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class LocalDataSourceImpl(private val database: AppRoomDatabase) : LocalDataSource {
    override fun insertFolder(name: String): Long {
        return database.getFolderDao().insert(FolderEntity.fromNameOfFolder(name))
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

    override fun deleteFolder(folder: Folder) {
        val folderEntity = FolderEntity.fromFolderModel(folder)
        database.getFolderDao().deleteFolder(folderEntity)
    }

    override fun insertTask(task: Task) {
        val taskEntity = TaskEntity.fromTaskModel(task)
        database.getTaskDao().insertTask(taskEntity)
    }

    override fun deleteTask(id: Long) {
        database.getTaskDao().deleteTaskById(id)
    }

    override fun updateFolderNameByFolderId(name: String, folderId: Long) {
        database.getFolderDao().updateNameById(name, folderId)
    }

    override fun getAllTasksWithoutFolder(): Flow<List<Task>> {
        return database.getTaskDao().getAllTasksWithoutFolder()
            .map { list -> list.map { mapTaskEntityToModel(it) } }
    }

    override fun getCountOfTasksWithoutFolder(): Flow<Long> {
        return database.getTaskDao().getCountOfTasksWithoutFolder()
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