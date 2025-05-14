package com.example.appfilm.domain.usecase.api_movie

import android.content.Context
import com.example.appfilm.common.Resource
import com.example.appfilm.domain.model.Movie
import com.example.appfilm.domain.repository.IApiMovie
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FetchMovieAndSave @Inject constructor(
    private val movieRepository : IApiMovie
) {
    suspend fun fetchDataMovieAndSaveFromDb(context: Context, page: Int): Resource<List<Movie>> {
        return movieRepository.fetchDataMovieAndSaveFromDb( context, page)
    }

}