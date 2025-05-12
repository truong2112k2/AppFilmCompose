package com.example.appfilm.domain.model

import com.example.appfilm.data.source.remote.dto.Imdb
import com.example.appfilm.data.source.remote.dto.Modified
import com.example.appfilm.data.source.remote.dto.Tmdb

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
val time: String?


//    val _id: String,
//    val name: String,
//    val origin_name: String,
//    val poster_url: String,
//    val slug: String, // dung de search
//    val thumb_url: String,
//    val year: Int,
//    val type : String,
//    val season : String,
//    val vote_average: String,
//    val vote_count: String,
//    val time: String
)