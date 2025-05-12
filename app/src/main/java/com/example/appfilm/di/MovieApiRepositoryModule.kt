package com.example.appfilm.di

import com.example.appfilm.data.reposiory.ApiMovieRepositoryImpl
import com.example.appfilm.data.source.remote.IApiMovieDataSource
import com.example.appfilm.data.source.remote.impl.ApiMovieDataSource
import com.example.appfilm.domain.repository.IApiMovie
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent :: class)
abstract class MovieApiRepositoryModule {

    @Singleton
    @Binds
    abstract fun provideIMovieApiDataSource(apiMovieDataSource: ApiMovieDataSource) : IApiMovieDataSource

    @Singleton
    @Binds
    abstract fun provideIMovieApi(apiMovieRepositoryImpl: ApiMovieRepositoryImpl): IApiMovie

}