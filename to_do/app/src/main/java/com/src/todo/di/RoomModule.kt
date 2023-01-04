package com.src.todo.di

import android.content.Context
import androidx.room.Room
import com.src.todo.data.local.AppRoomDatabase
import com.src.todo.data.local.LocalDataSource
import com.src.todo.data.local.LocalDataSourceImpl
import com.src.todo.data.local.converters.DateConverter
import dagger.Module
import dagger.Provides

@Module
class RoomModule {
    @Provides
    fun provideAppRoomDatabase(context: Context): AppRoomDatabase {
        return Room.databaseBuilder(
            context,
            AppRoomDatabase::class.java,
            AppRoomDatabase.DATABASE_NAME
        )
            .createFromAsset("todo.db")
            .build()
    }

    @Provides
    fun provideLocalDataSource(db: AppRoomDatabase): LocalDataSource {
        return LocalDataSourceImpl(db)
    }

    @Provides
    fun provideDateConverter(): DateConverter {
        return DateConverter()
    }
}