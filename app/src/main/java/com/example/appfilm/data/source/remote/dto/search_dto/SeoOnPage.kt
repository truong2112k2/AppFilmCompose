package com.example.appfilm.data.source.remote.dto.search_dto

data class SeoOnPage(
    val descriptionHead: String,
    val og_image: List<String>,
    val og_type: String,
    val og_url: String,
    val titleHead: String
)