package com.example.appfilm.domain.repository

import android.content.Context
import com.example.appfilm.common.Resource
import com.example.appfilm.domain.model.Category
import com.example.appfilm.domain.model.Movie
import com.example.appfilm.domain.model.MovieByCategory

interface IApiMovie {
    suspend fun fetchDataMovieAndSaveFromDb(context: Context, page: Int): Resource<List<Movie>>
    suspend fun fetchCategory() : Resource<List<Category>>
    suspend fun fetchMoviesByCategory(category: String, page: Int, limit: Int ) : Resource<List<MovieByCategory>>

}