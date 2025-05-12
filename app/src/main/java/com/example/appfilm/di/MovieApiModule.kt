package com.example.appfilm.di

import com.example.appfilm.data.source.remote.dto.MovieApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val URL_BASE = "https://phimapi.com/"

//danh-sach/phim-moi-cap-nhat-v3?page=1
@Module
@InstallIn(SingletonComponent ::class)
object MovieApiModule {




    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit{
       return Retrofit.Builder()
            .baseUrl(URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieApiService(retrofit: Retrofit): MovieApiService {
        return retrofit.create(MovieApiService :: class.java)
    }
//    @Singleton
//    val api: MovieApiService by lazy {
//        Retrofit.Builder()
//            .baseUrl(URL_BASE)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(MovieApiService :: class.java)
//    }




}