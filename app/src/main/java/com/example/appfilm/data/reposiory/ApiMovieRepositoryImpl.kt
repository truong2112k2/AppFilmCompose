package com.example.appfilm.data.reposiory

import android.util.Log
import com.example.appfilm.common.Constants
import com.example.appfilm.common.Resource
import com.example.appfilm.data.source.remote.dto.MovieDto
import com.example.appfilm.data.source.remote.impl.ApiMovieDataSource
import com.example.appfilm.domain.model.Movie
import com.example.appfilm.domain.repository.IApiMovie
import com.example.appfilm.domain.toMovie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ApiMovieRepositoryImpl @Inject constructor (

    private val apiMovieDataSource: ApiMovieDataSource

) : IApiMovie {

    override suspend fun getNewMovies(page: Int): Resource<List<Movie>> {

        return when (val getNewMovies = apiMovieDataSource.getNewMovies(1)){
            is Resource.Success ->{
                val dto = getNewMovies.data
                val movieList = dto?.items?.map { it.toMovie() } ?: emptyList()
                Resource.Success( movieList)

            }
            is Resource.Error -> {
                Resource.Error(message =  getNewMovies.message ?: "Unknown error", getNewMovies.exception)
            }
            is Resource.Loading ->{   Resource.Loading(emptyList())}
        }



    }
}