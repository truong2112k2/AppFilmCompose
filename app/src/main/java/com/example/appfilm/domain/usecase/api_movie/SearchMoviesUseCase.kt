package com.example.appfilm.domain.usecase.api_movie

import com.example.appfilm.common.Resource
import com.example.appfilm.domain.model.MovieBySearch
import com.example.appfilm.domain.repository.IApiMovie
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchMoviesUseCase  @Inject constructor(
    private val movieRepository : IApiMovie
) {

    suspend operator fun invoke(keyword: String): Resource<List<MovieBySearch>>{
        return movieRepository.searchMovies(keyword)
    }


}