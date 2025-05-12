package com.example.appfilm.domain.repository

import com.example.appfilm.common.Resource
import com.example.appfilm.data.source.remote.dto.MovieDto
import com.example.appfilm.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IApiMovie {
    suspend fun getNewMovies(page: Int): Resource<List<Movie>>

}