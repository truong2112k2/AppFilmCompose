package com.example.appfilm.domain

import com.example.appfilm.data.source.local.model.MovieDb
import com.example.appfilm.data.source.remote.dto.category_dto.CategoryDto
import com.example.appfilm.data.source.remote.dto.category_dto.CategoryDtoItem
import com.example.appfilm.data.source.remote.dto.movie_dto.Item
import com.example.appfilm.domain.model.Category
import com.example.appfilm.domain.model.Movie
import com.example.appfilm.domain.model.MovieByCategory

fun Item.toMovie(): Movie {
    return Movie(
        _id = this._id,
        name = this.name,
        origin_name = this.origin_name,
        poster_url = this.poster_url,
        slug = this.slug,
        thumb_url = this.thumb_url,
        year = this.year,
        type = this.tmdb.type,
        season = this.tmdb.season.toString(),
        vote_average = this.tmdb.vote_average.toString(),
        vote_count = this.tmdb.vote_count.toString(),
        time = this.modified.time
    )
}

fun Item.toMovieDb(): MovieDb {
    return MovieDb(
        _id = this._id,
        name = this.name,
        origin_name = this.origin_name,
        poster_url = this.poster_url,
        slug = this.slug,
        thumb_url = this.thumb_url,
        year = this.year,
        type = this.tmdb.type,
        season = this.tmdb.season.toString(),
        vote_average = this.tmdb.vote_average.toString(),
        vote_count = this.tmdb.vote_count.toString(),
        time = this.modified.time
    )
}

fun MovieDb.toMovie(): Movie{
    return Movie(
        _id = this._id,
        name = this.name,
        origin_name = this.origin_name,
        poster_url = this.poster_url,
        slug = this.slug,
        thumb_url = this.thumb_url,
        year = this.year,
        type = this.type,
        season = this.season.toString(),
        vote_average = this.vote_average.toString(),
        vote_count = this.vote_count.toString(),
        time = this.time,
        localPosterPath = this.localPosterPath
    )
}


fun CategoryDtoItem.toCategory(): Category{
    return Category(
        _id = this._id,
        name =  this.name,
        slug = this.slug
    )
}



fun com.example.appfilm.data.source.remote.dto.movie_catgory_dto.Item.toMovieByCategory(): MovieByCategory {
    return MovieByCategory(
        _id = this._id,
        name = this.name,
        origin_name = this.origin_name,
        poster_url = this.poster_url,
        slug = this.slug
    )
}

/*
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
 */