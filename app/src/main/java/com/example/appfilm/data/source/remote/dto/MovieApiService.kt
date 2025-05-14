package com.example.appfilm.data.source.remote.dto

import com.example.appfilm.data.source.remote.dto.category_dto.CategoryDto
import com.example.appfilm.data.source.remote.dto.movie_catgory_dto.MovieByCategoryDto
import com.example.appfilm.data.source.remote.dto.movie_dto.MovieDto
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
    //https://phimapi.com/v1/api/the-loai/gia-dinh
    @GET("v1/api/the-loai/{type_list}")
   suspend  fun getMoviesByCategory(@Path("type_list") category: String): MovieByCategoryDto


}
