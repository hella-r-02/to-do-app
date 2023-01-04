package com.src.todo.di

import com.src.todo.data.FolderRepositoryImpl
import com.src.todo.data.local.LocalDataSource
import com.src.todo.domain.repository.FolderRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {
    @Provides
    fun provideFolderRepository(localDataSource: LocalDataSource): FolderRepository {
        return FolderRepositoryImpl(localDataSource = localDataSource)
    }
}