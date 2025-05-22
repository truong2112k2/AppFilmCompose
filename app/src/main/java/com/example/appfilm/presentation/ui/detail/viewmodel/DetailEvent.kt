package com.example.appfilm.presentation.ui.detail.viewmodel

import com.example.appfilm.domain.model.Movie
import com.example.appfilm.domain.model.detail_movie.MovieDetail
import com.example.appfilm.presentation.ui.home.screen.home_movie_screen.viewmodel.HomeMovieEvent

sealed class DetailEvent {
    data class ReTry(val slug: String) : DetailEvent()
    data class GetDetail(val slug: String) : DetailEvent()
    data class AddFavouriteMovie(val movieDetail: MovieDetail): DetailEvent()
    data class CheckFavouriteMovie(val movieID: String): DetailEvent()
    data class DeleteFavouriteMovie(val movieId: String): DetailEvent()
}