package com.example.appfilm.data.source.remote.impl

import android.util.Log
import com.example.appfilm.common.Constants
import com.example.appfilm.common.Resource
import com.example.appfilm.data.source.remote.IApiMovieDataSource
import com.example.appfilm.data.source.remote.dto.MovieApiService
import com.example.appfilm.data.source.remote.dto.category_dto.CategoryDto
import com.example.appfilm.data.source.remote.dto.detail_dto.MovieDetailDto
import com.example.appfilm.data.source.remote.dto.movie_catgory_dto.MovieByCategoryDto
import com.example.appfilm.data.source.remote.dto.movie_dto.MovieDto
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiMovieDataSource @Inject constructor(
    private val apiService: MovieApiService

) : IApiMovieDataSource {


    override suspend fun fetchDataMovieAndSaveFromDb(page: Int): Resource<MovieDto> {
        return try {
            val movies = apiService.getNewMovies(page)
            Log.d(Constants.STATUS_TAG, "get new movies success")
            Resource.Success(movies)
        } catch (e: IOException) {
            Log.e(Constants.ERROR_TAG, "Network error: ${e.message}")
            Resource.Error(e.message ?: "get new movies failed", e)

        } catch (e: HttpException) {
            Log.e(Constants.ERROR_TAG, "HTTP error: ${e.code()} ${e.message()}")
            Resource.Error(e.message ?: "get new movies failed", e)

        } catch (e: Exception) {
            Log.e(Constants.ERROR_TAG, "Unexpected error: ${e.message}")
            Resource.Error(e.message ?: "get new movies failed", e)

        }
    }

    override suspend fun fetchCategory(): Resource<CategoryDto> {
        return try {
            val categories = apiService.getCategory()
            Log.d(Constants.STATUS_TAG, "get category movies success")
            Resource.Success(categories)
        } catch (e: IOException) {
            Log.e(Constants.ERROR_TAG, "Network error: ${e.message}")
            Resource.Error(e.message ?: "get category failed", e)

        } catch (e: HttpException) {
            Log.e(Constants.ERROR_TAG, "HTTP error: ${e.code()} ${e.message()}")
            Resource.Error(e.message ?: "get category failed", e)

        } catch (e: Exception) {
            Log.e(Constants.ERROR_TAG, "Unexpected error: ${e.message}")
            Resource.Error(e.message ?: "get category failed", e)

        }
    }

    override suspend fun fetchMoviesByCategory(category: String, page: Int, limit: Int): Resource<MovieByCategoryDto> {
        return try {
            val moviesByCategory = apiService.getMoviesByCategory(category,page, limit)
            Log.d(Constants.STATUS_TAG, "get movies by category movies success")
            Resource.Success(moviesByCategory)
        } catch (e: IOException) {
            Log.e(Constants.ERROR_TAG, "Network error: ${e.message}")
            Resource.Error(e.message ?: "get movies by category failed", e)

        } catch (e: HttpException) {
            Log.e(Constants.ERROR_TAG, "HTTP error: ${e.code()} ${e.message()}")
            Resource.Error(e.message ?: "get movies by category failed", e)

        } catch (e: Exception) {
            Log.e(Constants.ERROR_TAG, "Unexpected error: ${e.message}")
            Resource.Error(e.message ?: "get movies by category failed", e)

        }
    }

    override suspend fun getDetailMovie(slug: String): Resource<MovieDetailDto> {
        return try {
            val moviesByCategory = apiService.getDetailMovie(slug)
            Log.d(Constants.STATUS_TAG, "get movies detail by category movies success")
            Resource.Success(moviesByCategory)
        } catch (e: IOException) {
            Log.e(Constants.ERROR_TAG, "Network error: ${e.message}")
            Resource.Error(e.message ?: "get movies detail by category failed", e)

        } catch (e: HttpException) {
            Log.e(Constants.ERROR_TAG, "HTTP error: ${e.code()} ${e.message()}")
            Resource.Error(e.message ?: "get movies detail by category failed", e)

        } catch (e: Exception) {
            Log.e(Constants.ERROR_TAG, "Unexpected error: ${e.message}")
            Resource.Error(e.message ?: "get movies detail by category failed", e)

        }

    }
}