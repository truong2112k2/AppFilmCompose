package com.example.appfilm.data.reposiory

import android.content.Context
import com.example.appfilm.common.Resource
import com.example.appfilm.data.source.local.IDatabaseDataSource
import com.example.appfilm.data.source.remote.impl.ApiMovieDataSource
import com.example.appfilm.domain.model.Movie
import com.example.appfilm.domain.repository.IApiMovie
import com.example.appfilm.domain.toMovie
import com.example.appfilm.domain.toMovieDb
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ApiMovieRepositoryImpl @Inject constructor (


    private val apiMovieDataSource: ApiMovieDataSource,
    private val databaseDataSource: IDatabaseDataSource

) : IApiMovie {

    override suspend fun fetchDataMovieAndSaveFromDb(context: Context, page: Int): Resource<List<Movie>> {

        return when (val getNewMovies = apiMovieDataSource.fetchDataMovieAndSaveFromDb(1)){
            is Resource.Success ->{
                val dto = getNewMovies.data
                val movieList = dto?.items?.map { it.toMovie() } ?: emptyList() // trả về cho use case

                val movieListDb = dto?.items?.map { it.toMovieDb() } ?: emptyList() // lưu vào database
                val listCache = databaseDataSource.cacheMovies(context, movieListDb) // lưu ảnh trước

                databaseDataSource.insertAll(listCache) // lưu list đã có ảnh vào database

                Resource.Success( movieList)

            }
            is Resource.Error -> {
                Resource.Error(message =  getNewMovies.message ?: "Unknown error", getNewMovies.exception)
            }
            is Resource.Loading ->{   Resource.Loading(emptyList())}
        }



    }



}