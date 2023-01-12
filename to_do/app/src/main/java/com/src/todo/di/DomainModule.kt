package com.src.todo.di

import com.src.todo.domain.repository.FolderRepository
import com.src.todo.domain.repository.TaskRepository
import com.src.todo.domain.usecase.folder.DeleteFolderUseCase
import com.src.todo.domain.usecase.folder.GetListOfFoldersWithCountTasksUseCase
import com.src.todo.domain.usecase.folder.InsertFolderUseCase
import com.src.todo.domain.usecase.task.GetTaskByIdUseCase
import com.src.todo.domain.usecase.task.GetTasksByFolderIdUseCase
import com.src.todo.domain.usecase.task.UpdateTaskUseCase
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

    @Provides
    fun provideGetTaskByIdUseCase(taskRepository: TaskRepository): GetTaskByIdUseCase {
        return GetTaskByIdUseCase(taskRepository = taskRepository)
    }

    @Provides
    fun provideUpdateTaskUseCase(taskRepository: TaskRepository): UpdateTaskUseCase {
        return UpdateTaskUseCase(taskRepository = taskRepository)
    }

    @Provides
    fun provideInsertFolderUseCase(folderRepository: FolderRepository): InsertFolderUseCase {
        return InsertFolderUseCase(folderRepository = folderRepository)
    }

    @Provides
    fun provideDeleteFolderUseCase(folderRepository: FolderRepository): DeleteFolderUseCase {
        return DeleteFolderUseCase(folderRepository = folderRepository)
    }
}