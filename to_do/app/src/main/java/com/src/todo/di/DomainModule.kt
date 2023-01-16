package com.src.todo.di

import com.src.todo.domain.repository.FolderRepository
import com.src.todo.domain.repository.TaskRepository
import com.src.todo.domain.usecase.folder.DeleteFolderUseCase
import com.src.todo.domain.usecase.folder.GetListOfFoldersWithCountTasksUseCase
import com.src.todo.domain.usecase.folder.InsertFolderUseCase
import com.src.todo.domain.usecase.folder.UpdateFolderNameByFolderIdUseCase
import com.src.todo.domain.usecase.task.*
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

    @Provides
    fun provideInsertTaskUseCase(taskRepository: TaskRepository): InsertTaskUseCase {
        return InsertTaskUseCase(taskRepository = taskRepository)
    }

    @Provides
    fun provideDeleteTask(taskRepository: TaskRepository): DeleteTaskUseCase {
        return DeleteTaskUseCase(taskRepository = taskRepository)
    }

    @Provides
    fun provideUpdateFolderNameByFolderIdUseCase(folderRepository: FolderRepository): UpdateFolderNameByFolderIdUseCase {
        return UpdateFolderNameByFolderIdUseCase(folderRepository = folderRepository)
    }

    @Provides
    fun provideGetTasksWithoutFolderUseCase(taskRepository: TaskRepository): GetTasksWithoutFolderUseCase {
        return GetTasksWithoutFolderUseCase(taskRepository = taskRepository)
    }

    @Provides
    fun provideGetCountOfTasksWithoutFolderUseCase(taskRepository: TaskRepository): GetCountOfTasksWithoutFolderUseCase {
        return GetCountOfTasksWithoutFolderUseCase(taskRepository = taskRepository)
    }
}