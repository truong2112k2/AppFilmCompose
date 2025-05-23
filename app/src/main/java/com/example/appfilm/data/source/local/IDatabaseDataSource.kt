package com.example.appfilm.data.source.local

import android.content.Context
import com.example.appfilm.common.Resource
import com.example.appfilm.data.source.local.model.MovieDb
import kotlinx.coroutines.flow.Flow

interface IDatabaseDataSource {
    suspend fun insertAll(movies: List<MovieDb>): Resource<Boolean>
    fun getAllMovies(): Flow<List<MovieDb>>
    suspend fun cacheMovies(context: Context, movies: List<MovieDb>): List<MovieDb>
    suspend fun saveImageToInternalStorage(
        context: Context,
        imageUrl: String,
        fileName: String
    ): String?
}