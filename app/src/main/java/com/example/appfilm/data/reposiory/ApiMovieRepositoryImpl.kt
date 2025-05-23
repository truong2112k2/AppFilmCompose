package com.example.appfilm.data.reposiory

import android.content.Context
import android.util.Log
import com.example.appfilm.common.Constants
import com.example.appfilm.common.Resource
import com.example.appfilm.data.source.local.IDatabaseDataSource
import com.example.appfilm.data.source.remote.impl.ApiMovieDataSource
import com.example.appfilm.domain.model.Category
import com.example.appfilm.domain.model.Movie
import com.example.appfilm.domain.model.MovieByCategory
import com.example.appfilm.domain.model.MovieBySearch
import com.example.appfilm.domain.model.detail_movie.MovieDetail
import com.example.appfilm.domain.repository.IApiMovie
import com.example.appfilm.domain.toCategory
import com.example.appfilm.domain.toMovie
import com.example.appfilm.domain.toMovieByCategory
import com.example.appfilm.domain.toMovieBySearch
import com.example.appfilm.domain.toMovieDb
import com.example.appfilm.domain.toMovieDetail
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ApiMovieRepositoryImpl @Inject constructor(
    private val apiMovieDataSource: ApiMovieDataSource,
    private val databaseDataSource: IDatabaseDataSource

) : IApiMovie {

    override suspend fun fetchDataMovieAndSaveFromDb(
        context: Context,
        page: Int
    ): Resource<List<Movie>> {

        return when (val getNewMovies = apiMovieDataSource.fetchDataMovieAndSaveFromDb(1)) {
            is Resource.Success -> {
                val dto = getNewMovies.data
                val movieList =
                    dto?.items?.map { it.toMovie() } ?: emptyList() // trả về cho use case

                val movieListDb =
                    dto?.items?.map { it.toMovieDb() } ?: emptyList() // lưu vào database
                val listCache =
                    databaseDataSource.cacheMovies(context, movieListDb) // lưu ảnh trước

                databaseDataSource.insertAll(listCache) // lưu list đã có ảnh vào database

                Resource.Success(movieList)

            }

            is Resource.Error -> {
                Resource.Error(
                    message = getNewMovies.message ?: "Unknown error",
                    getNewMovies.exception
                )
            }

            is Resource.Loading -> {
                Resource.Loading(emptyList())
            }
        }


    }

    override suspend fun fetchCategory(): Resource<List<Category>> {

        var categoryList = emptyList<Category>()
        return when (val getCategory = apiMovieDataSource.fetchCategory()) {
            is Resource.Success -> {

                val dto = getCategory.data
                if (dto != null) {
                    categoryList = dto.map { it.toCategory() }
                }

                Resource.Success(categoryList)

            }

            is Resource.Error -> {
                Resource.Error(
                    message = getCategory.message ?: "Unknown error",
                    getCategory.exception
                )
            }

            is Resource.Loading -> {
                Resource.Loading(emptyList())
            }
        }
    }

    override suspend fun fetchMoviesByCategory(
        category: String,
        page: Int,
        limit: Int
    ): Resource<List<MovieByCategory>> {
        var movieByCategoryList = emptyList<MovieByCategory>()
        return when (val getCategory =
            apiMovieDataSource.fetchMoviesByCategory(category, page, limit)) {
            is Resource.Success -> {

                val dto = getCategory.data
                if (dto != null) {
                    movieByCategoryList = dto.data.items.map { it.toMovieByCategory() }
                }

                Resource.Success(movieByCategoryList)

            }

            is Resource.Error -> {
                Resource.Error(
                    message = getCategory.message ?: "Unknown error",
                    getCategory.exception
                )
            }

            is Resource.Loading -> {
                Resource.Loading(emptyList())
            }
        }
    }

    override suspend fun getDetailMovie(slug: String): Resource<MovieDetail> {

        var detailMovie: MovieDetail = MovieDetail()
        return when (val getDetailMovie = apiMovieDataSource.getDetailMovie(slug)) {
            is Resource.Success -> {

                detailMovie = (getDetailMovie.data?.toMovieDetail()
                    ?: emptyList<MovieDetail>()) as MovieDetail

                Resource.Success(detailMovie)

            }

            is Resource.Error -> {
                Resource.Error(
                    message = getDetailMovie.message ?: "Unknown error",
                    getDetailMovie.exception
                )
            }

            is Resource.Loading -> {
                Resource.Loading(detailMovie)
            }
        }
    }

    override suspend fun searchMovies(keyword: String): Resource<List<MovieBySearch>> {
        var movies: List<MovieBySearch> = emptyList()

        return when (val searchMovie = apiMovieDataSource.searchMovies(keyword)) {
            is Resource.Success -> {
                movies = searchMovie.data?.data?.items?.map { it.toMovieBySearch() } ?: emptyList()
                Log.d(
                    Constants.STATUS_TAG,
                    "search movies by ApiMovieRepositoryImpl success ${movies.size}"
                )

                Resource.Success(movies)
            }

            is Resource.Error -> {
                Resource.Error(message = searchMovie.message.toString())
            }

            is Resource.Loading -> {
                Resource.Loading(movies)
            }
        }
    }
}