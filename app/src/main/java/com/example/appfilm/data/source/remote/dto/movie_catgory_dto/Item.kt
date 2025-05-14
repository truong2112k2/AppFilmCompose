package com.example.appfilm.data.source.remote.dto.movie_catgory_dto

data class Item(
    val _id: String, // lay
    val category: List<Category>,
    val chieurap: Boolean,
    val country: List<Country>,
    val episode_current: String,
    val lang: String,
    val modified: Modified,
    val name: String, // lay
    val origin_name: String, // lay
    val poster_url: String, // lay
    val quality: String,
    val slug: String,// lay
    val sub_docquyen: Boolean,
    val thumb_url: String,// lay
    val time: String, // lay
    val type: String,// lay
    val year: Int // lay
)

/*
    val _id: String, // lay
    val name: String, // lay
    val origin_name: String, // lay
    val poster_url: String, // lay
    val slug: String,// lay



 */