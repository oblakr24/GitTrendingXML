package com.rokoblak.trendinggit.di

import com.rokoblak.trendinggit.data.repo.AppRepositoriesLoadingRepo
import com.rokoblak.trendinggit.data.repo.GitRepositoriesLoadingRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ApplicationModule {

    @Binds
    abstract fun provideLoadingRepo(impl: AppRepositoriesLoadingRepo): GitRepositoriesLoadingRepo
}
