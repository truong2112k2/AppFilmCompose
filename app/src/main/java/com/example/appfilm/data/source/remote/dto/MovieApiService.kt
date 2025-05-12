package com.example.appfilm.data.source.remote.dto

import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    //danh-sach/phim-moi-cap-nhat-v3?page=1
    @GET("danh-sach/phim-moi-cap-nhat-v3")
    suspend fun getNewMovies(@Query("page") page: Int): MovieDto
}
