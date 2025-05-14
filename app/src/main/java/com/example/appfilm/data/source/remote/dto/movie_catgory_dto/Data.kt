package com.example.appfilm.data.source.remote.dto.movie_catgory_dto

data class Data(
    val APP_DOMAIN_CDN_IMAGE: String,// skip
    val APP_DOMAIN_FRONTEND: String, // skip
    val breadCrumb: List<BreadCrumb>,
    val items: List<Item>,
    val params: Params,
    val seoOnPage: SeoOnPage,
    val titlePage: String,
    val type_list: String
)