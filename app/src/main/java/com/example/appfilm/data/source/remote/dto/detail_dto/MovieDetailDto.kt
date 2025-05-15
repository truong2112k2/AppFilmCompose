package com.example.appfilm.data.source.remote.dto.detail_dto

data class MovieDetailDto(
    val episodes: List<Episode>,
    val movie: Movie,
    val msg: String,
    val status: Boolean
)