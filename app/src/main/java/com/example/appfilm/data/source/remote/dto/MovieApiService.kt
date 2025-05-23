package com.example.appfilm.data.source.remote.dto

import com.example.appfilm.data.source.remote.dto.category_dto.CategoryDto
import com.example.appfilm.data.source.remote.dto.detail_dto.MovieDetailDto
import com.example.appfilm.data.source.remote.dto.movie_catgory_dto.MovieByCategoryDto
import com.example.appfilm.data.source.remote.dto.movie_dto.MovieDto
import com.example.appfilm.data.source.remote.dto.search_dto.MovieBySearchDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    //danh-sach/phim-moi-cap-nhat-v3?page=1
    @GET("danh-sach/phim-moi-cap-nhat-v3")
    suspend fun getNewMovies(@Query("page") page: Int): MovieDto

    //https://phimapi.com/the-loai
    @GET("the-loai")
    suspend fun getCategory(): CategoryDto

    ///private const val URL_BASE = "https://phimapi.com/"
    @GET("v1/api/the-loai/{type_list}")
    suspend fun getMoviesByCategory(
        @Path("type_list") category: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int

    ): MovieByCategoryDto

    ///https://phimapi.com/phim/${slug}
    @GET("phim/{slug}")
    suspend fun getDetailMovie(
        @Path("slug") slug: String,
    ): MovieDetailDto

    @GET("v1/api/tim-kiem")
    suspend fun searchMovies(
        @Query("keyword") keyword: String
    ): MovieBySearchDto
}
