package com.rokoblak.trendinggit.di

import android.content.Context
import androidx.room.Room
import com.rokoblak.trendinggit.data.service.db.ReposDao
import com.rokoblak.trendinggit.data.service.db.ReposDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DbModule {

    @Singleton
    @Provides
    fun providesReposDatabase(@ApplicationContext context: Context): ReposDatabase {
        return Room.databaseBuilder(context, ReposDatabase::class.java, ReposDatabase.NAME)
            .fallbackToDestructiveMigrationOnDowngrade()
            .build()
    }

    @Provides
    fun provideReposDao(db: ReposDatabase): ReposDao {
        return db.reposDao()
    }
}