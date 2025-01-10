package com.fatihbilgin.movieapp.di

import com.fatihbilgin.movieapp.data.datasource.FilmsDataSource
import com.fatihbilgin.movieapp.data.repo.FilmsRepository
import com.fatihbilgin.movieapp.retrofit.ApiUtils
import com.fatihbilgin.movieapp.retrofit.FilmsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideFilmsRepository(filmsDataSource: FilmsDataSource) : FilmsRepository {
        return FilmsRepository(filmsDataSource)
    }

    @Provides
    @Singleton
    fun provideFilmsDataSource(filmsDao: FilmsDao) : FilmsDataSource {
        return FilmsDataSource(filmsDao)
    }

    @Provides
    @Singleton
    fun provideFilmsDao() : FilmsDao {
        return ApiUtils.getFilmsDao()
    }
}