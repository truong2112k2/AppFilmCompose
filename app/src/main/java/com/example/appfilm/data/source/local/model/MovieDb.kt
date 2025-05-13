package com.example.appfilm.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieDb(
    @PrimaryKey val _id: String,
    val name: String?,
    val origin_name: String?,
    val poster_url: String?,
    val slug: String?,
    val thumb_url: String?,
    val year: Int?,
    val type: String?,
    val season: String?,
    val vote_average: String?,
    val vote_count: String?,
    val time: String?,
    val localPosterPath: String? = null  // Lưu đường dẫn ảnh trên thiết bị
)
