package com.example.appfilm.data.reposiory

import com.example.appfilm.common.Resource
import com.example.appfilm.data.source.local.IDatabaseDataSource
import com.example.appfilm.data.source.local.model.MovieDb
import com.example.appfilm.domain.model.Movie
import com.example.appfilm.domain.repository.IDatabase
import com.example.appfilm.domain.toMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseRepositoryImpl @Inject constructor(
    private val databaseDataSource: IDatabaseDataSource
): IDatabase {
     override suspend fun insertAll(movies: List<MovieDb>): Resource<Boolean> {
        return databaseDataSource.insertAll(movies)
    }

    override fun getAllMovies(): Flow<List<Movie>> {
        return databaseDataSource.getAllMovies()
            .map { movieDbList ->
                movieDbList.map { it.toMovie() }
            }
    }
}