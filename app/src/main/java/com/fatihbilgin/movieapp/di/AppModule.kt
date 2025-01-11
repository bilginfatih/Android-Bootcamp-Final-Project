package com.fatihbilgin.movieapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.fatihbilgin.movieapp.data.datasource.FilmsDataSource
import com.fatihbilgin.movieapp.data.repo.FilmsRepository
import com.fatihbilgin.movieapp.retrofit.ApiUtils
import com.fatihbilgin.movieapp.retrofit.FilmsDao
import com.fatihbilgin.movieapp.room.OrderDao
import com.fatihbilgin.movieapp.room.OrderDatabase
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


    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    fun provideDatabase(context: Context): OrderDatabase {
        return Room.databaseBuilder(
            context,
            OrderDatabase::class.java,
            "order_database"
        ).build()
    }

    @Provides
    fun provideOrderDao(database: OrderDatabase): OrderDao {
        return database.orderDao()
    }

}