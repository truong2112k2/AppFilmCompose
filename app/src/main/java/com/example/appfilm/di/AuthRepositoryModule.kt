package com.example.appfilm.di

import com.example.appfilm.data.reposiory.AuthRepositoryImpl
import com.example.appfilm.data.source.remote.IAuthDataSource
import com.example.appfilm.data.source.remote.impl.AuthDataSource
import com.example.appfilm.domain.repository.IAuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent :: class)
abstract class AuthRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindIAuthDataSource( authDataSource: AuthDataSource): IAuthDataSource

    @Binds
    @Singleton
    abstract fun bindIAuthRepository( authRepositoryImpl: AuthRepositoryImpl): IAuthRepository
}