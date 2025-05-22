package com.example.appfilm.data.source.remote.dto.movie_dto

data class Item(
    val _id: String?= null ,
    val imdb: Imdb? = null ,
    val modified: Modified? = null,
    val name: String?= null,
    val origin_name: String?= null,
    val poster_url: String?= null,
    val slug: String?= null,
    val thumb_url: String?= null,
    val tmdb: Tmdb? = null,
    val year: Int?= null
)

