package com.example.appfilm.data.source.remote.dto.movie_catgory_dto

data class BreadCrumb(
    val isCurrent: Boolean,
    val name: String,
    val position: Int,
    val slug: String
)