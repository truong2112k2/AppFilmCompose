package com.example.appfilm.data.source.remote.dto.search_dto

data class Tmdb(
    val id: String,
    val season: Any,
    val type: String,
    val vote_average: Double,
    val vote_count: Int
)