package com.example.appfilm.domain

import com.example.appfilm.data.source.local.model.MovieDb
import com.example.appfilm.data.source.remote.dto.category_dto.CategoryDtoItem
import com.example.appfilm.data.source.remote.dto.detail_dto.MovieDetailDto
import com.example.appfilm.data.source.remote.dto.detail_dto.ServerData
import com.example.appfilm.data.source.remote.dto.movie_dto.Item
import com.example.appfilm.domain.model.Category
import com.example.appfilm.domain.model.Movie
import com.example.appfilm.domain.model.MovieByCategory
import com.example.appfilm.domain.model.MovieBySearch
import com.example.appfilm.domain.model.detail_movie.EpisodeMovie
import com.example.appfilm.domain.model.detail_movie.MovieDetail

fun Item.toMovie(): Movie {
    return Movie(
        _id = this._id,
        name = this.name,
        origin_name = this.origin_name,
        poster_url = this.poster_url,
        slug = this.slug,
        thumb_url = this.thumb_url,
        year = this.year,
        type = this.tmdb?.type,
        season = this.tmdb?.season.toString(),
        vote_average = this.tmdb?.vote_average.toString(),
        vote_count = this.tmdb?.vote_count.toString(),
        time = this.modified?.time
    )
}

fun Item.toMovieDb(): MovieDb {
    return MovieDb(
        _id = this._id.toString(),
        name = this.name,
        origin_name = this.origin_name,
        poster_url = this.poster_url,
        slug = this.slug,
        thumb_url = this.thumb_url,
        year = this.year,
        type = this.tmdb?.type,
        season = this.tmdb?.season.toString(),
        vote_average = this.tmdb?.vote_average.toString(),
        vote_count = this.tmdb?.vote_count.toString(),
        time = this.modified?.time
    )
}

fun MovieDb.toMovie(): Movie {
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

fun Movie.toItem(): Item {
    return Item(
        _id = this._id.toString(),
        name = this.name.toString(),
        origin_name = this.origin_name.toString(),
        poster_url = this.poster_url.toString(),
        slug = this.slug.toString(),
        thumb_url = this.thumb_url.toString(),
        year = this.year?.toInt() ?: 0,

        )
}


fun CategoryDtoItem.toCategory(): Category {
    return Category(
        _id = this._id,
        name = this.name,
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


fun ServerData.toEpisodeMovie(): EpisodeMovie {
    return EpisodeMovie(
        filename = this.filename,
        link_embed = this.link_embed,
        link_m3u8 = this.link_m3u8,
        name = this.name,
        slug = this.slug
    )
}

fun MovieDetailDto.toMovieDetail(): MovieDetail {

    val listCategory = this.movie.category.map {
        it.name.toString()
    }

    val listCountry = this.movie.country.map {
        it.name.toString()
    }

    val listEpisode = this.episodes.first().server_data.map {
        it.toEpisodeMovie()
    }

    return MovieDetail(
        _id = this.movie._id,
        actor = this.movie.actor,
        category = listCategory,
        theater_movies = this.movie.chieurap,
        content = this.movie.content,
        country = listCountry,
        director = this.movie.director,
        episode_current = this.movie.episode_current,
        episode_total = this.movie.episode_total,
        is_copyright = this.movie.is_copyright,
        lang = this.movie.lang,
        name = this.movie.name,
        origin_name = this.movie.origin_name,
        poster_url = this.movie.poster_url,
        quality = this.movie.quality,
        showtimes = this.movie.showtimes,
        slug = this.movie.slug,
        status = this.movie.status,
        thumb_url = this.movie.thumb_url,
        trailer_url = this.movie.trailer_url,
        type = this.movie.type,
        year = this.movie.year,
        vote_average = this.movie.tmdb.vote_average.toInt(),
        listEpisodeMovie = listEpisode,
    )
}

fun MovieDetail.toMovie(): Movie {
    return Movie(
        _id = this._id,
        name = this.name,
        origin_name = this.origin_name,
        poster_url = this.poster_url,
        slug = this.slug,
        thumb_url = this.thumb_url,
        year = this.year,
        type = this.type,
        season = null, // Không có trong MovieDetail, để null
        vote_average = this.vote_average?.toString(), // Chuyển Int? -> String?
        vote_count = null, // Không có trong MovieDetail, để null
        time = null, // Không có trong MovieDetail, để null
        localPosterPath = null // Dữ liệu local, không có trong MovieDetail
    )
}


fun com.example.appfilm.data.source.remote.dto.search_dto.Item.toMovieBySearch(): MovieBySearch {
    return MovieBySearch(
        _id = this._id,
        category = this.category.map { it.name.toString() },
        chieurap = this.chieurap,
        country = this.country.map { it.name.toString() },
        episode_current = this.episode_current,
        lang = this.lang,
        name = this.name,
        origin_name = this.origin_name,
        poster_url = this.poster_url,
        quality = this.quality,
        slug = this.slug,
        sub_docquyen = this.sub_docquyen,
        thumb_url = this.thumb_url,
        time = this.time,
        vote_average = this.tmdb.vote_average,
        vote_count = this.tmdb.vote_count,
        type = this.type,
        year = this.year
    )
}

