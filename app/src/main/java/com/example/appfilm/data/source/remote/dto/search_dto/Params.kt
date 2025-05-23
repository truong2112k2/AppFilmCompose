package com.example.appfilm.data.source.remote.dto.search_dto

data class Params(
    val filterCategory: List<String>,
    val filterCountry: List<String>,
    val filterType: List<String>,
    val filterYear: List<String>,
    val keyword: String,
    val pagination: Pagination,
    val sortField: String,
    val sortType: String,
    val type_slug: String
)