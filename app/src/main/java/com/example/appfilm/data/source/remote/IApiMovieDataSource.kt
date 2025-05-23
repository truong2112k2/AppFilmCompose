package com.example.appfilm.data.source.remote

import com.example.appfilm.common.Resource
import com.example.appfilm.data.source.remote.dto.category_dto.CategoryDto
import com.example.appfilm.data.source.remote.dto.detail_dto.MovieDetailDto
import com.example.appfilm.data.source.remote.dto.movie_catgory_dto.MovieByCategoryDto
import com.example.appfilm.data.source.remote.dto.movie_dto.MovieDto
import com.example.appfilm.data.source.remote.dto.search_dto.MovieBySearchDto

interface IApiMovieDataSource {
    suspend fun fetchDataMovieAndSaveFromDb(page: Int): Resource<MovieDto>
    suspend fun fetchCategory() : Resource<CategoryDto>
    suspend fun fetchMoviesByCategory(category: String, page: Int, limit: Int ): Resource<MovieByCategoryDto>
    suspend fun getDetailMovie(slug: String) : Resource<MovieDetailDto>
    suspend fun searchMovies(keyword: String) : Resource<MovieBySearchDto>

}