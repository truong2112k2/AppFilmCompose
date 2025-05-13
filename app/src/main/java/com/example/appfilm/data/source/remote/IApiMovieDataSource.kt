package com.example.appfilm.data.source.remote

import com.example.appfilm.common.Resource
import com.example.appfilm.data.source.remote.dto.MovieDto

interface IApiMovieDataSource {
    suspend fun fetchDataMovieAndSaveFromDb(page: Int): Resource<MovieDto>

}