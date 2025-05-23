package com.example.appfilm.domain.usecase.database

import com.example.appfilm.domain.model.Movie
import com.example.appfilm.domain.repository.IDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton

class GetMoviesUseCase @Inject constructor(
    private val movieDb: IDatabase
) {
    fun getMovie(): Flow<List<Movie>> {
        return movieDb.getAllMovies()
    }
}