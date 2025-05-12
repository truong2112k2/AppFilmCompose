package com.example.appfilm.data.source.remote

import com.example.appfilm.common.Resource
import com.example.appfilm.data.source.remote.dto.MovieDto
import kotlinx.coroutines.flow.Flow

interface IApiMovieDataSource {
    suspend fun getNewMovies(page: Int): Resource<MovieDto>

}