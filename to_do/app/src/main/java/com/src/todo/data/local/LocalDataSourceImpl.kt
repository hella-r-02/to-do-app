package com.src.todo.data.local

import com.src.todo.data.local.entity.FolderEntity
import com.src.todo.data.local.entity.views.FolderWithCountOfTasksView
import com.src.todo.domain.model.FolderWithCountOfTasks
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

    private suspend fun mapFoldersWithCountOfTasksViewToModel(folderWithCountOfTasksView: FolderWithCountOfTasksView): FolderWithCountOfTasks =
        withContext(Dispatchers.IO) {
            return@withContext FolderWithCountOfTasks(
                folder = folderWithCountOfTasksView.folder.toFolder(),
                count = folderWithCountOfTasksView.taskCount
            )
        }
}