package com.example.appfilm.domain.usecase.api_movie

import com.example.appfilm.common.Resource
import com.example.appfilm.domain.model.MovieByCategory
import com.example.appfilm.domain.repository.IApiMovie
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FetchMoviesByCategoryUseCase @Inject constructor(
    private val movieRepository: IApiMovie

) {
    suspend operator fun invoke(category: String, page: Int, limit: Int ): Resource<List<MovieByCategory>> {
        return movieRepository.fetchMoviesByCategory(category, page, limit)
    }


}