package com.example.appfilm.data.source.remote

import com.example.appfilm.common.Resource
import com.example.appfilm.data.source.remote.dto.category_dto.CategoryDto
import com.example.appfilm.data.source.remote.dto.movie_catgory_dto.MovieByCategoryDto
import com.example.appfilm.data.source.remote.dto.movie_dto.MovieDto

interface IApiMovieDataSource {
    suspend fun fetchDataMovieAndSaveFromDb(page: Int): Resource<MovieDto>
    suspend fun fetchCategory() : Resource<CategoryDto>
    suspend fun fetchMoviesByCategory(category: String): Resource<MovieByCategoryDto>

}