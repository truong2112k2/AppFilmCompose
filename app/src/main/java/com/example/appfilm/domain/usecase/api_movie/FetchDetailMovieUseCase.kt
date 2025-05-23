package com.example.appfilm.domain.usecase.api_movie

import com.example.appfilm.common.Resource
import com.example.appfilm.domain.model.detail_movie.MovieDetail
import com.example.appfilm.domain.repository.IApiMovie
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FetchDetailMovieUseCase @Inject constructor(
    private val movieRepository: IApiMovie

) {
    suspend operator fun invoke(slug: String): Resource<MovieDetail> {
        return movieRepository.getDetailMovie(slug)
    }
}