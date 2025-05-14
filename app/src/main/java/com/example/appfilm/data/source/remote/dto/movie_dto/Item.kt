package com.example.appfilm.data.source.remote.dto.movie_dto

data class Item(
    val _id: String,
    val imdb: Imdb,
    val modified: Modified,
    val name: String,
    val origin_name: String,
    val poster_url: String,
    val slug: String,
    val thumb_url: String,
    val tmdb: Tmdb,
    val year: Int
)

