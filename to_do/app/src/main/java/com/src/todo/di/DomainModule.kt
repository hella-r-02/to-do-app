package com.src.todo.di

import com.src.todo.domain.repository.FolderRepository
import com.src.todo.domain.repository.TaskRepository
import com.src.todo.domain.usecase.GetListOfFoldersWithCountTasksUseCase
import com.src.todo.domain.usecase.GetTasksByFolderIdUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule {
    @Provides
    fun provideGetListOfFoldersWithCountOfTasksUseCase(folderRepository: FolderRepository): GetListOfFoldersWithCountTasksUseCase {
        return GetListOfFoldersWithCountTasksUseCase(folderRepository = folderRepository)
    }

    @Provides
    fun provideGetTasksByFolderIdUseCase(taskRepository: TaskRepository): GetTasksByFolderIdUseCase {
        return GetTasksByFolderIdUseCase(taskRepository = taskRepository)
    }
}