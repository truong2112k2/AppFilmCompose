package com.example.appfilm.di

import com.example.appfilm.data.reposiory.FirebaseRepositoryImpl
import com.example.appfilm.data.source.remote.IFirebaseDataSource
import com.example.appfilm.data.source.remote.impl.FirebaseDataSource
import com.example.appfilm.domain.repository.IFirebaseRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent :: class)
abstract class FirebaseRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindIAuthDataSource( authDataSource: FirebaseDataSource): IFirebaseDataSource

    @Binds
    @Singleton
    abstract fun bindIAuthRepository( authRepositoryImpl: FirebaseRepositoryImpl): IFirebaseRepository
}