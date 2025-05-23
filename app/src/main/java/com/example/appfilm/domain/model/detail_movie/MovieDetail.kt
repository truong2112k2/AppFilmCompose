package com.example.appfilm.domain.model.detail_movie

data class MovieDetail(
    val _id: String? = "",
    val actor: List<String>? = emptyList(),
    val category: List<String>? = emptyList(),
    val theater_movies: Boolean? = false,
    val content: String? = "",
    val country: List<String>? = emptyList(),
    val director: List<String>? = emptyList(),
    val episode_current: String? = "",
    val episode_total: String? = "",
    val is_copyright: Boolean? = false,
    val lang: String? = "",
    val name: String? = "",
    val origin_name: String? = "",
    val poster_url: String? = "",
    val quality: String? = "",
    val showtimes: String? = "",
    val slug: String? = "", //
    val status: String? = "", //
    val thumb_url: String? = "",
    val trailer_url: String? = "",
    val type: String? = "",
    val year: Int? = 0,
    val vote_average: Int? = 0,
    val listEpisodeMovie: List<EpisodeMovie>? = emptyList()

)