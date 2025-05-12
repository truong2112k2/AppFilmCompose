package com.example.appfilm.domain.usecase.api_movie

import com.example.appfilm.common.Resource
import com.example.appfilm.data.source.remote.dto.MovieDto
import com.example.appfilm.domain.model.Movie
import com.example.appfilm.domain.repository.IApiMovie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GetNewMoviesUseCase @Inject constructor(
    private val movieRepository : IApiMovie
) {
    suspend fun getNewMovies(page: Int): Resource<List<Movie>> {
        return movieRepository.getNewMovies(page)
    }

}