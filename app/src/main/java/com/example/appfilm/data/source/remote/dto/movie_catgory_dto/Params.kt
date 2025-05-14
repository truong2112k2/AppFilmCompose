package com.example.appfilm.data.source.remote.dto.movie_catgory_dto

data class Params(
    val filterCategory: List<String>,
    val filterCountry: List<String>,
    val filterType: List<String>,
    val filterYear: List<String>,
    val pagination: Pagination,
    val slug: String,
    val sortField: String,
    val sortType: String,
    val type_slug: String
)