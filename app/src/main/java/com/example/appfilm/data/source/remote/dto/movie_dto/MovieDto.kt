package com.example.appfilm.data.source.remote.dto.movie_dto

data class MovieDto(
    val items: List<Item>,
    val pagination: Pagination,
    val status: Boolean
)

/*
data class Movie(
    val _id: String,
    val name: String,
    val origin_name: String,
    val poster_url: String,
    val slug: String,
    val thumb_url: String,
    val year: Int,
    val type : String,
    val season : String,
    val vote_average: String,
    val vote_count: String,
    val time: String
)
 */

