package com.example.appfilm.domain.model

data class Movie(

    val _id: String?,
    val name: String?,
    val origin_name: String?,
    val poster_url: String?,
    val slug: String?,
    val thumb_url: String?,
    val year: Int?,
    val type: String?,  // <- có thể null
    val season: String?,
    val vote_average: String?,
    val vote_count: String?,
    val time: String?,
    val localPosterPath: String? = null  // Lưu đường dẫn ảnh trên thiết bị


)