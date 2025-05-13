package com.example.appfilm.di

import com.example.appfilm.data.reposiory.ApiMovieRepositoryImpl
import com.example.appfilm.data.reposiory.DatabaseRepositoryImpl
import com.example.appfilm.data.reposiory.FirebaseRepositoryImpl
import com.example.appfilm.data.source.local.IDatabaseDataSource
import com.example.appfilm.data.source.local.impl.DatabaseDataSource
import com.example.appfilm.data.source.remote.IApiMovieDataSource
import com.example.appfilm.data.source.remote.IFirebaseDataSource
import com.example.appfilm.data.source.remote.impl.ApiMovieDataSource
import com.example.appfilm.data.source.remote.impl.FirebaseDataSource
import com.example.appfilm.domain.repository.IApiMovie
import com.example.appfilm.domain.repository.IDatabase
import com.example.appfilm.domain.repository.IFirebase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent ::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideIMovieApiDataSource(apiMovieDataSource: ApiMovieDataSource) : IApiMovieDataSource

    @Singleton
    @Binds
    abstract fun provideIMovieApi(apiMovieRepositoryImpl: ApiMovieRepositoryImpl): IApiMovie


    @Binds
    @Singleton
    abstract fun bindIAuthDataSource( authDataSource: FirebaseDataSource): IFirebaseDataSource

    @Binds
    @Singleton
    abstract fun bindIFirebase(authRepositoryImpl: FirebaseRepositoryImpl): IFirebase



    @Singleton
    @Binds
    abstract fun provideIDatabaseDataSource(databaseDataSource: DatabaseDataSource): IDatabaseDataSource

    @Singleton
    @Binds
    abstract fun provideIDatabase(databaseRepositoryImpl: DatabaseRepositoryImpl): IDatabase
}