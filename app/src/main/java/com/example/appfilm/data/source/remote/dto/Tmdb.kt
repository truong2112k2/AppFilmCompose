package com.example.appfilm.data.source.remote.dto

data class Tmdb(
    val id: String,
    val season: Int,
    val type: String,
    val vote_average: Double,
    val vote_count: Int
)