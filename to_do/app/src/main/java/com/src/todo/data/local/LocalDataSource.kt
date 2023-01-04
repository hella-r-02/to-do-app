package com.src.todo.data.local

import com.src.todo.domain.model.FolderWithCountOfTasks
import kotlinx.coroutines.flow.Flow


interface LocalDataSource {
    fun insertFolder(name:String)
    fun getFoldersWithCountOfTasks(): Flow<List<FolderWithCountOfTasks>>
}