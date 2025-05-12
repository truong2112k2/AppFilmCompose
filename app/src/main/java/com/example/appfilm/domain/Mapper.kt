package com.example.appfilm.domain

import com.example.appfilm.data.source.remote.dto.Item
import com.example.appfilm.domain.model.Movie

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