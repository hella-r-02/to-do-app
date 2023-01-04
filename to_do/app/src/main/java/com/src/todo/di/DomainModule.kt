package com.src.todo.di

import com.src.todo.domain.repository.FolderRepository
import com.src.todo.domain.usecase.GetListOfFoldersWithCountTasksUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule {
    @Provides
    fun provideGetListOfFoldersWithCountOfTasksUseCase(folderRepository: FolderRepository): GetListOfFoldersWithCountTasksUseCase {
        return GetListOfFoldersWithCountTasksUseCase(folderRepository = folderRepository)
    }
}