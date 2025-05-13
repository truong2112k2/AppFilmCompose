package com.example.appfilm.domain.repository


import com.example.appfilm.common.Resource
import com.example.appfilm.data.source.local.model.MovieDb
import com.example.appfilm.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IDatabase {
    suspend fun insertAll(movies: List<MovieDb>): Resource<Boolean>
    fun getAllMovies(): Flow<List<Movie>>
}