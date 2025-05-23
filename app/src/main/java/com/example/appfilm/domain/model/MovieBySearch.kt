package com.example.appfilm.domain.model

data class MovieBySearch(
    val _id: String,
    val category: List<String>,
    val chieurap: Boolean,
    val country: List<String>,
    val episode_current: String,
    val lang: String,
    val name: String,
    val origin_name: String,
    val poster_url: String,
    val quality: String,
    val slug: String,
    val sub_docquyen: Boolean,
    val thumb_url: String,
    val time: String,
    val vote_average: Double,  // val tmdb: Tmdb,
    val vote_count: Int,  // val tmdb: Tmdb,
    val type: String,
    val year: Int

)