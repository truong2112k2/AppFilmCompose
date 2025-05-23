package com.example.appfilm.data.source.remote.dto.search_dto

data class Pagination(
    val currentPage: Int,
    val totalItems: Int,
    val totalItemsPerPage: Int,
    val totalPages: Int
)